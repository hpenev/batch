package com.batch.insert.preparestatement;

import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class PrepStmtRepository {

    private static final String URL = "jdbc:postgresql://localhost/postgres?reWriteBatchedInserts=true";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Secret123";
    private static final String INSERT_SQL = "INSERT INTO bulk (data) VALUES (?)";

    public void savePrepStmt(List<BulkPOJO> list) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            assert conn != null;
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement(INSERT_SQL);

            StopWatch sw = new StopWatch();
            sw.start("Prepare batch insert statement");
            for (int i = 0; i < list.size(); i++) {
                ps.setBytes(1, list.get(i).getData());
                ps.addBatch();
            }
            sw.stop();
            System.out.println(sw.getLastTaskName() + " duration: " + (sw.getLastTaskInfo().getTimeSeconds()) + "s");

            sw.start("ExecuteBatch");
            ps.executeBatch();
            sw.stop();
            System.out.println(sw.getLastTaskName() + " duration: " + (sw.getLastTaskInfo().getTimeSeconds()) + "s");

            sw.start("Commit");
            conn.commit();
            sw.stop();
            System.out.println(sw.getLastTaskName() + " duration: " + (sw.getLastTaskInfo().getTimeSeconds()) + "s");

            System.out.println("TOTAL duration: " + sw.getTotalTimeSeconds() + "s");
            System.out.println("Rows number: " + list.size());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
