package hello.exception;

import hello.exception.filter.LogFilter;
import hello.exception.interceptor.LogInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "*/ioc", "/error", "/error-page/**"); //오류 페이지 경로
        //Interceptor는 DispatcherType이 따로 설정할 수 있는 방법이 없다. 그러므로 오류 페이지 경로를 excludePathPatterns에 넣어버리면 호출되지 않는다.
    }

//    @Bean
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
