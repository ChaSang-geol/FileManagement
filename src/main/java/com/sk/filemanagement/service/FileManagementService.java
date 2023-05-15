package com.sk.filemanagement.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface FileManagementService {

  ResponseEntity getFile(
      String fileName) throws IOException;

}