package com.baizhi.wts.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.text.Format;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
  @Id
  private String id;
  private String phone;
  private String password;
  private String salt;
  private String status;
  private String photo;
  private String name;
  private String nickName;
  private String sex;
  private String sign;
  private String location;
  @JSONField(format = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date rigestDate;
  @JSONField(format = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date lastLogin;
}
