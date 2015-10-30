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

package net.bafeimao.umbrella.web.service;

import net.bafeimao.umbrella.support.repository.GenericRepository;
import net.bafeimao.umbrella.support.repository.hibernate.HibernateRepositoryImpl;
import net.bafeimao.umbrella.web.domain.User;
import net.bafeimao.umbrella.web.exception.DuplicateEmailException;
import net.bafeimao.umbrella.web.exception.DuplicateNameException;
import net.bafeimao.umbrella.web.repository.UserRepository;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService extends HibernateRepositoryImpl<User, Long> implements GenericRepository<User, Long> {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private VelocityEngine velocityEngine;

    private void sendRegistrationConfirmEmail(final User user) {
        final MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(user.getEmail());
                message.setFrom("29283212@qq.com");
                message.setSubject("注册coconut成功");

                Map model = new HashMap();
                model.put("user", user);
                String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/templates/registration-confirm-mail.html", "gb2312", model);
                message.setText(text, true);
            }
        };
        this.mailSender.send(preparator);
    }

    public User register(User user) throws DuplicateEmailException, DuplicateNameException {
        user.setCreateTime(new Date());

        if (this.checkExistenceByName(user.getName())) {
            throw new DuplicateNameException();
        }

        if (this.checkExistenceByEmail(user.getEmail())) {
            throw new DuplicateEmailException();
        }

        // 发送注册确认邮件
        this.sendRegistrationConfirmEmail(user);

        return userRepository.save(user);
    }

    public boolean checkExistenceByName(String name) {
        return userRepository.checkExistenceByName(name);
    }

    public boolean checkExistenceByEmail(String email) {
        return userRepository.checkExistenceByEmail(email);
    }

    /**
     * 用户登录，1：成功，2：密码不正确，3：用户名不存在
     *
     * @param identifier
     * @param password
     * @return
     */
    public int login(String identifier, String password) {
        User user = userRepository.findById("where name=? or email=? or mobile=?", identifier, identifier, identifier);

        if (user != null) {
            if (user.getPassword().equals(password)) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return 2;
        }
    }
}
