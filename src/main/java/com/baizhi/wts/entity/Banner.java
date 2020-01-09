package com.baizhi.wts.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Banner implements Serializable {
  @Id
  @ExcelProperty(value = "id")
  private String id;
  @ExcelProperty(value = "标题")
  private String title;
  @ExcelProperty(value = "图片",converter = ImageConverter.class)
  private String url;
  @ExcelProperty(value = "链接")
  private String href;
  @JSONField(format = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @ExcelProperty(value = "创建时间")
  private java.util.Date createDate;
  @ExcelProperty(value = "描述")
  private String description;
  @ExcelProperty(value = "状态")
  private String status;



}
