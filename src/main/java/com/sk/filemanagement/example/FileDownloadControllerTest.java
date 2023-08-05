package com.sk.filemanagement.example;

import java.io.File;
import java.io.IOException;

import org.springframework.web.bind.annotation.RestController;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@RestController
public class FileDownloadControllerTest {

  private String fileName = "test.xml";
  private String apiUrl = "http://localhost:8080/api/files/download";
  private String filePath = "/temp/download/";
  private String apiKey = "predefinedMyApiKey";

  void testDownloadFile() throws IOException {

    OkHttpClient client = new OkHttpClient();

    File transFile = new File(filePath + fileName);
    MediaType mediaType = MediaType.parse("multipart/form-data");
    RequestBody body = RequestBody.create(transFile, mediaType);

    Request request = new Request.Builder()
        .url(apiUrl)
        .post(body)
        .addHeader("Authorization", "Bearer " + apiKey)
        .addHeader("content-type", "multipart/form-data")
        .build();

    Response response = client.newCall(request).execute();
    response.close();
  }

}
