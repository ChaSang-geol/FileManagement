package com.sk.filemanagement.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
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
          // 파일 이름을 가져 옵니다.
          // String fileName = new File(file.getOriginalFilename()).getCanonicalPath();
          String fileName = StringUtils.getFilename(file.getOriginalFilename());
          // 업로드한 디렉토리 + 전송 파일이름으로 파일 path를 만듦니다.
          Path targetLocation = FileSystems.getDefault().getPath(uploadDirectory + File.separator + fileName);
          // input 스트림에 있는 파일을 업로드 디렉토리에 저장, 같은 파일이 있으면 덮어쓰기 합니다.
          InputStream source = file.getInputStream();
          Files.copy(source, targetLocation, StandardCopyOption.REPLACE_EXISTING);
          // InputStream 을 Close 합니다.
          source.close();

          log.info("# 파일업로드 성공 : " + fileName);

          return "파일 업로드가 성공적으로 완료되었습니다." + fileName;
        } catch (IOException e) {
          log.error("# 파일 업로드 IOException 오류 : " + token, e);
          return "파일 업로드 중에 오류가 발생했습니다. IOException ";// + e.getMessage();
        }
      } else {
        return "업로드할 파일이 없습니다.";
      }
    } catch (Exception e) {
      // handle exception
      log.error("# 파일 업로드 예러(Runtime 오류) : " + token, e);
      return "파일 업로드 중에 오류가 발생했습니다. Runtime 오류";// + e.getMessage();
    }
    // return "파일 업로드가 성공적으로 완료되었습니다.";
  }

}