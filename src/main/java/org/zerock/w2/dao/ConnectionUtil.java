package org.zerock.jdbcex.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

public enum ConnectionUtil {
  INSTANCE;

  private HikariDataSource ds;
  private Properties props;

  ConnectionUtil() {
    try {
      // 설정 파일 로드
      props = new Properties();
      // 클래스패스에서 'application-db.properties' 파일 로드
      props.load(ConnectionUtil.class.getResourceAsStream("/application-db.properties"));

      // 프로퍼티 사용하여 연결 정보 설정
      String dbUrl = props.getProperty("db.url");
      String dbUser = props.getProperty("db.user");
      String dbPassword = props.getProperty("db.password");

      HikariConfig config = new HikariConfig();
      config.setDriverClassName("org.mariadb.jdbc.Driver");
      config.setJdbcUrl(dbUrl);
      config.setUsername(dbUser);
      config.setPassword(dbPassword);
      config.addDataSourceProperty("cachePrepStmts", "true");
      config.addDataSourceProperty("prepStmtCacheSize", "250");
      config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

      ds = new HikariDataSource(config);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("Failed to load properties file", e);
    }
  }

  public Connection getConnection() throws Exception{
    return ds.getConnection();
  }
}
