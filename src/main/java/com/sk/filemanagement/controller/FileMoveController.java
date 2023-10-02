package com.sk.filemanagement.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sk.filemanagement.service.FileManagementService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/files")
public class FileMoveController {
  private final FileManagementService fileService;

  @Value("${upload.directory}")
  private String uploadDirectory;

  @Value("${download.directory}")
  private String downloadDirectory;

  @Value("${file.folder.name}")
  private String tempDirectory;

  @Value("${predefined.token}")
  private String this_predefined_token;

  // @Autowired
  public FileMoveController(FileManagementService fileService) {
    this.fileService = fileService;
  }

  @PutMapping("/move")
  public ResponseEntity<String> moveFile(@RequestParam("fileName") String fileName) {

    String sourcePath = tempDirectory + "/" + fileName;
    String destinationPath = uploadDirectory + "/" + fileName;

    boolean moved = fileService.moveFile(sourcePath, destinationPath);

    if (moved) {
      log.debug("## File moved successfully!!");
      return new ResponseEntity<>("File moved successfully", HttpStatus.OK);
    } else {
      log.debug("## File move failed!!");
      return new ResponseEntity<>("File move failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
