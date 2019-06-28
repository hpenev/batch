package com.batch.insert.preparestatement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PrepStmtController {

    private static final String DATA = "1,Maude,Holloway,31,Taned Pass,Ocsujvel,ME,18723,$1265.76,RED,01/13/1973";

    @Autowired
    private PrepStmtService prepStmtService;

    @PostMapping("/savePS/{rows}")
    public String savePS(@PathVariable("rows") int rows) {
        prepStmtService.savePS(makeList(rows));
        return "ok";
    }

    private List<BulkPOJO> makeList(int rows) {
        List<BulkPOJO> list = new ArrayList<>();

        StopWatch sw = new StopWatch();
        sw.start("Loop");
        for (int i = 0; i < rows; i++) {
            list.add(new BulkPOJO(DATA.getBytes()));
        }
        sw.stop();
        System.out.println(sw.getLastTaskName() + " duration: " + sw.getLastTaskInfo().getTimeSeconds());

        return list;
    }
}
