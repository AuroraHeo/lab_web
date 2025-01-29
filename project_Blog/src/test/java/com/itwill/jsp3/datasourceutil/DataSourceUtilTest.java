package com.itwill.jsp3.datasourceutil;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariDataSource;

public class DataSourceUtilTest {
	private static final Logger log = LoggerFactory.getLogger(DataSourceUtilTest.class);
	private DataSourceUtil dsUtil = DataSourceUtil.INSTANCE;
	private HikariDataSource ds = DataSourceUtil.INSTANCE.getDataSource();
	
	@Test
	public void test() throws SQLException {
		Connection conn = ds.getConnection();
		Assertions.assertNotNull(conn);
		log.debug("conn = {}", conn);
		
		DataSourceUtil.close(conn, null);
		log.debug("커넥션 반환");
	}
}
