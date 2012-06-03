/*
 * wf-web-springmvc
 * Created on 2012-1-30-下午08:18:35
 */

package org.webframe.plugins.perf4j;

import org.webframe.support.driver.AbstractModulePluginDriver;
import org.webframe.support.driver.ModulePluginManager;

/**
 * 测试驱动
 * 
 * @author <a href="mailto:guoqing.huang@foxmail.com">黄国庆 </a>
 * @since 2012-1-30 下午08:18:35
 * @version
 */
public class TestModulePluginDriver extends AbstractModulePluginDriver {

	static {
		ModulePluginManager.registerDriver(new TestModulePluginDriver());
	}

	/* (non-Javadoc)
	 * @see org.webframe.support.driver.ModulePluginDriver#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "TestModule";
	}

	@Override
	public String getEntityLocation() {
		return "test";
	}
}
