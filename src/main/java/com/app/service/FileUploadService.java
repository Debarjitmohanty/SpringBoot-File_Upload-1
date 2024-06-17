package com.app.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.FileUploadResponse;
import com.app.model.FileUpload;
import com.app.repository.FileUploadRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class FileUploadService {

	private final FileUploadRepository fileUploadRepository;
	
	
	private final ModelMapper modelMapper;
	
	@Value("${upload.files.path}")
	private String uploadPath;
	
	@Value("${upload.files.extension}")
	private String fileExtensions;
	
	public List<FileUploadResponse> uploadFiles(MultipartFile[] multiPartFiles,Map<String,String> headers ) {
		
		if(multiPartFiles==null) {
			log.error("invalid file ");		
			throw new IllegalArgumentException("Invalid file");
		}
		List<FileUpload> fileUploads=new ArrayList<>();
		Arrays.stream(multiPartFiles).forEach(file->{
			try{
				
				String filename=StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
				String fileExtension=getFileExtension(filename);
				if(!fileExtensions.contains(fileExtension)) {
					log.error("invalid file ");		
					throw new IllegalArgumentException("Invalid file extension");
				}
				byte[] bytes=file.getBytes();
				Files.write(Paths.get(uploadPath+file.getOriginalFilename()),bytes);
				
				var fileNameUpload=FilenameUtils.removeExtension(filename)+"_"+Calendar.getInstance().getTimeInMillis()+"."+getFileExtension(filename);
				
				FileUpload fileUpload=new FileUpload();
				fileUpload.setStatus(true);
				fileUpload.setFileName(fileNameUpload);
				fileUpload.setExtensionName(fileExtension);
				fileUpload.setFileOriginalName(FilenameUtils.removeExtension(filename));
				fileUpload.setFullPath(uploadPath+filename);
				fileUpload.setFileSize(file.getSize());
				fileUpload.setChannelCode(headers.get("channel-code"));
				
					fileUploads.add(fileUpload);
				
			}catch(IOException ie) {
				log.error("Upload file error {}",ie.getLocalizedMessage());
			}
		});
		
		fileUploadRepository.saveAll(fileUploads);
		
		return fileUploads.stream().map(fileUpload->modelMapper.map(fileUpload,FileUploadResponse.class))
		.collect(Collectors.toList());
	}

	public String getFileExtension(String filename) {
		int dotIndex=filename.lastIndexOf(".");
		if(dotIndex<0) {
			return null;
		}
		return filename.substring(dotIndex+1);
	}
	
}
