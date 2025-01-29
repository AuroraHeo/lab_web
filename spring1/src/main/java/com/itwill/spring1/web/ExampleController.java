package com.itwill.spring1.web;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itwill.spring1.dto.User;

import lombok.extern.slf4j.Slf4j;

//POJO(Plain Old Java Object): '간단한' 오래된 자바 객체.
//특정 클래스를 상속(extends)하거나, 특정 인터페이스를 구현(implements)할 필요가 없는
//(상위 타입의 특정 메서드들을 반드시 재정의할 필요가 없는) 평범한(내 맘대로 만들 수 있는) 자바 객체.
//스프링 MVC 프레임워크에서는 POJO로 작성된 클래스를 컨트롤러로 사용할 수 있음!(POJO를 맘대로 호출 가능!)
//(비교) HttpServlet을 상속받는 클래스에서는 doGet(HttpServletRequest req, HttpServletResponse resp) 또는 doPost(req, resp)를
//반드시 재정의(override)해야 웹 서비스(요청 처리)가 가능.

@Slf4j
//-> private static final Logger log = LoggerFactory.getLogger(ExampleController.class);
//코드를 애너테이션으로 처리.
@Controller //이게 없으면 ExampleController 클래스가 Handler Mapping에 등록되지 않음. 요청을 처리할 수 있는 controller가 없는 셈.
//-> 클래스가 컨트롤러 컴포넌트임을 설정하는 애너테이션.
//servlet-context.xml 파일에서 <context:component-scan ... /> 태그에서 설정된 패키지와 
//그 하위 패키지에서 @Controller 애너테이션을 사용한 클래스를 찾음.
public class ExampleController {

	@GetMapping("/") //-> GET 방식, 요청 주소가 컨텍스트 루트(예: /spring1/)인것만 요청처리하는 메서드임을 설정하는 애너테이션.
	public String home(Model model) {
		log.debug("home()"); //@Slf4j때문에 가능..
		
		LocalDateTime now = LocalDateTime.now(); // 현재 시간
		model.addAttribute("now", now);
		
		return "home"; //파일 이름(home)을 리턴.
		//-> 컨트롤러 메서드가 문자열을 리턴하면, '디스패쳐 서블릿이 뷰(JSP)의 이름을 찾는 데' 사용됨.
		// servlet-context.xml 파일에서 <bean> 태그에서 설정된 ViewResolver 설정을 사용함.
		// 디스패쳐 서블릿이 뷰 리졸버를 이용해서 요청을 포워드할
		// 뷰의 경로(/WEB-INF/views/returnValue.jsp)를 찾을 수 있음. ex./WEB-INF/views/home.jsp
		// void로 만들면 jsp파일 이름이 .jsp이니까 리턴타입을 String으로 해서 jsp파일 이름을 home으로..
	}
	
	@GetMapping("/example")
	public void ex() { // 위임한 controller가 아규먼트를 넣지도 않고 리턴값을 설정하지도 않고(void) 빈 {}인 메서드를 만들더라도 dispatcherServlet이 jsp를 찾을 수 있음. 
		log.debug("ex()");
		//디스패쳐 서블릿이 ViewResolver를 이용하여 뷰의 이름을 찾는 방법:
		//(i) 컨트롤러의 메서드가 문자열(String)을 리턴하는 경우는 리턴 값이 jsp파일의 이름.
		//(ii) 컨트롤러 메서드의 리턴 타입이 void인 경우, 매핑된 요청주소(/example)가 jsp파일의 이름(example.jsp).
	}
	
	@GetMapping("/ex1")
	/*
	 * 이클립스 메뉴 Window -> Preferences -> Java -> Compiler ->
	 * Store information about method parameters (usable via reflection) 항목 체크.
	 * 자바 컴파일러가 컴파일할 때 메서드 파라미터 정보를 저장해서,
	 * 자바 프로그램이 파라미터 이름을 reflection 기능으로 사용할 수 있도록 함.
	 * 
	 * 컨트롤러 메서드의 파라미터를 선언할 때 @RequestParam 애너테이션을 사용하면,
	 * 디스패쳐 서블릿이 컨트롤러 메서드를 호출할 때, 요청 파라미터의 값을 메서드 아규먼트로 전달하게 됨.
	 * 
	 * @RequestParam 애너테이션에 name 속성이 설정되지 않은 경우에는,
	 * 메서드 파라미터 이름(age)으로 요청 파라미터 값을 찾음. (예) request.getParameter("age")
	 * 
	 * @RequestParam 애너테이션에 name 속성이 설정된 경우,
	 * name 속성의 값으로 요청 파라미터 값을 찾음. (예) request.getParameter("username") == ex1(@RequestParam(name = "username") String name, ...) {...}
	 * 
	 * 디스패쳐 서블릿을 컨트롤러 메서드의 아규먼트(들)을 전달하기 위해서 
	 * 가능한 경우 타입 변환도 자동으로 수행. (예) Integer.parseInt(reqeust.getParameter("age")) == int age
	 * 
	 * @RequestParam 애너테이션의 defaultValue 속성 값을 설정하면
	 * 요청 파라미터 값이 없는 경우에도 기본값으로 사용할 수 있음. (예) @RequestParam(defaultValue = "0") int age
	 */
// example.jsp에
//	<h1>Example Page</h1>
//    <section>
//        <h2>GET 방식 요청</h2>
//        <c:url value="/ex1" var="ex1Page"/>
//        <form method="get" action="${ ex1Page }">
//            <input type="text" name="username" placeholder="이름 입력"/>
//            <input type="number" name="age" placeholder="나이 입력"/>
//            <input type="submit" value="제출"/>
//        </form>
//    </section>
	public void ex1(@RequestParam(name = "username") String name, @RequestParam(defaultValue = "0") int age, Model model) {
		log.debug("ex1(username={}, age={})", name, age);
		
		//요청 파라미터 값들로 User타입의 객체를 생성.
		User user = User.builder().username(name).age(age).build(); // User클래스에서 @Builder했기 때문에 가능.
	
		//user객체를 뷰(jsp)에게 전달. << ex1메서드 아규먼트에 model를 추가하기.
		model.addAttribute("user", user); //model에 속성을 넣음
	}
	
	//POST방식의 /ex2 요청 ->  ex1.jsp를 보여주는 메서드
	@PostMapping("/ex2")
//	public String ex2(@RequestParam(name = "username") String name, @RequestParam(defaultValue = "0") int age) {
// username과 age(요청파라미터)를 필드로 선언한 User클래스(객체)를 생성했으니 아규먼트 코드를 더 간단하게 할 수 있음.
	public String ex2(/*@ModelAttribute*/ User user) {
		// 디스패쳐 서블릿은 컨트롤러 메서드를 호출하기 위해서,
		// (1) User클래스의 기본 생성자를 호출.
		// (2) 요청 파라미터 값을 읽어서(request.getParameter) 요청 파라미터 이름으로 User클래스의 setter을 호출.
		//	요청 파라미터 값이 없을 경우, 기본타입인 int는 에러가 발생하지만,
		//	wrapper클래스타입인 Integer는 에러가 발생하지 않음.
		// (3) User타입 객체를 Controller 메서드의 아규먼트로 전달.
		// (4) Controller 메서드의 아규먼트를 Model의 속성으로 추가(spring 버전 6 이상부터는 @ModelAttribute의 역할까지 해주기때문에 @ModelAttribute 생략 가능).
		
		
		log.debug("ex2(user={})", user);
		
		return "ex1";
	}
	
	@GetMapping("/test")
	public void test() {
		log.debug("test()");
	}	
	
	@GetMapping("/test2")
	public String test2() {
		log.debug("test2()");
		
		return "forward:/test"; //test라는 요청주소로 forward해라
		//컨트롤러 메서드가 "forward:"으로 시작하는 문자열을 리턴
		//-> 포워드(forward) 방식의 이동.
		//-> 최초 요처 주소가 변경되지 않음. request, response 객체가 유지.
	}
	
	@GetMapping("/test3")
	public String test3() {
		log.debug("test3()");
		
		return "redirect:/test"; // /test3 요청 > redirect(/test)응답 (HTTP 302) > /test 요청 > test.jsp 응답
		//컨트롤러 메서드가 "redirect:"으로 시작하는 문자열을 리턴.
		//-> 리다이렉트(redirect) 방식의 이동.
		// HTTP 302(redirect) 응답 이후에 클라이언트가 요청을 다시 보냄.
		// 최초 요청 URL이 리다이렉트되는 URL로 바뀜.
		//기존의 request, reponse객체가 없어지고 새로운 request, response객체가 만들어짐.
	}
	
	@GetMapping("/rest1")
	@ResponseBody //클래스가 아닌 '메서드'에 사용하는 애너테이션. 뷰이름을 찾지 않고, 리턴값인 '문자열이 그대로 클라이언트에게 응답으로' 감.
	//-> 컨트롤러 메서드가 리턴하는 값이 뷰를 찾기 위한 문자열이 아니라, 클라이언트로 직접 응답으로 전송되는 데이터.
	public String rest1() {
		log.debug("rest1()");
		
		return "Hello, 안녕하세요!";
	}
	
	@GetMapping("/rest2") @ResponseBody //-> 메서드의 리턴값이 클라이언트에게 직접 전송되는 데이터.
	public User rest2() {
		log.debug("rest2()");
		
		return User.builder().username("홍길동").age(16).build(); // 자바 객체를 JSON으로 변환하여 응답 보냄
		//-> 컨트롤러가 리턴한 자바 객체를 jackson-databind 라이브러리에서
		// JSON(JavaScript Object Notation) 형식의 문자열로 변환하고
		// 클라이언트에게는 JSON 문자열이 응답으로 전송됨.
		// (참고) jackson-databind의 기능: Java 객체 <---(Jackson Data Bind)---> JSON 문자열
		//Jackson Data Bind 라이브러리(의존성)를 pom.xml에 설정했었음
	}
	
}


// forward: 같은 서버 내에서만 돌기때문에 최초 요청주소가 변하지 않음.
//이클립스가 lombok를 사용하게끔 다운로드했기 때문에 애너테이션만 넣어주면 코드가 간단해짐.
//dispatcherServlet이 호출할 메서드를 만들면 됨.

//POCO(Plain Old C++(C#) Object)

//RequestParam(defaultValue = "0") >> 기본값을 0으로 설정

//클래스에 사용하는 애너테이션 : @Controller, @RestController
//메서드에 사용하는 애너테이션 : @GetMapping, @PostMapping, @ResponseBody
//아규먼트에 사용하는 애너테이션 : @RequestParam, @@ModelAttribute