package com.sk.filemanagement.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sk.filemanagement.dto.FileName;

@Service
public class FileManagementServiceImpl implements FileManagementService {
  public boolean fileNameCheck(String fileName) {

    return true;
  }

  public ResponseEntity<FileName> getFile(String fileName) {
    FileName resFileName = new FileName();
    File file = new File(fileName);
    resFileName.setFileName(file.getName());
    return ResponseEntity.ok().body(resFileName);
  }

  public boolean deleteFile(String filePath) {
    File file = new File(filePath);
    if (file.exists() && file.isFile()) {
      return file.delete();
    }
    return false;
  }

  public boolean moveFile(String sourcePath, String destinationPath) {
    try {
      Path source = new File(sourcePath).toPath();
      Path destination = new File(destinationPath).toPath();
      Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

}
