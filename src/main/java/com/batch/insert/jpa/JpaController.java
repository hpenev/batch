package com.batch.insert.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class JpaController {

    private static final String DATA = "1,Maude,Holloway,31,Taned Pass,Ocsujvel,ME,18723,$1265.76,RED,01/13/1973";

    @Autowired
    private JpaService jpaService;


    @PostMapping("/saveAll/{rows}")
    public String saveAll(@PathVariable("rows") int rows) {
        jpaService.saveAll(makeList(rows));
        return "ok";
    }

    @PostMapping("/save/{rows}")
    public String save(@PathVariable("rows") int rows) {
        jpaService.save(makeList(rows));
        return "ok";
    }

    @PostMapping("/emPersist/{rows}")
    public String emPersist(@PathVariable("rows") int rows) {
        jpaService.emPersist(makeList(rows));
        return "ok";
    }

    @PostMapping("/sessionFactory/{rows}")
    public String sessionFactory(@PathVariable("rows") int rows) {
        jpaService.sessionFactory(makeList(rows));
        return "ok";
    }

    @PostMapping("/statelessSession/{rows}")
    public String statelessSession(@PathVariable("rows") int rows) {
        jpaService.statelessSession(makeList(rows));
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
