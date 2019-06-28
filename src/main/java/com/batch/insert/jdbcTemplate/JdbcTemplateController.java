package com.batch.insert.jdbcTemplate;

import com.batch.insert.jpa.JpaEntity;
import com.batch.insert.preparestatement.BulkPOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class JdbcTemplateController {

    private static final String DATA = "1,Maude,Holloway,31,Taned Pass,Ocsujvel,ME,18723,$1265.76,RED,01/13/1973";

    @Autowired
    private JdbcTemplateService jdbcTemplateService;

    @PostMapping("/jdbctemplate/{rows}")
    public String saveJdbcTemplate(@PathVariable("rows") int rows) {
        jdbcTemplateService.saveJdbcTemplate(makeList(rows));
        return "ok";
    }

    private List<JpaEntity> makeList(int rows) {
        List<JpaEntity> list = new ArrayList<>();

        StopWatch sw = new StopWatch();
        sw.start("Loop");
        for (int i = 0; i < rows; i++) {
            list.add(new JpaEntity(DATA.getBytes()));
        }
        sw.stop();
        System.out.println(sw.getLastTaskName() + " duration: " + sw.getLastTaskInfo().getTimeSeconds());

        return list;
    }
}
