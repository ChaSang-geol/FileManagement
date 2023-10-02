package com.sk.filemanagement.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import com.sk.filemanagement.dto.FileName;

public interface FileManagementService {

  public ResponseEntity<FileName> getFile(String fileName);

  public boolean fileNameCheck(String fileName);

  public boolean deleteFile(String filePath);

  public boolean moveFile(String sourcePath, String destinationPath);

}