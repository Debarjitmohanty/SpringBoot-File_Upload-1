package com.app.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.service.FileUploadService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/files")
@Service
public class FileUploadController {

	private final FileUploadService fileUploadService;
	
	@PostMapping("upload")
	public ResponseEntity<Object> uploadFiles(@RequestParam("files")MultipartFile[] files,
			                                  @RequestHeader Map<String, String> headers){
		try {
			return ResponseEntity.ok().body(fileUploadService.uploadFiles(files,headers));
		}catch(Exception e) {
			log.error(e.getLocalizedMessage());
			return ResponseEntity.badRequest().body(e.getLocalizedMessage());
		}
	}
}
