package com.ctsig.base.config;

import com.ctsig.base.filter.CorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 过滤器配置
 *
 * @author wangan
 * @date 2017/8/9
 */
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean corsFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CorsFilter());
        registration.addUrlPatterns("/*");
        registration.setName("CorsFilter");
        registration.setOrder(1);
        return registration;
    }
}
