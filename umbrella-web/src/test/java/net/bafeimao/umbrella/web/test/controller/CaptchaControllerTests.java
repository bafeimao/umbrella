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

package net.bafeimao.umbrella.web.test.controller;

import com.google.code.kaptcha.Constants;
import net.bafeimao.umbrella.web.test.common.TransactionalSpringContextTestsBase;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// TODO 优化：运行这个测试是不需要连数据库的，因此需要修改一下
@WebAppConfiguration
public class CaptchaControllerTests  extends TransactionalSpringContextTestsBase {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}


	/**
	 * 测试创建一个验证码
	 */
	@Test
	public void testBuildCaptcha() throws Exception {
		this.mockMvc.perform(get("/captcha").accept(MediaType.IMAGE_JPEG))
				.andExpect(status().isOk())
				.andExpect(content().contentType("image/jpeg"));
	}

	/**
	 * 测试有效匹配的验证码
	 */
	@Test
	public void testMatchedCaptcha() throws Exception {
		this.mockMvc.perform(get("/captcha/match?text=abcd").sessionAttr(Constants.KAPTCHA_SESSION_KEY, "abcd"))
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.result").value(true));

	}

	/**
	 * 测试无效匹配的验证码
	 */
	@Test
	public void testUnmatchedCaptcha() throws Exception {
		this.mockMvc.perform(get("/captcha/match?text=xxx").sessionAttr(Constants.KAPTCHA_SESSION_KEY, "yyy"))
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.result").value(false));
	}

}
