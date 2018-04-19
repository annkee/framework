package com.ctsig.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置
 *
 * @author wangan
 * @date 2018/01/05
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(new String[]{"/webjars/**", "swagger-ui.html"})
                .addResourceLocations(new String[]{"classpath:/META-INF/resources/webjars/",
                        "classpath:/META-INF/resources/"});

    }

    @Bean
    public Docket restApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ctsig.api"))
                .paths(PathSelectors.any()).build();
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .contact(new Contact("wangan", "", ""))
                .title("myframework")
                .version("V1.0.0")
                .description("<strong>开放API接口说明</strong><br/><br/>" +
                        "<strong>接口返回成功码：</strong><br/>" +
                        "200 成功<br/><br/>" +
                        "<strong>接口返回错误码：</strong><br/>" +
                        "400 客户端请求有误<br/>" +
                        "404 路径有误<br/>" +
                        "415 请求头有误<br/>" +
                        "500 内部服务器异常<br/>" +
                        "504026 订单修改失败<br/>" +
                        "504027 订单不存在<br/>" +
                        "504028 实施单修改失败<br/>" +
                        "504029 实施单不存在<br/>" +
                        "504025 参数错误<br/>"
                )
                .build();
    }
}
