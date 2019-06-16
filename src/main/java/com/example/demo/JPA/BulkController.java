package com.example.demo.JPA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BulkController {

    @Autowired
    private BulkService bulkService;

    @PostMapping("/saveAll/{rows}")
    public String saveAll(@PathVariable("rows") int rows) {
        bulkService.saveAll(rows);
        return "ok";
    }

    @PostMapping("/save/{rows}")
    public String save(@PathVariable("rows") int rows) {
        bulkService.save(rows);
        return "ok";
    }

}
