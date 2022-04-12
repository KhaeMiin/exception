package hello.exception.exhandler.advice;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ControllerAdvice는 대상으로 지정한 여러 컨트롤러에 @ExceptionController, @initBinder 기능을 부여해주는 역할(AOP처럼)
 * @ControllerAdvice에 대상을 지정하지 않으면 모든 컨트롤러에 적용된다.
 * (annotations = RestController.class) 이런 식으로 지정해주면 @RestController가 붙은 클래스만 적용 (특정 컨트롤러 지정도 가능)
 * (basePackages = "hello.exception.api") 특정 패키지를 직접 지정할 수 있다. (기본적으로 패키지정도는 지정해준다)
 */
@Slf4j
@RestControllerAdvice(basePackages = "hello.exception.api") //@ControllerAdvice에 ResponseBody 붙음
public class ExControllerAdvice {

    /**
     * 지저분하게 서블릿 컨테이너까지 예외가 올라가지 않고 여기서 끝이남.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST) //이 어노테이션을 안붙이면 아래 코드가 실행되면서 일단 예외가 해결된 상태이기 때문에 Status가  200이 된다.
    //하지만 우리가 원하는 것은 Status 400이다. 위 어노테이션을 붙여서 상태코드를 바꾸자.
    @ExceptionHandler(IllegalArgumentException.class) // 현재 컨트롤러에서 IllegalArgumentException 발생하면 (자식 예외 포함됨) 아래 코드 실행(하지만 @RestCotrollerAdvice가 붙은 클래스)
    // ({A.class, B.class})로 여러가지 예외를 지정할 수 있다.
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage()); //현재 컨트롤러는 RestController: JSON으로 ErrorResult가 반환됨
    }

    @ExceptionHandler //(예외클래스.class)생략시 메서드 파라미터에 있는 예외 클래스로 등록됨
    public ResponseEntity<ErrorResult> userHandler(UserException e) { //ResponseEntity 반환으로 메시지에 값 넣고 상태코드 보내기
        log.error("[exceptionHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)//500
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) { //최상위 예외클래스
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("EX", "내부 오류");

    }


    //현재 컨트롤러가 RestController가 아닌 경우 아래 코드와 같이 view를 직접 반환할 수도 있다. (ModelAndView 가능)
    //MVC 처리할 경우 잘 쓰진 않음.(BasicErrorController를 사용함) 대부분 API 예외를 쓸 경우에 사용하니 아래코드를 사용할 일은 없다.
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)//500
//    @ExceptionHandler
//    public String exHandler(Exception e) { //최상위 예외클래스
//        log.error("[exceptionHandler] ex", e);
//        return "error/500";
//
//    }

}
