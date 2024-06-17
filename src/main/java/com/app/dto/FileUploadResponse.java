package com.app.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class FileUploadResponse {

	private Long Id;
	
	@JsonProperty("file_name")
    private String fileName;
	
	@JsonProperty("file_original_name")
    private String fileOriginalName;
	
	@JsonProperty("extension_name")
	private String extensionName;
	
	@JsonProperty("file_size")
	private Long fileSize;
	
	@JsonProperty("full_path")
	private String fullPath;
	
	@JsonProperty("status")
	private boolean status;
	
	@JsonProperty("channel_code")
	private String channelCode;
	
	@JsonProperty("created_on")
	private LocalDateTime localDateTime=LocalDateTime.now();
}
