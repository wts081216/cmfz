package com.baizhi.wts.entity;

import com.alibaba.excel.converters.string.StringImageConverter;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.FileUtils;
import org.aspectj.util.FileUtil;


import java.io.File;
import java.io.IOException;

public class ImageConverter extends StringImageConverter {


    @Override
    public CellData convertToExcelData(String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws IOException {
        //需要将value由 相当路径|网络路径 转化为绝对路径
        String property = System.getProperty("user.dir");//绝对路径
        //获取文件名
        System.out.println("property =========================== " + property);
        String[] split = value.split("/");
        value = split[split.length - 1];
        value = property + "\\src\\main\\webapp\\upload\\img\\" + value;
        return new CellData(FileUtils.readFileToByteArray(new File(value)));
    }
}
