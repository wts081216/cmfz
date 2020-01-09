package com.baizhi.wts.Aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CacheAspect {
    @Autowired
    RedisTemplate redisTemplate;

    @Around(value = "@annotation(com.baizhi.wts.annotation.AddOrSelectAnnotation)")
    //添加和查询
    public Object addOrSelectCatch(ProceedingJoinPoint proceedingJoinPoint) {
        //问题：缓存中的数据如何设计？
        //Key:(id) 原始类的类的全限定名   key: 方法名+参数   value: 数据
        String clazz = proceedingJoinPoint.getTarget().getClass().toString();//获取类名
        System.out.println("clazz==============" + clazz);
        String name = proceedingJoinPoint.getSignature().getName();//获取方法名
        Object[] args = proceedingJoinPoint.getArgs();//获取参数
        //对key进行拼接
        String key = name;
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            key += arg;
        }
        //查询redis之前 先查询redis中的换成数据
        Object o = redisTemplate.opsForHash().get(clazz, key);
        //如果查询到直接返回缓存中的数据
        if (o != null) {
            return o;
        }
        //如果查询不到 数据库查询 将数据添加至缓存
        //proceed（） 放行方法  返回值为切入方法的返回值
        try {
            Object proceed = proceedingJoinPoint.proceed();
            redisTemplate.opsForHash().put(clazz, key, proceed);//将数据放入缓存中
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

    @Around(value = "@annotation(com.baizhi.wts.annotation.ClearCatchAnnotation)")
    //删除
    public Object clearCatch(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String s = proceedingJoinPoint.getTarget().getClass().toString();
        redisTemplate.delete(s);
        return proceedingJoinPoint.proceed();
    }
}
