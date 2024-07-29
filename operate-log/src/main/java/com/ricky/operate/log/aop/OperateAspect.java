package com.ricky.operate.log.aop;

import com.alibaba.fastjson.JSON;
import com.ricky.operate.log.annotation.RecordOperate;
import com.ricky.operate.log.converter.Converter;
import com.ricky.operate.log.model.OperateLogDO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/29
 * @className OperateAspect
 * @desc 操作日志切面
 */
@Aspect
@Component
public class OperateAspect {

    @Pointcut("@annotation(com.ricky.operate.log.annotation.RecordOperate)")
    public void pointcut() {
    }

    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            1, 1, 1, TimeUnit.SECONDS, new LinkedBlockingDeque<>(100)
    );

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = proceedingJoinPoint.proceed();
        threadPoolExecutor.execute(() -> {
            try {
                MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
                RecordOperate annotation = methodSignature.getMethod().getAnnotation(RecordOperate.class);

                Class<? extends Converter> converter = annotation.converter();
                Converter logConverter = converter.getDeclaredConstructor().newInstance();
                OperateLogDO operateLogDO = logConverter.convert(proceedingJoinPoint.getArgs()[0]);

                operateLogDO.setDesc(annotation.desc());
                operateLogDO.setResult(result.toString());

                logOperate(operateLogDO);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
        return result;
    }

    /**
     * 记录操作日志
     * @param operateLogDO 操作日志
     */
    private void logOperate(OperateLogDO operateLogDO) {
        System.out.println("insert operateLog " + JSON.toJSONString(operateLogDO));
    }

}
