package com.sk.filemanagement.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Slf4j
@RestController
@RequestMapping("/api/files")
public class FileUploadController {

  @Value("${upload.directory}")
  private String uploadDirectory;

  @Value("${download.directory}")
  private String downloadDirectory;

  @Value("${predefined.token}")
  private String this_predefined_token;

  @PostMapping("/upload")
  public String uploadFile(@RequestHeader("Authorization") String token,
      @RequestParam("file") MultipartFile file) {

    try {

      if (!file.isEmpty()) {
        try {
          String fileName = StringUtils.cleanPath(file.getOriginalFilename());
          Path targetLocation = Path.of(uploadDirectory + File.separator + fileName);
          Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

          return "파일 업로드가 성공적으로 완료되었습니다.";
        } catch (IOException e) {
          return "파일 업로드 중에 오류가 발생했습니다: " + e.getMessage();
        }
      } else {
        return "업로드할 파일이 없습니다.";
      }
    } catch (Exception e) {
      // TODO: handle exception
      log.error("파일 업로드 에러 : " + token, e);
      return "파일 업로드 중에 오류가 발생했습니다";
    }
    // return "파일 업로드가 성공적으로 완료되었습니다.";
  }

}