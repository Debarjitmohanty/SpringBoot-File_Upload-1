package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.FileUpload;

@Repository
public interface FileUploadRepository extends JpaRepository<FileUpload, Long>{

}
