package com.bettercloud.demo.spel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SPeLDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SPeLDemoApplication.class, args);
	}

	@Bean
	public SpelExpressionParser spelExpressionParser() {
		return new SpelExpressionParser();
	}

	@Bean
	@Scope("prototype")
	public EvaluationContext spelEvaluationContextProvider() {
		return new StandardEvaluationContext();
	}

	@Autowired private SpelExpressionParser expressionParser;

	@GetMapping("/math")
	public Double math(@RequestParam("eq") String eq) {
		return expressionParser.parseExpression(eq).getValue(Double.class);
	}
}
