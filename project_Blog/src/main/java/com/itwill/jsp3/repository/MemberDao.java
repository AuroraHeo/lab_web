package com.itwill.jsp3.repository;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.itwill.jsp3.datasourceutil.DataSourceUtil.close;

import com.itwill.jsp3.datasourceutil.DataSourceUtil;
import com.itwill.jsp3.domain.Member;
import com.zaxxer.hikari.HikariDataSource;

public enum MemberDao {
    INSTANCE; //싱글톤
    
    private static final Logger log = LoggerFactory.getLogger(MemberDao.class);
    private final HikariDataSource ds = DataSourceUtil.INSTANCE.getDataSource();
    
    //회원 가입에 필요한 SQL, 메서드
    private static final String SQL_INSERT = 
            "insert into members (username, password, email, created_time, modified_time) "
            + "values (?, ?, ?, systimestamp, systimestamp)";
    
    public int insert(Member member) {
        log.debug("insert(member={})", member );
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
    private static final String SQL_SELECT_BY_USERNAME_AND_PASSWORD = 
            "select * from members where username = ? and password = ?";
    
    public Member select(String username, String password) {
        log.debug("select(username = {}, password = {})", username, password);
        log.debug(SQL_SELECT_BY_USERNAME_AND_PASSWORD);
        
        Connection conn =  null;
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
    private static final String SQL_UPDATE_POINTS = 
            "update members set points = points + ?, modified_time = systimestamp where username = ?";
    
    public int update(String username, Integer points){
        log.debug("update(username = {}, points = {})", username, points);
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
    
    //아이디 존재 여부 확인 메서드
    public boolean isUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM members WHERE username = ?";
        try (Connection conn = ds.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //이메일 존재 여부 확인 메서드
    public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM members WHERE email = ?";
        try (Connection conn = ds.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    
    private Member toMemberFromResultSet(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("id");
        String username = rs.getString("username");
        String password = rs.getString("password");
        String email = rs.getString("email");
        Integer points = rs.getInt("points"); // points 값을 가져옴
        Timestamp createdTime = rs.getTimestamp("created_time");
        Timestamp modifiedTime = rs.getTimestamp("modified_time");
        
        return Member.builder()
                .id(id)
                .username(username)
                .password(password)
                .email(email)
                .points(points) // points 값을 설정
                .createdTime(createdTime)
                .modifiedTime(modifiedTime)
                .build();
    }
    
}
