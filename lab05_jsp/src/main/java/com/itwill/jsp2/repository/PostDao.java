package com.itwill.jsp2.repository;

import static com.itwill.jsp2.datasourceutil.DataSourceUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.jsp2.datasourceutil.DataSourceUtil;
import com.itwill.jsp2.domain.Post;
import com.zaxxer.hikari.HikariDataSource;

// 웹 MVC 아키텍쳐에서 영속성/저장소 계층(persistence/repository layer)을 담당하는 객체.
// DB CRUD(create/read/update/delete) 기능을 갖고 있는 싱글톤 객체.
// DAO(Data Access Object)
public enum PostDao {
	INSTANCE; // 싱글톤 객체
	
	//로그를 남기기 위해 Logger 객체를 생성
	private static final Logger log = LoggerFactory.getLogger(PostDao.class);
	private final HikariDataSource ds = DataSourceUtil.INSTANCE.getDataSource();
	
	// 포스트 목록 검색에서 사용할 SQL
	private static final String SQL_SELECT_ALL = 
			"select * from posts order by id desc";
	
	public List<Post> select() {
		log.debug(SQL_SELECT_ALL);
		
		List<Post> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement(SQL_SELECT_ALL);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Post post = toPostFromResultSet(rs);
				list.add(post);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, stmt, rs);
		}
		
		return list;
	}
	
	// 포스트 저장(새 글 작성)에서 필요한 SQL 문장을 선언.
	private static final String SQL_INSERT = "insert into posts (title, content, author, created_time, modified_time) "
			+ "values (?, ?, ?, systimestamp, systimestamp)";
	
	public int insert(Post post) {
		log.debug("insert(post={})", post);
		log.debug(SQL_INSERT);
		
		int result = 0;// insert 문장의 실행 결과를 저장하기 위한 지역 변수.
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			//데이터 소스(커넥션 풀)에서 커넥션을 빌려옴.
			conn = ds.getConnection();
			stmt = conn.prepareStatement(SQL_INSERT);
			stmt.setString(1, post.getTitle());
			stmt.setString(2, post.getContent());
			stmt.setString(3, post.getAuthor());
			result = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//사용했던 커넥션을 커넥션 풀에 반환
			close(conn, stmt);
		}
		return result;
	}
	
	//아이디(pk)로 포스트 1개를 검색하는 SQL.
	private static final String SQL_SELECT_BY_ID = "select * from posts where id = ?";
	
	public Post select(int id) {//데이터베이스에서 아이디로 검색한 결과를 Post 객체로 반환함으로써 객체 지향
		log.debug("select(id={})", id);
		log.debug(SQL_SELECT_BY_ID);
		
		Connection conn = null; //커넥션 풀에서 커넥션을 빌려옴.
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Post post = null;
		
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement(SQL_SELECT_BY_ID);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if(rs.next()){ //ResultSet에 레코드가 있으면
				post = toPostFromResultSet(rs);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, stmt, rs); // 사용했던 커넥션 객체를 커넥션 풀에 반환. 
		}
		return post;
	}
	
	// 해당 아이디(PK)의 포스트 1개를 삭제하는 SQL 문장
	private static final String SQL_DELETE_BY_ID = "delete from posts where id = ?";
	
	public int delete(int id) { //아규먼트를 Integer id로 해도 됨
		log.debug("delete(id={})", id);
		log.debug(SQL_DELETE_BY_ID);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		int result = 0; //executeUpdate() 메서드의 리턴 값을 저장하기 위한 변수.
		try {
			conn = ds.getConnection(); // 커넥션 풀에서 커넥션을 빌려옴.
			stmt = conn.prepareStatement(SQL_DELETE_BY_ID);
			stmt.setInt(1, id);
			result = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, stmt); // 사용했던 커넥션을 커넥션 풀에 반환.
		}
		return result;
	}
	
	//업데이트하는 SQL문장
	private static final String SQL_UPDATE = "update posts "
			+ "set title = ?, content = ?, modified_time = systimestamp "
			+ "where id = ?";
	
	public int update(Post post) { //id, title, content를 가지고 있는 객체인 post를 파라미터로 ..
	    log.debug("update(post={})", post);
	    log.debug(SQL_UPDATE);

	    Connection conn = null;
	    PreparedStatement stmt = null;
	    int result = 0;
	    
	    try {
	        conn = ds.getConnection();
	        stmt = conn.prepareStatement(SQL_UPDATE);
	        stmt.setString(1, post.getTitle());
	        stmt.setString(2, post.getContent());
	        stmt.setInt(3, post.getId());
	        result = stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(conn, stmt);
	    }
	    return result;
	}
	
	//search하는 SQL문장
	private static final String SQL_SELECT_BY_TITLE = 
			"select * from posts "
			+ "where upper(title) like upper('%' || ? || '%') "
			+ "order by id desc"; 
	
	private static final String SQL_SELECT_BY_CONTENT = 
			"select * from posts "
			+ "where upper(content) like upper('%' || ? || '%') "
			+ "order by id desc";
	
	private static final String SQL_SELECT_BY_TITLE_OR_CONTENT = 
			"select * from posts "
			+ "where upper(title) like upper('%' || ? || '%') "
			+ "or upper(content) like upper('%' || ? || '%') "
			+ "order by id desc";

	private static final String SQL_SELECT_BY_AUTHOR = 
			"select * from posts "
			+ "where upper(author) like upper('%' || ? || '%') "
			+ "order by id desc";
	
	public List<Post> select(String category, String keyword) {
	    log.debug("select(category={}, keyword={})", category, keyword);

	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    List<Post> result = new ArrayList<>(); // 검색 결과를 저장할 (빈) 리스트.

	    try {
	        conn = ds.getConnection();
	        
	        switch(category) {
	        case "t":
	        	log.debug(SQL_SELECT_BY_TITLE);
	        	stmt = conn.prepareStatement(SQL_SELECT_BY_TITLE);
	        	stmt.setString(1, keyword);
	        	break;
	        case "c":
	        	log.debug(SQL_SELECT_BY_CONTENT);
	        	stmt = conn.prepareStatement(SQL_SELECT_BY_CONTENT);
	        	stmt.setString(1, keyword);
	        	break;
	        case "tc":
	        	log.debug(SQL_SELECT_BY_TITLE_OR_CONTENT);
	        	stmt = conn.prepareStatement(SQL_SELECT_BY_TITLE_OR_CONTENT);
	        	stmt.setString(1, keyword);
	        	stmt.setString(2, keyword);
	        	break;
	        case "a":
	        	log.debug(SQL_SELECT_BY_AUTHOR);
	        	stmt = conn.prepareStatement(SQL_SELECT_BY_AUTHOR);
	        	stmt.setString(1, keyword);
	        	break;
	        }
	        
	        rs = stmt.executeQuery();

	        while (rs.next()) {
	            Post post = toPostFromResultSet(rs);
	            result.add(post);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(conn, stmt, rs);
	    }

	    return result;
	}

	private Post toPostFromResultSet(ResultSet rs) throws SQLException { //ResultSet 객체에서 데이터를 읽어와 Post 객체로 변환
		Integer id = rs.getInt("ID");
		String title = rs.getString("TITLE");
		String content = rs.getString("CONTENT");
		String author = rs.getString("AUTHOR");
		Timestamp createdTime = rs.getTimestamp("CREATED_TIME");
		Timestamp modifiedTime = rs.getTimestamp("MODIFIED_TIME");
		
		Post post = Post.builder().id(id).title(title).content(content).author(author).createdTime(createdTime).modifiedTime(modifiedTime).build();
		
		return post;
	}
}

