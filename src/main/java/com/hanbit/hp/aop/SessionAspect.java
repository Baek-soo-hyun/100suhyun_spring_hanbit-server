package com.hanbit.hp.aop;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component //스프링 빈이라는 걸 알려주기 위해서
@Order(3) // 우선 순위를 지정
public class SessionAspect {
	
	@Before("@annotation(com.hanbit.hp.annotation.SignInRequired)")
	public void checkSignedIn() {
		ServletRequestAttributes requestAttributes =
		(ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = null;
		
		if(session.getAttribute("signedIn") == null ||
				!(Boolean) session.getAttribute("signedIn")) {
			throw new RuntimeException("로그인이 필요합니다.");
		}
	}
	
}
