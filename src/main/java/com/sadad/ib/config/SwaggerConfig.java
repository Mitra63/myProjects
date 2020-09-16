package com.sadad.ib.config;

import static com.google.common.collect.Lists.newArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import java.util.Collections;
@EnableSwagger2
@Configuration
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.sadad.ib.controller"))
				.paths(PathSelectors.ant("/**"))
				.build()
				.apiInfo(apiInfo()).useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, newArrayList(new ResponseMessageBuilder().code(500)
								.message("500 message")
								.responseModel(new ModelRef("Error"))
								.build(),
						new ResponseMessageBuilder().code(403)
								.message("Forbidden!!!!!")
								.build()));
	}

	private ApiInfo apiInfo() {
		return new ApiInfo(
				"My REST API",
				"account API is for create accounts,blocked and nonBlocking Account,show accounts.",
				null,
				null,
				null,
				null,null,Collections.emptyList());
	}

}