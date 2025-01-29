package com.itwill.spring2.web;

import java.util.List;

import org.apache.ibatis.annotations.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itwill.spring2.domain.Comment;
import com.itwill.spring2.dto.CommentCreateDto;
import com.itwill.spring2.dto.CommentItemDto;
import com.itwill.spring2.dto.CommentUpdateDto;
import com.itwill.spring2.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor //필수 아규먼트를 갖는 생성자 // final 필드를 선언했기 때문
@RestController // 컨트롤러 메서드의 '리턴값'이 뷰의 이름이 아니라 '클라이언트로 직접 응답되는 데이터'임.
@RequestMapping("/api/comment")
public class CommentController {
	
	// final 필드와 (필수 아규먼트를 갖는) 생성자를 이용한 의존성 주입
	private final CommentService commentService;
	
	@GetMapping("/{id}") //{id} : 요청 올때마다 변할 수 있는 숫자값.....
	public ResponseEntity<CommentItemDto> getCommentById(@PathVariable Integer id) { //@PathVariable : 요청uri에 포함되어져있는 변수가 있을 때(요청주소의 일부가 변수처럼 변할 수 있는 값(path variable)일때), {varName} 형식으로 작성.
		// @PathVariable: 요청 주소의 일부가 변수처럼 변할 수 있는 값일 때,
        // 디스패쳐 서블릿이 요청 주소를 분석해서 메서드의 아규먼트로 전달해줌.
		// (i) @PathVariable(name = "id") 처럼 경로 변수(path variable)의 이름을 명시하거나, //속성 name : PathVariable의 이름
        // (ii) 메서드의 파라미터 이름을 경로 변수와 동일하게 선언하면 됨.
		// (Eclipse) Window -> Preferences -> Java Compiler ->
        // "Store information about method parameters (usable via reflection)" 항목을 체크해야 함.
		
		log.debug("getCommentById(id={})", id);
		
		CommentItemDto comment = commentService.readById(id);
		
		return ResponseEntity.ok(comment); //but 클라이언트에게는 메모리에 있는 자바객체인 comment를 보낼 수(응답할 수) 없기 때문에, 자바객체를 문자열(json)로 변환해서 리턴(응답)하려고 jackson-databind(jackson-datatype-jsr310) 라이브러리 추가
		// ResponseEntity<T>
		// 서버가 클라이언트로 보내는 데이터와 응답코드를 함께 설정할 수 있는 객체.
		
		// REST 컨트롤러 메서드가 자바 객체를 리턴하면
        // jackson-databind 라이브러리가 자바 객체를 JSON 문자열로 변환을 담당하고,
        // JSON 문자열이 클라이언트로 전송(응답)됨.
        // jackson-databind 라이브러리의 역할:
        //   1. 직렬화(serialization): 자바 객체 -> JSON
        //   2. 역직렬화(de-serialization): JSON -> 자바 객체
        // jackson-databind 라이브러리에서 
        // Java 8 이후에 생긴 날짜/시간 타입(LocalDate, LocalDateTime)을 JSON으로 변환하기 위해서는
        // jackson-datatype-jsr310 모듈이 필요함(POM.xml에 dependency 추가).
	}
	
	@GetMapping("/all/{postId}")
	public ResponseEntity<List<CommentItemDto>> getAllCommentsByPostId(@PathVariable Integer postId){
		log.debug("getAllCommentsByPostId(postid = {})", postId);
		
		List<CommentItemDto> list = commentService.readByPostId(postId);
		
		return ResponseEntity.ok(list);
	} 
	
	@PostMapping // /api/comment니까 @PostMapping옆에 주소 쓸 이유 없음.
	public ResponseEntity<Integer> registerComment(@RequestBody CommentCreateDto dto){ //API tester에서 500오류(client가 json문자열을 실어서 요청을 보냈는데 이걸 읽으려면 @RequestBody를 붙여야함)나서 @RequestBody를 붙이니까 오류 없어짐. //스크린샷 참고
		//@RequestBody
		//디스패쳐 서블릿이 Ajax 요청에서 요청 패킷 몸통(body)에 포함된 JSON 문자열을 읽고
		//jackson-databind 라이브러리를 사용해서 자바 객체로 변환 후
		//컨트롤러 메서드의 아규먼트로 전달해줌.
		log.debug("registerComment(dto={})", dto);
		
		int result = commentService.create(dto);
		
		return ResponseEntity.ok(result);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> deleteComment(@PathVariable Integer id){
		log.debug("deleteComment(id={})", id);
		
		int result = commentService.delete(id);
		
		return ResponseEntity.ok(result);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Integer> updateComment(@PathVariable Integer id, 
			@RequestBody CommentUpdateDto dto){ //@RequestBody에 댓글 수정내용을 실어서
		log.debug("updateComment(id={}, dto={})", id, dto);
		
		dto.setId(id);
		int result = commentService.update(dto);
		
		return ResponseEntity.ok(result);
	}
}

//servlet-context에 설정한 <context:component-scan base-package="com.itwill.spring2.web" />와 @RestController때문에 스프링 컨테이너가 설정할 수 있음.

//pom.xml에
//<!-- Add-on module to support JSR-310 (Java 8 Date & Time API) data types.-->
//<dependency> 추가
//    <groupId>com.fasterxml.jackson.datatype</groupId>
//    <artifactId>jackson-datatype-jsr310</artifactId>
//    <version>${jackson-version}</version>
//</dependency>

// 클라이언트에게 자바객체를 xml로 변환하거나 json으로 변환해서 응답을 보내줘야함.

//gframework.web.servlet.DispatcherServlet(120)] GET "/spring2/api/comment/all/1", parameters={} //parameters가 없는 이유는 uri가 변수역할을 하기때문에

//@RequestParam @RequestBody, @PathVariable 잘 이용해보기

//REST Controller니까 ResponseEntity 이용...