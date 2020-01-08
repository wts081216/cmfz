package com.baizhi.wts.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Chapter {
 @Id
  private String id;
  private String title;
  private String url;
  private String size;
  private String time;
  @JSONField(format = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private java.util.Date createTime;
  private String albumId;
}
