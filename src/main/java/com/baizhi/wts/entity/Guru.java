package com.baizhi.wts.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Guru {
  @Id
  private String id;
  private String name;
  private String photo;
  private String status;
  private String nickName;

}
