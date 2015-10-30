/*
 * Copyright 2002-2015 by bafeimao.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
