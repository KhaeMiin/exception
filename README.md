# exception
예외 처리와 오류 페이지

## 프로젝트 설정
1. Project:
	- Gradle Project
	- Language: Java 11
	- Spring Boot: 2.6.5
2.  Dependencies
	- SpringWeb
	- Thymeleaf
	- Lombok
	- Validation
3. Pachaging: **Jar**
	- 내장 서버(톰캣등)을 사용하고, 'webapp'경로도 사용하지 않습니다. (내장 서버 사용에 최적화)

<br>

---

## 스프링부트로 오류 페이지 처리하기

> 오류가 발생했을 때 오류 페이지로 `/error`를 기본으로 요청하게 된다.(룰이니 기억하자) <br>
> 이 때 스프리이 부트가 자동 등록한 `BasicErrorController`는 이 경로를 기본으로 받는다. <br>
> `BasicErrorController`는 기본적인 로직이 모두 개발되어 있다. <br>
> `BasicErrorController`가 제공하는 룰과 우선순위에 따라서 등록하면 된다. <br>

<br><br>
BasicErrorController의 처리 순서

1. 뷰 템플릿
	- resources/templates/error/500.html 	   
	- resources/templates/error/5xx.html
2. 정적 리소스(static, public)
	- resources/static/error/400.html
	- resources/static/error/404.html
	- resources/static/error/4xx.html
3. 적용 대상이 없을 때 뷰 이름(error)
	- resources/templates/error.html

<br>

해당 경로 위치에 HTTP 상태 코드 이름의 뷰 파일을 넣어두면 된다. <br>
뷰 템플릿이 정적 리소스보다 우선순위가 높고, 404, 500처럼 구체적인 것이 5xx처럼 덜 구체적인 것 보다 우선순위가 높다.<br>
 5xx, 4xx 라고 하면 500대, 400대 오류를 처리해준다.
