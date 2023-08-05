package com.sk.filemanagement.example;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class UploadFile {
  private String fileName = "test.txt";
  private String apiUrl = "http://localhost:8080/api/files/upload";
  private String filePath = "/temp/";
  private String apiKey = "predefinedMyApiKey";


  public void whenUploadFile_thenCorrect() throws IOException {
    MultipartBody requestBody = new MultipartBody.Builder()
        .setType(MultipartBody.FORM)
        .addFormDataPart("file", fileName, okhttp3.RequestBody.create(new File(
            filePath + fileName),
            MediaType.parse("multipart/form-data")))
        .build();

    String BASE_URL = "http://localhost:8080/api/files";
    Request request = new Request.Builder()
        .url(BASE_URL + "/upload")
        .post(requestBody)
        .build();
    OkHttpClient client = new OkHttpClient();
    Call call = client.newCall(request);
    Response response = call.execute();

    // assertThat(response.code(), is(200));

    if ("200".equals(response.code())) {
      System.out.println("파일을 업로드에 성공하였습니다.");
    } else {
      System.out.println("파일을 업로드에 실패하였습니다.");
    }
    response.close();
  }

  
  public void whenSetDefaultHeader_thenCorrect()
      throws IOException {

    File transFile = new File(filePath + fileName);

    OkHttpClient client = new OkHttpClient();
    // .Builder()
    // .addInterceptor(
    // new DefaultContentTypeInterceptor("application/json"))
    // .build();

    Request request = new Request.Builder()
        .addHeader("Authorization", "Bearer " + apiKey)
        .url(apiUrl)
        .put(okhttp3.RequestBody.create(transFile, MediaType.parse("multipart/form-data")))
        .build();

    Call call = client.newCall(request);
    Response response = call.execute();
    // Response response = client.newCall(request).execute();
    response.close();
  }


  public void FileUploadTest() throws Exception {
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
