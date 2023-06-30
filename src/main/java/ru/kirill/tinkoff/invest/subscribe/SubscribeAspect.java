package ru.kirill.tinkoff.invest.subscribe;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
@RequiredArgsConstructor
public class SubscribeAspect {

    @Pointcut
    public void subscribeMethodType() {

    }

    @After(value = "subscribeMethodType()")
    public void validateWebService(JoinPoint jp) {

    }
}