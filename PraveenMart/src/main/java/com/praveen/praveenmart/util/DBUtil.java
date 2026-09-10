package com.praveen.praveenmart.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DBUtil {

    private static final Logger logger = LoggerFactory.getLogger(DBUtil.class);
    private static HikariDataSource dataSource;

    static {
        initDataSource();
    }

    public static synchronized void initDataSource() {
        if (dataSource != null && !dataSource.isClosed()) {
            return;
        }

        String jdbcUrl = System.getProperty("db.url", System.getenv("DB_URL"));
        if (jdbcUrl == null || jdbcUrl.isBlank()) {
            jdbcUrl = "jdbc:h2:~/praveenmart;AUTO_SERVER=TRUE;MODE=LEGACY";
        }

        String dbUser = System.getProperty("db.user", System.getenv("DB_USER"));
        if (dbUser == null)
            dbUser = "sa";

        String dbPass = System.getProperty("db.password", System.getenv("DB_PASSWORD"));
        if (dbPass == null)
            dbPass = "";

        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.h2.Driver");
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(dbUser);
        config.setPassword(dbPass);
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000);
        config.setPoolName("PraveenMartHikariPool");

        dataSource = new HikariDataSource(config);
        logger.info("HikariCP connection pool initialized for URL: {}", jdbcUrl);

        initSchemaAndSeed();
    }

    private static void initSchemaAndSeed() {
        try (Connection conn = dataSource.getConnection();
                Statement stmt = conn.createStatement()) {

            boolean tablesExist = false;
            try (ResultSet rs = stmt
                    .executeQuery("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'USERS'")) {
                if (rs.next() && rs.getInt(1) > 0) {
                    tablesExist = true;
                }
            }

            int productCount = 0;
            if (tablesExist) {
                try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM products")) {
                    if (rs.next()) {
                        productCount = rs.getInt(1);
                    }
                } catch (Exception ignored) {}
            }

            if (!tablesExist || productCount < 10) {
                logger.info("Initializing schema from schema.sql...");
                executeSqlScript(conn, "schema.sql");
                logger.info("Schema created successfully.");

                logger.info("Populating database with seed.sql...");
                executeSqlScript(conn, "seed.sql");
                logger.info("Seed data inserted successfully.");
            } else if (productCount < 48) {
                logger.info("Database has {} products. Applying migrations and adding products...", productCount);
                executeSqlScript(conn, "db/migrations/V2__add_wishlist_table.sql");
                executeSqlScript(conn, "db/migrations/V3__add_more_products.sql");
                executeSqlScript(conn, "db/migrations/V4__add_more_products.sql");
                executeSqlScript(conn, "db/migrations/V5__remove_mismatched_products.sql");
                logger.info("Additional products and migrations applied successfully.");
            } else {
                executeSqlScript(conn, "db/migrations/V5__remove_mismatched_products.sql");
                logger.info("Database schema already exists with {} products.", productCount);
            }
        } catch (Exception e) {
            logger.error("Error during database schema and seed initialization", e);
        }
    }

    public static void executeSqlScript(Connection conn, String scriptPath) throws SQLException {
        InputStream is = DBUtil.class.getClassLoader().getResourceAsStream(scriptPath);
        if (is == null) {
            logger.warn("Could not find script file on classpath: {}", scriptPath);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            StringBuilder cleanedSql = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmedLine = line.trim();
                if (!trimmedLine.startsWith("--")) {
                    cleanedSql.append(line).append("\n");
                }
            }

            String[] statements = cleanedSql.toString().split(";");
            for (String statement : statements) {
                String trimmed = statement.trim();
                if (!trimmed.isEmpty()) {
                    try (Statement stmt = conn.createStatement()) {
                        stmt.execute(trimmed);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Failed to execute SQL script: {}", scriptPath, e);
            throw new SQLException("Failed to execute SQL script: " + scriptPath, e);
        }
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource == null || dataSource.isClosed()) {
            initDataSource();
        }
        return dataSource.getConnection();
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static synchronized void shutdown() {
        if (dataSource != null && !dataSource.isClosed()) {
            logger.info("Closing HikariCP connection pool...");
            dataSource.close();
            logger.info("Connection pool closed.");
        }
    }
}
