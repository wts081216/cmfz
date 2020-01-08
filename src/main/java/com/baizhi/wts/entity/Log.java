package com.baizhi.wts.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.text.Format;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Log {
  @Id
  private String id;
  private String name;
  private String thing;
  @JSONField(format = "yyyy-MM-dd ")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private java.util.Date date;
  private String flag;
}
