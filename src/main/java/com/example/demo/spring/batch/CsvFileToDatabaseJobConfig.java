package com.example.demo.spring.batch;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class CsvFileToDatabaseJobConfig {

    private static final String QUERY_INSERT = "INSERT " +
            "INTO bulk(data) " +
            "VALUES (?)";

    @Bean
    ItemWriter<BulkDTO> csvFileDatabaseItemWriter(DataSource dataSource,
                                                  NamedParameterJdbcTemplate jdbcTemplate) {
        JdbcBatchItemWriter<BulkDTO> databaseItemWriter = new JdbcBatchItemWriter<>();
        databaseItemWriter.setDataSource(dataSource);
        databaseItemWriter.setJdbcTemplate(jdbcTemplate);

        databaseItemWriter.setSql(QUERY_INSERT);

        ItemPreparedStatementSetter<BulkDTO> valueSetter =
                new BulkPreparedStatementSetter();
        databaseItemWriter.setItemPreparedStatementSetter(valueSetter);

        return databaseItemWriter;
    }
}
