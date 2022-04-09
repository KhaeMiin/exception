package hello.exception.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 에러가 발생하는 페이지 만들기(테스트는 해봐야하니까)
 */
@Slf4j
@Controller
public class ServletExController {

    /**
     * 예외 발생 프름
     * - WAS(여기까지 전파) < 필터 < 서블릿 < 인터셉터 < 컨트롤러(예외발생!)
     */
    @GetMapping("/error-ex")
    public void errorEx() {
        throw new RuntimeException("예외 발생!");
    }

    /**
     * sendError 흐름
     * - WAS(sendError 호출 기록 확인) < 필터 < 서블릿 < 인터셉터 < 컨트롤러(response.sendError) (dispatcherType=REQUEST)
     * WAS는 해당 예외를 처리하는 오류 페이지 정보를 확인한다.
     * (WebServerCustomizer.java > new ErrorPage(RuntimeException.class, "error-page/404")
     * - WAS '/error-page/500' 다시 요청 > 필터 > 서블릿 > 인터셉터 > 컨트롤러(/error-page/404) > View (dispatcherType=ERROR)
     * 서버 내부에서 다시 요청이 된다. (총 필터, 서블릿, 인터셉터, 컨트롤러가 2번씩 호출된다)
     */
    @GetMapping("/error-404")
    public void error404(HttpServletResponse response) throws IOException {
        response.sendError(404, "404 오류!"); //response에 상태 저장해서 보냄
    }

    @GetMapping("/error-500")
    public void error500(HttpServletResponse response) throws IOException {
        response.sendError(500);
    }
}
