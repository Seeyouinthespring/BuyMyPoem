package com.buymypoem.springmvc.controller;

import com.buymypoem.springmvc.dao.UserDAO;
import com.buymypoem.springmvc.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value="/sign in", method=RequestMethod.GET)
    public String sign(Model model) {
        model.addAttribute("usr",new User());
        return "sign in";
    }

    @RequestMapping(value = "/sign in", method = RequestMethod.POST)
    public String signIn(@ModelAttribute ("usr") User user, Model model){
        User userReal = userDAO.getUserByLogin(user.getLogin());
        if (userReal!=null){
        if (userReal.getPassword().equals(user.getPassword()))
        {
            return "success";
        }
        else{
            model.addAttribute("error", "Вы ввели не правильный пароль");
            return "/sign in";
        }
        }else{
            model.addAttribute("error", "Вы ввели не правильный логин");
            return "/sign in";
        }
    }

    @RequestMapping(value="/registration", method=RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String createUser(@ModelAttribute("user") User user, Model model) {
        LOG.debug(user.toString());

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
        return "success";

    }

    @RequestMapping(value="/")
    public String main() {
        return "main";
    }
}

