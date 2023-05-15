package com.sk.filemanagement.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/files")
public class FileDownloadController {

  @Value("${upload.directory}")
  private String uploadDirectory;

  @Value("${download.directory}")
  private String downloadDirectory;

  @Value("${predefined.token}")
  private String this_predefined_token;

  // 파일 다운로드 요청을 처리하는 컨트롤러 메소드입니다.
  @GetMapping("/download")
  public ResponseEntity<Resource> downloadFile(@RequestHeader("Authorization") String token,
      @RequestParam("fileName") String fileName,
      HttpServletResponse response) throws IOException {

    // 파일 시스템에서 파일을 읽어들입니다.
    Path filePath = Paths.get(downloadDirectory, fileName);
    Resource resource = new FileSystemResource(filePath.toFile());
    try {

      // 파일이 존재하지 않는 경우 404 에러를 반환합니다.
      if (!resource.exists()) {
        return ResponseEntity.notFound().build();
      }
    } catch (Exception e) {
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "파일 다운로드 중에 오류가 발생했습니다: " + e.getMessage());
    }

    // 파일 다운로드를 위한 응답 객체를 생성합니다.
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
        .body(resource);
  }

}
