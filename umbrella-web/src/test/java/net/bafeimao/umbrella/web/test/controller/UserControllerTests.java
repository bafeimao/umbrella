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

import net.bafeimao.umbrella.web.domain.User;
import net.bafeimao.umbrella.web.exception.DuplicateEmailException;
import net.bafeimao.umbrella.web.exception.DuplicateNameException;
import net.bafeimao.umbrella.web.service.UserService;
import net.bafeimao.umbrella.web.test.common.TransactionalSpringContextTestsBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
public class UserControllerTests extends TransactionalSpringContextTestsBase {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private UserService userService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .alwaysDo(MockMvcResultHandlers.print()).build();
    }

    @Test
    public void testShowLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("user/login"));
    }

    @Test
    public void testLoginSuccessThenRedirect() throws Exception {
        User user = this.createTmpUser();

        mockMvc.perform(post("/login?name=" + user.getName() + "&password="
                + user.getPassword() + "&returl=/profile/payments"))

                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(view().name("redirect:/profile/payments"));
    }

    @Test
    public void testLoginWithIncorrectPassword() throws Exception {
        mockMvc.perform(post("/login?name=ktgu&password=incorrect"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("/login?error="));
    }

    /**
     * 访问未授权页面会被权限拦截器（SecurityCheckingHandlerInterceptor）拦截并导航到登陆页
     */
    @Test
    public void testShowProfileWithUnauthorized() throws Exception {
        mockMvc.perform(get("/profile"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(redirectedUrl("/login?returl=/profile")); //
    }

    /**
     * 返回一个已授权的页面，测试不会被导航到登陆页面
     */
    @Test
    public void testShowProfileWithAuthorized() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("ktgu");

        mockMvc.perform(get("/profile").sessionAttr("user", user))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("user/profile"));
    }

    /**
     * 测试“导航到注册表单页”
     */
    @Test
    public void testShowRegisterForm() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/register"));
    }

    /**
     * 测试提交注册
     */
    @Test
    public void testSubmitRegister() throws Exception {
        mockMvc.perform(post("/register")
                .param("name", "longfei")
                .param("password", "xxxxxx")
                .param("email", "somebody@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("user/register-success"));
    }

    // TODO 有空需要详细测试一下关于违反约束的异常
//    /**
//     * 测试提交注册表单时数据校验失败
//     */
//    @Test(expected = ConstraintViolationException.class)
//    public void testSubmitRegisterWithConstraintViolations() throws Exception {
//
//        mockMvc.perform(post("/register?name=ktgu&password=123")).andReturn();
////                    .param("name", "ktgu")
////                    .param("password", "123")) // 密码必须为6位，因此违反了约束
////        .andReturn();
//    }

    @Test
    public void testSubmitRegisterWithDuplicateNameException() throws Exception {
        try {
            mockMvc.perform(post("/register").param("name", "ktgu").param("email", "xxx@qq.com"));
        } catch (Exception e) {
            Assert.assertTrue(e.getCause() instanceof DuplicateNameException);
        }
    }

    @Test
    public void testSubmitRegisterWithDuplicateEmailException() throws Exception {
        try {
            mockMvc.perform(post("/register").param("name", "xxx").param("email", "29283212@qq.com"));
        } catch (Exception e) {
            Assert.assertTrue(e.getCause() instanceof DuplicateEmailException);
        }
    }

    /**
     * 请求参数中没有跟参数名或不能识别的参数名都返回-1
     */
    @Test
    public void testCheckExistenceByNoParams() throws Exception {
        mockMvc.perform(get("/user/exists").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value(-1));
    }

    /**
     * 测试“指定名称的用户是否已经存在”
     */
    @Test
    public void testCheckExistenceByName() throws Exception {
        User user = createTmpUser();

        mockMvc.perform(get("/user/exists?name=" + user.getName()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value(1));

        mockMvc.perform(get("/user/exists?name=notfound").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value(0));
    }

    /**
     * 测试“指定邮箱的用户是否已经存在”
     */
    @Test
    public void testCheckExistenceByEmail() throws Exception {
        User user = createTmpUser();

        mockMvc.perform(get("/user/exists?email=" + user.getEmail()).accept(
                MediaType.parseMediaType("application/json; charset=utf-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value(1))
                .andReturn();

        mockMvc.perform(
                get("/user/exists?email=notfound@qq.com").accept(
                        MediaType.parseMediaType("application/json; charset=utf-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value(0))
                .andReturn();
    }

    private User createTmpUser() {
        User user = new User();
        user.setName("u" + new Date().getTime());
        user.setPassword("aaaaaa");
        user.setEmail(user.getName() + "@qq.com");

        userService.save(user);

        return user;
    }
}
