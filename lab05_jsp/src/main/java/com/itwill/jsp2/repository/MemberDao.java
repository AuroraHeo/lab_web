package com.itwill.jsp2.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.itwill.jsp2.datasourceutil.DataSourceUtil.close;

import com.itwill.jsp2.datasourceutil.DataSourceUtil;
import com.itwill.jsp2.domain.Member;
import com.zaxxer.hikari.HikariDataSource;

public enum MemberDao {
	INSTANCE; // 싱글톤
	
	private static final Logger log = LoggerFactory.getLogger(MemberDao.class);
	private final HikariDataSource ds = DataSourceUtil.INSTANCE.getDataSource();
	
	//회원 가입에 필요한 SQL, 메서드
	private static final String SQL_INSERT = 
		    "insert into members (username, password, email, created_time, modified_time) " +
		    "values (?, ?, ?, systimestamp, systimestamp)";
	
	
	public int insert(Member member) {
		log.debug("insert(member={})", member);
		log.debug(SQL_INSERT);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		int result = 0;
		
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement(SQL_INSERT);
			stmt.setString(1, member.getUsername());
			stmt.setString(2, member.getPassword());
			stmt.setString(3, member.getEmail());
			result = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, stmt);
		}
		
		return result;
	}
	
	//로그인할 때 필요한 SQL, 메서드
	private static final String SQL_SELECT_BY_USERNAME_AND_PASSWORD = "select * from members where username = ? and password = ?";
	
	public Member select(String username, String password) {
		log.debug("select(username={}, password={})", username, password);
		log.debug(SQL_SELECT_BY_USERNAME_AND_PASSWORD);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Member member = null;
		
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement(SQL_SELECT_BY_USERNAME_AND_PASSWORD);
			stmt.setString(1, username);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			if(rs.next()) {
				member = toMemberFromResultSet(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, stmt, rs);
		}
		
		return member;
	}
	
	// members 테이블의 points 컬럼을 업데이트하는 SQL, 메서드
	private static final String SQL_UPDATE_POINTS = "update members set points = points + ?, modified_time = systimestamp"
			+ " where username = ?";
	
	public int update(String username, Integer points) {
	    log.debug("update(username={}, points={})", username, points);
	    log.debug(SQL_UPDATE_POINTS);
	    
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    int result = 0;

	    try {
	        conn = ds.getConnection();
	        stmt = conn.prepareStatement(SQL_UPDATE_POINTS);
	        stmt.setInt(1, points);
	        stmt.setString(2, username);
	        result = stmt.executeUpdate();
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    } finally {
	        close(conn, stmt);
	    }
	    return result;
	}

	private Member toMemberFromResultSet(ResultSet rs) throws SQLException {
		Integer id = rs.getInt("id");
		String username = rs.getString("username");
		String password = rs.getString("password");
		String email = rs.getString("email");
		Timestamp createdTime = rs.getTimestamp("created_time");
		Timestamp modifiedTime = rs.getTimestamp("modified_time");  // 대소문자 구분 X
		
		return Member.builder()
				.id(id)
				.username(username)
				.password(password)
				.email(email)
				.createdTime(createdTime)
				.modifiedTime(modifiedTime)
				.build();
	}
}
