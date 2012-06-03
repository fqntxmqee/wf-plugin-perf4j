
package org.webframe.plugins.perf4j.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.perf4j.StopWatch;
import org.perf4j.log4j.Log4JStopWatch;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.PathMatcher;

/**
 * Perf4j拦截器
 * 
 * @author <a href="mailto:guoqing.huang@foxmail.com">黄国庆</a>
 * @since 2012-6-2 下午6:37:09
 * @version
 */
@Service
public class Perf4jInterceptor implements MethodBeforeAdvice,
			AfterReturningAdvice, InitializingBean {

	private Map<String, StopWatch>				watches					= new HashMap<String, StopWatch>();

	private Class<? extends StopWatch>			stopWatchClass			= Log4JStopWatch.class;

	private Constructor<? extends StopWatch>	stopWatchConstructor	= null;
	
	private String[]									methodPatterns			= "*".split("\\|");

	private PathMatcher								pathMatcher				= null;

	@Override
	public void before(Method method, Object[] args, Object target)
				throws Throwable {
		if (!methodIsMatch(method)) {
			return;
		}
		String completeMethodName = getCompleteMethodName(target, method);
		// 创建性能日志记录器
		StopWatch stopWatch;
		if (watches.containsKey(completeMethodName)) {
			stopWatch = watches.get(completeMethodName);
		} else {
			Object[] stopWatchArgs = new Object[]{
						completeMethodName, Arrays.toString(args)};
			stopWatch = BeanUtils.instantiateClass(stopWatchConstructor,
				stopWatchArgs);
			watches.put(completeMethodName, stopWatch);
		}
		stopWatch.start();
	}

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target)
				throws Throwable {
		if (!methodIsMatch(method)) {
			return;
		}
		String completeMethodName = getCompleteMethodName(target, method);
		// 记录性能
		if (watches.containsKey(completeMethodName)) {
			StopWatch stopWatch = watches.get(completeMethodName);
			stopWatch.stop();
		}
	}

	/**
	 * 根据目标对象与方法获取方法完整名称.
	 * 
	 * @param target 目标对象
	 * @param method 方法
	 * @return 方法完整名称
	 */
	protected String getCompleteMethodName(Object target, Method method) {
		String className = "";
		if (target != null) {
			className = target.toString();
			int loc = className.indexOf("@");
			if (loc >= 0) {
				className = className.substring(0, loc);
			}
		}
		return className + "." + method.getName();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(stopWatchClass, "StopWatch will be not null!");
		Assert.notNull(pathMatcher, "PathMatcher will be not null!");
		stopWatchConstructor = stopWatchClass.getConstructor(new Class[]{
					String.class, String.class});
		stopWatchConstructor.setAccessible(true);
	}

	protected boolean methodIsMatch(Method method) {
		String methodName = method.getName();
		for (String pattern : methodPatterns) {
			if (pathMatcher.match(pattern, methodName)) {
				return true;
			}
		}
		return false;
	}

	public void setStopWatchClass(Class<? extends StopWatch> stopWatchClass) {
		this.stopWatchClass = stopWatchClass;
	}

	public Class<? extends StopWatch> getStopWatchClass() {
		return stopWatchClass;
	}

	public void setMethodPatterns(String[] methodPatterns) {
		this.methodPatterns = methodPatterns;
	}

	public void setMethodPattern(String methodPattern) {
		if (methodPattern != null) {
			setMethodPatterns(methodPattern.split("\\|"));
		}
	}

	public String[] getMethodPatterns() {
		return methodPatterns;
	}

	public void setPathMatcher(Class<PathMatcher> pathMatcher) {
		this.pathMatcher = BeanUtils.instantiate(pathMatcher);
	}
}
