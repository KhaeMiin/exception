package hello.exception.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    /**
     * WebConfig.java에 등록해서 사용하면 됩니다.
     */

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        log.info("call resolver", ex);

        try {
            if (ex instanceof IllegalArgumentException) {
                log.info("IllegalArgumentException resolver to 400");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());//기존 500에러를 덮어버림 SC_BAD_REQUEST 이걸로 sendError됨
                return new ModelAndView(); //ModelAndView 객체에는 아무것도 없음. 뷰를 렌더링하지 않고 정상 흐름으로 서블릿이 리턴된다.
                //ModelAndView 안에 Model이나 View를 넣을 수 있다. 그러면 WAS는 이 객체로 렌더링하게 된다.
//                response.getWriter().println("dddd"); //이런 식으로 json데이터를 보낼수도 있다.
            }


        } catch (IOException e) {
            log.error("resolver ex", e);
        }

        return null; //null로 리턴되면 예외가 다시 터져서 날라감 (기존 났던 에러를 서블릿으로 보냄)
    }

}
