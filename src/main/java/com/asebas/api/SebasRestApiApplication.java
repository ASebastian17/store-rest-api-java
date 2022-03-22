package com.asebas.api;

import com.asebas.api.filters.AuthFilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class SebasRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SebasRestApiApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        source.registerCorsConfiguration("/**", config);
        registrationBean.setFilter(new CorsFilter(source));
        registrationBean.setOrder(0);

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<AuthFilter> filterRegistrationBean() {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        AuthFilter authFilter = new AuthFilter();
        registrationBean.setFilter(authFilter);
        registrationBean.addUrlPatterns("/api/users");
        registrationBean.addUrlPatterns("/api/users/");
        registrationBean.addUrlPatterns("/api/users/u/*");
        registrationBean.addUrlPatterns("/api/clients/*");
        registrationBean.addUrlPatterns("/api/categories/*");
        registrationBean.addUrlPatterns("/api/payments/*");
        registrationBean.addUrlPatterns("/api/providers/*");
        registrationBean.addUrlPatterns("/api/products/*");
        registrationBean.addUrlPatterns("/api/sales/*");

        return registrationBean;
    }
}
