package com.baizhi.wts.dao;

import com.baizhi.wts.entity.Banner;

import java.io.Serializable;
import java.util.List;

public class BannerPageDto implements Serializable {
    private Integer page;
    private Integer total;
    private Integer records;
    private List<Banner> rows;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getRecords() {
        return records;
    }

    public void setRecords(Integer records) {
        this.records = records;
    }

    public List<Banner> getRows() {
        return rows;
    }

    public void setRows(List<Banner> rows) {
        this.rows = rows;
    }
}
