package com.wangboak.noteassistant.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author wangboak
 */
@Configuration
public class Knife4jConfiguration {

    @Bean
    public Docket docketBean() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(new ApiInfoBuilder()
                        .description("# note assistant Restful APIs")
                        .title("note assistant API")
                        .version("1.0")
                        .build())
                .groupName("note assistant openapi")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wangboak.noteassistant"))
                .paths(PathSelectors.any())
                .build()
                .directModelSubstitute(Long.class, String.class);

    }
}