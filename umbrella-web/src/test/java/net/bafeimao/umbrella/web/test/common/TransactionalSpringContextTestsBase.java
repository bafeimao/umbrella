package net.bafeimao.umbrella.web.test.common;

import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
//import org.springframework.test.context.transaction.TransactionConfigurationnfiguration;

@ContextConfiguration(locations = { "classpath:/config/spring/context-web.xml" })
//@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Rollback(true)
public abstract class TransactionalSpringContextTestsBase extends AbstractTransactionalJUnit4SpringContextTests {

}
