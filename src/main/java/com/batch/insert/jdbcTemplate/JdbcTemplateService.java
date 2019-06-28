package com.batch.insert.jdbcTemplate;

import com.batch.insert.jpa.JpaEntity;
import com.batch.insert.preparestatement.BulkPOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service
public class JdbcTemplateService {

    @Autowired
    private JdbcTemplateRepository jdbcTemplateRepository;


    @Transactional
    public void saveJdbcTemplate(List<JpaEntity> list) {
        jdbcTemplateRepository.saveJdbcTemplate(list);
    }

}
