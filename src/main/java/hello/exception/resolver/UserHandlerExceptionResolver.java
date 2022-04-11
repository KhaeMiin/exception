package hello.exception.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.exception.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 깔끔하게 ExceptionHandler에서 끝날 수 있도록 하기
 * 서버에서 예외가 터지면 그 예외가 서블릿 > WAS까지 전달되고 WAS는 오류페이지를 확인하고 다시 서블릿 > 컨트롤러 가서 view를 응답했다.
 * 이런식으로 과정이 너무 복잡하다.
 * WAS까지 예외를 그대로 보내지 말고 바로 ExceptionHandler에서 끝내자 (따라서 서블릿 컨테이너까지 에러가 올라가지 않게)
 */
@Slf4j
public class UserHandlerExceptionResolver implements HandlerExceptionResolver {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        try {

            if (ex instanceof UserException) {
                log.info("UserException resolver tp 400");
                String acceptHeader = request.getHeader("accept");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); //response로 상태코드를 바꿔줌

                if ("application/json".equals(acceptHeader)) { //APPLICATION/JSON 일 경우
                    Map<String, Object> errorResult = new HashMap<>();
                    errorResult.put("ex", ex.getClass());
                    errorResult.put("message", ex.getMessage());

                    String result = objectMapper.writeValueAsString(errorResult); //JSON을 문자로 변환

                    response.setContentType("application/json");
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().write(result);

                    return new ModelAndView();
                } else {
                    // TEXT/HTML
                    return new ModelAndView("error/500");
                }
            }

        } catch (IOException e) {
            log.error("resolver ex", ex);
        }

        return null;
    }
}
