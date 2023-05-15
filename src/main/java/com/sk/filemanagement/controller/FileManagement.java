package com.sk.filemanagement.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.commons.io.IOUtils;//.toByteArray;

public class FileManagement {
  private String YOUR_PATH = "/temp";
  // @Override
  @GetMapping(value = "", produces = "application/json;charset=utf-8")
  public ResponseEntity getFile(
      @RequestParam(value = "fileName", required = false) String fileName) throws IOException {

    ResponseEntity respEntity = null;
    File result = new File(YOUR_PATH + fileName);

    if (result.exists()) {
      InputStream inputStream = new FileInputStream(YOUR_PATH + fileName);
      String type = URLConnection.guessContentTypeFromStream(inputStream);

      byte[] out = IOUtils.toByteArray(inputStream);

      HttpHeaders responseHeaders = new HttpHeaders();
      responseHeaders.add("content-disposition", "attachment; filename=" + fileName);
      responseHeaders.add("Content-Type", type);

      respEntity = new ResponseEntity(out, responseHeaders, HttpStatus.OK);
    } else {
      respEntity = new ResponseEntity("File Not Found", HttpStatus.OK);
    }
    return respEntity;
  }
}
