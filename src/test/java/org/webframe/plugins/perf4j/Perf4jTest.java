package org.webframe.plugins.perf4j;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Repeat;
import org.webframe.core.exception.ServiceException;
import org.webframe.core.service.IBaseService;
import org.webframe.plugins.perf4j.test.TTest;
import org.webframe.test.BaseSpringTests;


/**
 * Perf4jTest
 * 
 * @author <a href="mailto:guoqing.huang@foxmail.com">黄国庆</a>
 * @since 2012-6-2 下午8:06:18
 * @version
 */
public class Perf4jTest extends BaseSpringTests {

	@Autowired
	private IBaseService	baseService;

	@Test
	@Repeat
	public void testSave() throws ServiceException {
		TTest test = new TTest();
		baseService.save(test);
		baseService.update(test);
		baseService.saveOrUpdate(test);
	}
}
