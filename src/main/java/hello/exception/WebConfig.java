package hello.exception;

import hello.exception.filter.LogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new LogFilter());
        filterFilterRegistrationBean.setOrder(1); //순서
        filterFilterRegistrationBean.addUrlPatterns("/*"); //모든 경로
//        filterFilterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ERROR); //이 필터는 DispatcherType이 REQUEST, ERROR 일 때 호출됩니다.
        // 아무것도 넣지 않으면 기본값이 DispatcherType.REQUEST 이다. 즉, 클라이언트 요청이 있는 경우에만 필터가 적용된다.(기본일 때) 그러면 필터가 2번 호출되지 않음
        return filterFilterRegistrationBean;
    }

}
