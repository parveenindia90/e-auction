package com.auction.seller.app.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation which logs start and end method info
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodStartEndLogger {
    boolean withArgumentsInfo() default false;

    @Aspect
    @Component
    class MethodStartEndLoggerAspect {
        private static final Logger LOGGER = LoggerFactory.getLogger(MethodStartEndLoggerAspect.class);
        private static final String LOG_INFO_START = SellerConstants.LOG_INFO_METHOD_START_MSG.concat(" :: {}");
        private static final String LOG_INFO_WITH_ARGS_START = LOG_INFO_START.concat(" :: argument names: {}, argument values: {}");
        private static final String LOG_INFO_END = SellerConstants.LOG_INFO_METHOD_END_MSG.concat(" :: {}");

        @Around("@annotation(com.auction.seller.app.util.MethodStartEndLogger) && @annotation(methodStartEndLogger)")
        public Object logStartEndMethod(ProceedingJoinPoint pjp, MethodStartEndLogger methodStartEndLogger) throws Throwable {
            if (methodStartEndLogger.withArgumentsInfo()) {
                CodeSignature codeSignature = (CodeSignature) pjp.getSignature();
                LOGGER.info(LOG_INFO_WITH_ARGS_START, codeSignature.getDeclaringType().getSimpleName(), codeSignature.getName(), codeSignature.getParameterNames(), pjp.getArgs());
            } else {
                LOGGER.info(LOG_INFO_START, pjp.getSignature().getDeclaringType().getSimpleName(), pjp.getSignature().getName());
            }

            try {
                return pjp.proceed();
            } finally {
                LOGGER.info(LOG_INFO_END, pjp.getSignature().getDeclaringType().getSimpleName(), pjp.getSignature().getName());
            }
        }
    }
}
