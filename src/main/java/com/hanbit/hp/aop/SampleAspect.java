package com.hanbit.hp.aop;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SampleAspect {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SampleAspect.class);
	
	@Before("execution(* com.hanbit.hp.controller.WelcomeController.welcome(..))")
	public void doBefore() {
		LOGGER.debug("before welcome");
	}
	
	@AfterReturning(
			pointcut="execution(* com.hanbit.hp.controller.WelcomeController.welcome(..))",
			returning="retVal")
	public void doAfterReturning(Object retVal) {
		LOGGER.debug("after returning welcome");
	}
	
	@AfterThrowing(
			pointcut="execution(* com.hanbit.hp.controller.WelcomeController.welcome(..))",
			throwing="t")
	public void doAfterThrowing(Throwable t) {
		LOGGER.debug("after throwing welcome");
	}
	
	@After("execution(* com.hanbit.hp.controller.WelcomeController.welcome(..))")
	public void doAfter() {
		LOGGER.debug("after welcome");
	}
	
	@Around("execution(* com.hanbit.hp.controller.WelcomeController.welcome(..))")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		Object retVal = null;
		
		LOGGER.debug("before");
		
		try {
			retVal = pjp.proceed();
			LOGGER.debug("after returning");
		}
		catch (Throwable t) {
			LOGGER.debug("after throwing");
			throw t;
		}
		finally {
			LOGGER.debug("after");
		}
		
		if (retVal instanceof Map) {
			Map retMap = (Map) retVal;
			retMap.put("aop", "injected by aop");
		}
		
		return retVal;
	}
		
}