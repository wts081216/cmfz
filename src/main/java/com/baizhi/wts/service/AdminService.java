package com.baizhi.wts.service;

import com.baizhi.wts.entity.Admin;

public interface AdminService {
    public String selectOneByName(Admin admin, String clientCode);
}
