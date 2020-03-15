package com.buymypoem.springmvc.controller;

import com.buymypoem.springmvc.dao.UserDAO;
import com.buymypoem.springmvc.model.User;
import com.buymypoem.springmvc.model.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = "/sign in", method = RequestMethod.GET)
    public String sign(Model model) {
        model.addAttribute("usr", new User());
        return "sign in";
    }


    @Autowired
    UserSession userSession;

    @RequestMapping(value = "/sign in", method = RequestMethod.POST)
    public String signIn(@ModelAttribute("usr") User user, Model model) {
        User userReal = userDAO.getUserByLogin(user.getLogin());

        if(   (!(userReal == null))&&( Integer.parseInt(userReal.getPassword())==user.getPassword().hashCode())) {
            Map<String, String> pages = new HashMap<String, String>();
            pages.put("Author", "successAuthor");
            pages.put("Customer", "successCustomer");
            pages.put("Service", "successService");
            userSession.setUserSession(userReal);
            String page = pages.get(userReal.getRole());
            if(page == null) return "error";

            return page;

        } else {
            model.addAttribute("error", "Вы ввели неверные данные");
            return "/sign in";
        }

    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("user") User user, Model model) {

        if (!user.getConfirmPassword().equals(user.getPassword())) {
            model.addAttribute("error", "Пароли не совпали");
            return "registration";
        }

        User userReal = userDAO.getUserByLogin(user.getLogin());
        if (userReal != null) {
            model.addAttribute("error", "Пользователь с таким логином уже существует");
            return "registration";
        }

        userDAO.insertUser(user);
        if (user.getRole().equals("Author")) return "successAuthor";
        return "successCustomer";


    }

    @RequestMapping(value = "/")
    public String main() {
        return "main";
    }
}

