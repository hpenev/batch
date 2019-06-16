package com.example.demo.JPA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.LinkedList;
import java.util.List;

@Service
public class BulkService {

    @Autowired
    private BulkRepository repo;

    private static final String DATA = "1,Maude,Holloway,31,Taned Pass,Ocsujvel,ME,18723,$1265.76,RED,01/13/1973";
    private static final String SQL = "INSERT INTO bulk (data) VALUES (?)";

    //    http://localhost:8080/saveAll/100000/
    public void saveAll(int rows) {
        List<BulkEntity> list = new LinkedList<>();

        StopWatch sw = new StopWatch();
        sw.start("Loop");
        for (int i = 0; i < rows; i++) {
            list.add(new BulkEntity(DATA.getBytes()));
        }
        sw.stop();
        System.out.println(sw.getLastTaskName() + " duration: " + sw.getLastTaskInfo().getTimeSeconds());

        sw.start("saveAll");
        repo.saveAll(list);
        sw.stop();
        System.out.println(sw.getLastTaskName() + " duration: " + sw.getLastTaskInfo().getTimeSeconds());
        System.out.println("Rows: " + rows);

//        Loop duration: 0.033
//        saveAll duration: 18.158
//        Rows: 100000

//        Loop duration: 0.145
//        saveAll duration: 88.777
//        Rows: 500000


    }

    //    http://localhost:8080/save/100000/
    public void save(int rows) {
        List<BulkEntity> list = new LinkedList<>();

        StopWatch sw = new StopWatch();
        sw.start("Save");
        for (int i = 0; i < rows; i++) {
            repo.save(new BulkEntity(DATA.getBytes()));
        }
        sw.stop();
        System.out.println(sw.getLastTaskName() + " duration: " + sw.getLastTaskInfo().getTimeSeconds());
        System.out.println("Rows: " + rows);

//        N/A
    }
}
