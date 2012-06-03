
package org.webframe.plugins.perf4j.core;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;

/**
 * Perf4jAutoProxyCreator
 * 
 * @author <a href="mailto:guoqing.huang@foxmail.com">黄国庆</a>
 * @since 2012-6-2 下午7:47:46
 * @version
 */
public class Perf4jAutoProxyCreator extends BeanNameAutoProxyCreator {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 3869456450105206285L;

	private String					beanNamePattern;

	private String					interceptorName;

	public void setInterceptorName(String interceptorName) {
		this.interceptorName = interceptorName;
		setInterceptorNames(new String[]{
			interceptorName});
	}

	public String getInterceptorName() {
		return interceptorName;
	}

	/**
	 * 匹配注入的bean
	 * 
	 * @param beanNamePattern 分割符为：'|', 例如：*Service|*BO
	 * @author 黄国庆 2012-6-2 下午7:55:04
	 */
	public void setBeanNamePattern(String beanNamePattern) {
		this.beanNamePattern = beanNamePattern;
		if (getBeanNamePattern() == null) {
			this.beanNamePattern = "*Service|*BO";
		}
		String[] beanNames = getBeanNamePattern().split("\\|");
		setBeanNames(beanNames);
	}

	public String getBeanNamePattern() {
		return beanNamePattern;
	}
}
