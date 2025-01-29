package com.itwill.jsp3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.itwill.jsp3.datasourceutil.DataSourceUtil.close;

import com.itwill.jsp3.datasourceutil.DataSourceUtil;
import com.itwill.jsp3.domain.Post;
import com.zaxxer.hikari.HikariDataSource;

public enum PostDao {
	INSTANCE;
	
	private static final Logger log = LoggerFactory.getLogger(PostDao.class);
	private final HikariDataSource ds = DataSourceUtil.INSTANCE.getDataSource();
	
	//포스트 '목록' 검색에서 사용할 SQL 문장, 메서드
	private static final String SQL_SELECT_ALL = 
			"select * from posts order by id desc";
	
	public List<Post> select(){
		log.debug(SQL_SELECT_ALL);
		
		List<Post> list = new ArrayList<Post>();
		
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

	// 포스트 '새 글' 작성에서 필요한 SQL 문장, 메서드
	private static final String SQL_INSERT = 
			"insert into posts "
			+ "(title, content, author, image_url, view_count, created_time, modified_time)"
			+ "values (?, ?, ?, ?, 0, systimestamp, systimestamp)";
	
	public int insert(Post post) {
		log.debug("insert(post={})", post);
		log.debug(SQL_INSERT);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		int result = 0;
		
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement(SQL_INSERT);
			stmt.setString(1, post.getTitle());
			stmt.setString(2, post.getContent());
			stmt.setString(3, post.getAuthor());
			
			if(post.getImageUrl() != null) {
				stmt.setString(4, post.getImageUrl()); // 이미지 첨부한 경우
			} else {
				stmt.setNull(4, java.sql.Types.VARCHAR); // 이미지 첨부하지 않은 경우
			}
			
			result = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, stmt);
		}
		return result;
	}
	
	//id(pk)로 포스트 1개를 검색하는 SQL 문장, 메서드
	private static final String SQL_SELECT_BY_ID = 
			"select * from posts where id = ?";
	
	public Post select(int id) {
		log.debug("select(id={})", id);
		log.debug(SQL_SELECT_BY_ID);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Post post = null;
		
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement(SQL_SELECT_BY_ID);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if(rs.next()) {
				post = toPostFromResultSet(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, stmt, rs);
		}
		return post;
	}
	
	//해당 id(pk)의 포스트 1개를 삭제하는 SQL 문장, 메서드
	private static final String SQL_DELETE_BY_ID = 
			"delete from posts where id = ?";
	
	public int delete(int id) {
		log.debug("delete(id = {})", id);
		log.debug(SQL_DELETE_BY_ID);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		int result = 0;
		
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement(SQL_DELETE_BY_ID);
			stmt.setInt(1, id);
			result = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, stmt);
		}
		
		return result;
	}
	
	//업데이트하는 SQL 문장, 메서드
	public int update(Post post) {
	    StringBuilder sql = new StringBuilder("update posts set title = ?, content = ?, modified_time = systimestamp");
	    
	    // image_url이 존재할 때만 SQL 문장에 포함
	    if (post.getImageUrl() != null) {
	        sql.append(", image_url = ?");
	    }
	    
	    sql.append(" where id = ?");

	    Connection conn = null;
	    PreparedStatement stmt = null;
	    int result = 0;

	    try {
	        conn = ds.getConnection();
	        stmt = conn.prepareStatement(sql.toString());
	        stmt.setString(1, post.getTitle());
	        stmt.setString(2, post.getContent());
	        
	        // image_url이 존재할 때만 setString 실행
	        if (post.getImageUrl() != null) {
	            stmt.setString(3, post.getImageUrl());
	            stmt.setInt(4, post.getId());
	        } else {
	            stmt.setInt(3, post.getId());
	        }

	        result = stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(conn, stmt);
	    }
	    return result;
	}
	
	// 콤보박스 search하는 SQL 문장, 메서드
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

	// 조회수 1 올리는 SQL 문장, 메서드
	private static final String SQL_UPDATE_VIEW_COUNT = 
			"update posts set view_count = view_count + 1 where id = ?";
	
	public void increaseViewCount(int id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement(SQL_UPDATE_VIEW_COUNT);
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, stmt);
		}
	}
		
	// offset 5개 게시글
	private static final String SQL_SELECT_OFFSET_5 = "SELECT * FROM "
			+ "(" + "  SELECT a.*, ROWNUM rnum FROM ("
			+ "    SELECT * FROM posts ORDER BY id DESC" + "  ) a " 
			+ "  WHERE ROWNUM <= ?" + ") " + "WHERE rnum > ?";

	public List<Post> read(int offset, int limit) {
		List<Post> list = new ArrayList<>(); // 빈 리스트로 초기화

		try (Connection conn = ds.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_OFFSET_5)) {

			stmt.setInt(1, offset + limit); // ROWNUM <= (offset + limit)
			stmt.setInt(2, offset); // rnum > offset

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Post post = toPostFromResultSet(rs);
					list.add(post);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// 로그를 남기거나 사용자 정의 예외로 래핑 가능
		}

		return list;
	}
	
	// 최신순으로 게시글을 가져오는 SQL 문장, 메서드
	private static final String SQL_SELECT_RECENT_POSTS = 
	    "SELECT * FROM (SELECT posts.*, ROWNUM rnum FROM (SELECT * FROM posts ORDER BY created_time DESC) posts WHERE ROWNUM <= ?) WHERE rnum > 0";

	public List<Post> selectRecentPosts(int limit) {
	    log.debug(SQL_SELECT_RECENT_POSTS);
	    
	    List<Post> list = new ArrayList<>();
	    
	    try (Connection conn = ds.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_RECENT_POSTS)) {
	        stmt.setInt(1, limit);
	        
	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                Post post = toPostFromResultSet(rs);
	                list.add(post);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return list;
	}

	// 조회수가 높은 게시글을 가져오는 SQL 문장, 메서드
	private static final String SQL_SELECT_POPULAR_POSTS = 
	    "SELECT * FROM (SELECT posts.*, ROWNUM rnum FROM (SELECT * FROM posts ORDER BY view_count DESC, created_time ASC) posts WHERE ROWNUM <= ?) WHERE rnum > 0";

	public List<Post> selectPopularPosts(int limit) {
	    log.debug(SQL_SELECT_POPULAR_POSTS);
	    
	    List<Post> list = new ArrayList<>();
	    
	    try (Connection conn = ds.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_POPULAR_POSTS)) {
	        stmt.setInt(1, limit);
	        
	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                Post post = toPostFromResultSet(rs);
	                list.add(post);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return list;
	}

	//글 수정하기에서 이미지 삭제하는 SQL, 메서드
	public void removeImage(int postId) {
	    log.debug("removeImage(postId={})", postId);
	    
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    
	    try {
	        conn = ds.getConnection();
	        stmt = conn.prepareStatement("UPDATE posts SET image_url = NULL WHERE id = ?");
	        stmt.setInt(1, postId);
	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(conn, stmt);
	    }
	}
	
	//게시글 갯수 카운트 하는 메서드(페이징 처리)
	private static final String SQL_TOTAL_COUNT_OF_POST = "select count(*) from posts";

	public int countPosts() { 
		Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    int count = 0;
	    
	    try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement(SQL_TOTAL_COUNT_OF_POST);
			rs = stmt.executeQuery();
			if (rs.next()) { 
				count = rs.getInt(1); 
			}
	    } catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, stmt, rs);
		}
	    return count;
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

	
	private Post toPostFromResultSet(ResultSet rs) throws SQLException {
		Integer id = rs.getInt("id");
		String title = rs.getString("title");
		String content = rs.getString("content");
		String imageUrl = rs.getString("image_url");
		String author = rs.getString("author");
		Integer viewCount = rs.getInt("view_count");
		Timestamp createdTime = rs.getTimestamp("created_time");
		Timestamp modifiedTime = rs.getTimestamp("modified_time");
		
		Post post = Post.builder().id(id).title(title).content(content).imageUrl(imageUrl).author(author).viewCount(viewCount).createdTime(createdTime).modifiedTime(modifiedTime).build();
		
		return post;
	}
}
