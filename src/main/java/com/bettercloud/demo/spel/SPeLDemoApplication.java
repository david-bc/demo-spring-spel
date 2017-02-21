package com.bettercloud.demo.spel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.expression.AccessException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.MethodExecutor;
import org.springframework.expression.MethodResolver;
import org.springframework.expression.TypedValue;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @Primary
    public EvaluationContext spelEvaluationContextProvider() {
        return new StandardEvaluationContext();
    }

    @Bean
    @Qualifier("mathContext")
    public EvaluationContext mathEvaluationContext() {
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        standardEvaluationContext.addMethodResolver((context, targetObject, name, argumentTypes) -> {
            if (!"pow".equals(name)) return null;
            return (context1, target, arguments) -> new TypedValue(Math.pow((int)arguments[0], (int)arguments[1]));
        });
        return standardEvaluationContext;
    }

	@Autowired private SpelExpressionParser expressionParser;
    @Autowired
    @Qualifier("mathContext")
    private EvaluationContext mathContext;

	@GetMapping("/math")
	public Double math(@RequestParam("eq") String eq) {
		return expressionParser.parseExpression(eq).getValue(mathContext, new Object(), Double.class);
	}
}
