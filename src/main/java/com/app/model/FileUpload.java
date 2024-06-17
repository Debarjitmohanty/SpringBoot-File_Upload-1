package com.app.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileUpload implements Serializable{

    @Id
    @GeneratedValue
	private Long Id;
    private String fileName;
    private String fileOriginalName;
	private String extensionName;
	private Long fileSize;
	private String fullPath;
	private boolean status;
	private String channelCode;
	@Column(name="created_on")
	private LocalDateTime localDateTime=LocalDateTime.now();
	
}
