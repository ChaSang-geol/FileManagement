package com.sk.filemanagement.controller;

// import javax.swing.Spring;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sk.filemanagement.service.FileManagementService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/files")
public class FileDeleteController {

  private final FileManagementService fileService;

  @Value("${upload.directory}")
  private String uploadDirectory;

  @Value("${download.directory}")
  private String downloadDirectory;

  @Value("${predefined.token}")
  private String this_predefined_token;

  // @Autowired
  public FileDeleteController(FileManagementService fileService) {
    this.fileService = fileService;
  }

  // DELETE http://localhost:8080/api/files/myfile.txt
  // 이렇게 하면"myfile.txt"
  // 파일이 삭제됩니다.
  @DeleteMapping("/{fileName}")
  public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
    String filePath = downloadDirectory + "/" + fileName; // 파일 경로 설정
    log.info("file path : {}", filePath);
    boolean deleted = fileService.deleteFile(filePath);
    if (deleted) {
      log.debug("## File deleted successfully!!");
      return new ResponseEntity<>("File deleted successfully", HttpStatus.OK);
    } else {
      log.debug("## File not found or unable to delete!!");
      return new ResponseEntity<>("File not found or unable to delete", HttpStatus.NOT_FOUND);
    }
  }
}
