package com.baizhi.wts.service;

import com.baizhi.wts.entity.Chapter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(value = SpringRunner.class)
public class Test_ChapterServiceImpl {

    @Autowired
    private ChapterService chapterService;

    @Test
    public void selectByPage() {
        Chapter chapter = new Chapter();
        chapter.setAlbumId("2");
        Map map = chapterService.quryBypage(2, 2, chapter);
        List<Chapter> rows = (List<Chapter>) map.get("rows");
        for (Chapter row : rows) {
            System.out.println("row = " + row);
        }
    }
}
