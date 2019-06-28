package com.batch.insert.jdbcTemplate;

import com.batch.insert.jpa.JpaEntity;
import com.batch.insert.preparestatement.BulkPOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@org.springframework.stereotype.Repository
public class JdbcTemplateRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_SQL = "INSERT INTO bulk (data) VALUES (?)";

    public void saveJdbcTemplate(List<JpaEntity> list) {
        StopWatch sw = new StopWatch();
        sw.start("jdbcTemplate update");

        jdbcTemplate.batchUpdate(INSERT_SQL, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setBytes(1, list.get(i).getData());
            }

            @Override
            public int getBatchSize() {
                return list.size();
            }
        });

        sw.stop();
        System.out.println(sw.getLastTaskName() + " duration: " + (sw.getLastTaskInfo().getTimeSeconds()) + "s");
    }
}
