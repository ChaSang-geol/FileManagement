package com.sk.filemanagement.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;//.toByteArray;

@Slf4j
@RestController
@RequestMapping("/api/v2/files")
public class FileManagement {
  @Value("${upload.directory}")
  private String uploadDirectory;

  @Value("${download.directory}")
  private String downloadDirectory;

  
  // File Download API
  @GetMapping(value = "/download", produces = "application/json;charset=utf-8")
  public ResponseEntity<?> getFile(
    @RequestParam(value = "fileName", required = false) String fileName) throws IOException {
      
    String YOUR_PATH = downloadDirectory + "/";
    ResponseEntity<?> respEntity = null;
    File result = new File(YOUR_PATH + fileName);
    log.info("# 파일 : " + fileName + " # :" + result.getAbsolutePath());
    if (result.exists()) {
      InputStream inputStream = new FileInputStream(YOUR_PATH + fileName);
      String type = URLConnection.guessContentTypeFromStream(inputStream);

      byte[] out = IOUtils.toByteArray(inputStream);

      HttpHeaders responseHeaders = new HttpHeaders();
      responseHeaders.add("content-disposition", "attachment; filename=" + fileName);
      responseHeaders.add("Content-Type", type);

      respEntity = new ResponseEntity<>(out, responseHeaders, HttpStatus.OK);
    } else {
      respEntity = new ResponseEntity<>("File Not Found", HttpStatus.OK);
    }
    return respEntity;
  }

  // File Upload API
  @PostMapping("/upload")
  public String postFile(@RequestHeader("Authorization") String token,
      @RequestParam("file") MultipartFile file) {

    try {

      if (!file.isEmpty()) {

        String fileName = StringUtils.getFilename(file.getOriginalFilename());
        Path targetLocation = Path.of(uploadDirectory + File.separator + fileName);
        InputStream source = file.getInputStream();
        Files.copy(source, targetLocation, StandardCopyOption.REPLACE_EXISTING);
        source.close();

        log.info("# 파일 업로드 성공 : " + fileName);
        return "파일 업로드가 성공적으로 완료되었습니다. : " + fileName;
      } else {
        return "업로드할 파일이 없습니다.";
      }
    } catch (IOException e) {
      log.error("# 파일 업로드 IOException 오류 : " + token, e);
      return "파일 업로드 중에 오류가 발생했습니다: " + e.getMessage();

    } catch (Exception e) {
      // handle exception
      log.error("# 파일 업로드 Runtime 오류 : " + token, e);
      return "파일 업로드 중에 오류가 발생했습니다 : " + e.getMessage();
    }
    // return "파일 업로드가 성공적으로 완료되었습니다.";
  }
}
