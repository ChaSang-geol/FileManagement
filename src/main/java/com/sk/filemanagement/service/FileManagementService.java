package com.sk.filemanagement.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import com.sk.filemanagement.dto.FileName;

public interface FileManagementService {

  public ResponseEntity<FileName> getFile(String fileName) throws IOException;
  public Boolean fileNameCheck(String fileName) throws Exception;


}