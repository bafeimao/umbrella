package net.bafeimao.umbrella.web.controller;

import net.bafeimao.umbrella.web.domain.User;
import net.bafeimao.umbrella.web.exception.DuplicateEmailException;
import net.bafeimao.umbrella.web.exception.DuplicateNameException;
import net.bafeimao.umbrella.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@SessionAttributes(value = {"user"})
public class UserController {

    private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 导航到注册页面
     *
     * @return 返回导注册页面对应的逻辑视图名
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerView(User user, ModelMap model) {
        model.put("user", user);
        return "user/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(HttpServletRequest request) {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");

        if(email == null || email.equals("")) {
            return "user/register-error";
        }
        return "user/register-success";
    }

    @RequestMapping(value = "/register1", method = RequestMethod.POST)
    public String register(@Valid User user, Model model, BindingResult result)
            throws DuplicateEmailException, DuplicateNameException {

        // 如果在绑定User对象的时候出现异常，直接返回，后面就不处理了
        if (!result.hasErrors()) {

            // // 检查验证码是否填写正确
            // String sessionChaptcha = (String)
            // session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
            // if (captcha == null || !captcha.equalsIgnoreCase(sessionChaptcha)) {
            // result.rejectValue("chaptcha", "chaptcha is incorrect");
            // return "user/register";
            // }

            if (user != null) {
                if (user.getName() == null) {
                    result.rejectValue("name", "name is required");
                    return "user/register";
                }

                if (user.getEmail() == null) {
                    result.rejectValue("email", "email is required");
                    return "user/register";
                }
            }

            userService.register(user);
            if (user.getId() != null) {
                model.addAttribute("user", user);
                return "user/register";
            }
        }

        return "user/register-success.jsp";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginView() {
        return "user/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(User user, HttpSession session, @RequestParam(required = false) String returl) {
        int result = userService.login(user.getName(), user.getPassword());
        if (result == 0) {
            session.setAttribute("user", user);

            if (returl != null) {
                return "redirect:" + returl;
            } else {
                return "redirect:/profile";
            }
        } else if (result == 1) {
            LOGGER.info("密码不正确");
        } else if (result == 2) {
            LOGGER.info("用户名不存在");
        }

        return "/login?error=";
    }

    @RequestMapping(value = "/user/exists", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> exists(HttpServletRequest request) {
        Map<String, Object> retMap = new HashMap<String, Object>();

        // URL格式不匹配
        if (request.getParameterMap().size() == 0) {
            retMap.put("result", -1);
            retMap.put("msg", "unknown url pattern!");
            return retMap;
        }

        // 判断用户名是否存在
        else if (request.getParameter("name") != null) {
            retMap.put("result", userService.checkExistenceByName(request.getParameter("name")) ? 1 : 0);
        }

        // 判断邮箱是否存在
        else if (request.getParameter("email") != null) {
            retMap.put("result", userService.checkExistenceByEmail(request.getParameter("email")) ? 1 : 0);
        }

        return retMap;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profileView(User user) {
        return "user/profile";
    }
}
