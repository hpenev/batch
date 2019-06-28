package com.batch.insert.spring.batch;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BulkPreparedStatementSetter implements ItemPreparedStatementSetter<BulkDTO> {

    @Override
    public void setValues(BulkDTO item, PreparedStatement ps) throws SQLException {
        ps.setBytes(1, item.getData());
    }
}
