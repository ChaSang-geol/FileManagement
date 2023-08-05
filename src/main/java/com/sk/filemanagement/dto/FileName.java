package com.sk.filemanagement.dto;

import lombok.Data;

@Data
public class FileName {
  public Long Id;
  public String fileName;
  public String fileExt;
  public String fileSize;
  public String fileDirectory;
}
