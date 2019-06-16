package com.example.demo;

import org.springframework.util.StopWatch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Batch {
    private static final String URL = "jdbc:postgresql://localhost/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Secret123";
    private static final int ROWS = 500_000;


    public static void main(String[] args) throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        assert conn != null;
        conn.setAutoCommit(false);

        String data = "1,Maude,Holloway,31,Taned Pass,Ocsujvel,ME,18723,$1265.76,RED,01/13/1973";

        PreparedStatement ps = conn.prepareStatement("INSERT INTO bulk (data) VALUES (?)");

        // bulk table with id = serial and data = bytea

        StopWatch sw = new StopWatch();
        sw.start("Loop");
        for (int i = 0; i < ROWS; i++) {
            ps.setBytes(1, data.getBytes());
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
        System.out.println("Rows number: " + ROWS);

//        Loop duration: 0.08s
//        ExecuteBatch duration: 1.387s
//        Commit duration: 0.011s
//        TOTAL duration: 1.478s
//        Rows number: 100000

//        Loop duration: 0.971s
//        ExecuteBatch duration: 7.369s
//        Commit duration: 0.009s
//        TOTAL duration: 8.349s
//        Rows number: 500000
    }
}
