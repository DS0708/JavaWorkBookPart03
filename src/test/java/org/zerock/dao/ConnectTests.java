package org.zerock.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.zerock.jdbcex.dao.ConnectionUtil;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectTests {
  private Properties props = new Properties();
  private String dbUrl;
  private String dbUser;
  private String dbPassword;

  @BeforeEach
  public void ready() throws Exception{
    // 설정 파일 로드
    props.load(ConnectionUtil.class.getResourceAsStream("/application-db.properties"));
    // 프로퍼티 사용하여 연결 정보 설정
    dbUrl = props.getProperty("db.url");
    dbUser = props.getProperty("db.user");
    dbPassword = props.getProperty("db.password");
  }

  @Test
  public void test1() {
    int v1 = 10;
    int v2 = 10;

    Assertions.assertEquals(v1,v2);
  }

  @Test
  public void testConnection() throws Exception{
    Class.forName("org.mariadb.jdbc.Driver");

    Connection connection = DriverManager.getConnection(
            dbUrl, dbUser, dbPassword
    );

    Assertions.assertNotNull(connection);

    connection.close();
  }

  @Test
  public void testHikariCP() throws Exception{

    HikariConfig config = new HikariConfig();
    config.setDriverClassName("org.mariadb.jdbc.Driver");
    config.setJdbcUrl(dbUrl);
    config.setUsername(dbUser);
    config.setPassword(dbPassword);
    config.addDataSourceProperty("cachePrepStmts", "true");
    config.addDataSourceProperty("prepStmtCacheSize", "250");
    config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

    HikariDataSource ds = new HikariDataSource(config);
    Connection connection = ds.getConnection();

    System.out.println(connection);

    connection.close();
  }
}
