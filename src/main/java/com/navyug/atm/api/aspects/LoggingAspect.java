package com.navyug.atm.api.aspects;


import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LoggingAspect {
	private static final Logger LOGGER = LogManager.getLogger(LoggingAspect.class);
	 
    //AOP expression for which methods shall be intercepted
    @Around("execution(* com.navyug.atm.api..*(..)))")
    public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable 
    {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
         
        //Get intercepted method details
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
         
        final StopWatch stopWatch = new StopWatch();
         
        //Measure method execution time
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();
 
        //Log method execution time
        LOGGER.info("Execution time of " + className + "." + methodName + " :: " + stopWatch.getTotalTimeMillis() + " ms");
 
        return result;
    }
    
    @AfterReturning(value = "execution(* com.navyug.atm.api..*.*(..))", 
			returning = "result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
    	LOGGER.info("{} returned with value {}", joinPoint, result);
	}
	
	@After(value = "execution(* com.navyug.atm.api..*.*(..))")
	public void after(JoinPoint joinPoint) {
		LOGGER.info("after execution of {}", joinPoint);
	}
	
	@Before("execution(* com.navyug.atm.api..*.*(..))")
	public void before(JoinPoint joinPoint){
		LOGGER.info("before execution of {}", joinPoint);
	}
	
	@AfterThrowing(pointcut = "execution(* com.navyug.atm.api..*.*(..))", throwing = "ex")
	public void doRecoveryActions(JoinPoint joinPoint, Throwable ex) {
		String methodName = joinPoint.getSignature().getName();
		String stuff = joinPoint.getSignature().toString();
		String arguments = Arrays.toString(joinPoint.getArgs());
		LOGGER.error("We have caught exception in method: " + methodName + " with arguments " + arguments
				+ "\nand the full toString: " + stuff + "\nthe exception is: " + ex.getMessage());
	}
	
	
	@Around("execution(* com.navyug.atm.api..*.*(..))")
	public Object exceptionHandlerWithReturnType(ProceedingJoinPoint joinPoint) throws Throwable{
		  Object obj = null;
	    try {
	       obj = joinPoint.proceed();
	    } catch(Exception ex) {
	        throw ex;
	    }
		return obj;
	}

}
