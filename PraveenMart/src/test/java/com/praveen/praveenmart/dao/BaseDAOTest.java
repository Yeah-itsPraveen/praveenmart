package com.praveen.praveenmart.dao;

import com.praveen.praveenmart.util.DBUtil;
import org.junit.jupiter.api.BeforeAll;

import java.sql.Connection;

public abstract class BaseDAOTest {

    @BeforeAll
    public static synchronized void setupTestDatabase() throws Exception {
        DBUtil.shutdown();
        System.setProperty("db.url", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MODE=LEGACY");
        System.setProperty("db.user", "sa");
        System.setProperty("db.password", "");
        DBUtil.initDataSource();
        try (Connection conn = DBUtil.getConnection()) {
            DBUtil.executeSqlScript(conn, "schema.sql");
            DBUtil.executeSqlScript(conn, "seed.sql");
        }
    }
}
