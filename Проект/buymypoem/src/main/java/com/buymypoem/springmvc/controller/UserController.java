package com.buymypoem.springmvc.controller;

import com.buymypoem.springmvc.dao.UserDAO;
import com.buymypoem.springmvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = "/sign in", method = RequestMethod.GET)
    public String sign(Model model) {
        model.addAttribute("usr", new User());
        return "sign in";
    }

    @RequestMapping(value = "/sign in", method = RequestMethod.POST)
    public String signIn(@ModelAttribute("usr") User user, Model model) {
        User userReal = userDAO.getUserByLogin(user.getLogin());

        if(   (!(userReal == null))&&( Integer.parseInt(userReal.getPassword())==user.getPassword().hashCode())) {
            switch (userReal.getRole().charAt(0)){
                case ('A'):
                    return "successAuthor";
                case ('C'):
                    return "successCustomer";
                case ('S'):
                    return "successService";
                default:
                    return "error";
            }
        } else {
            model.addAttribute("error", "Вы ввели неверные данные");
            return "/sign in";
        }

    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("newUsr", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("newUsr") User user, Model model) {
        if (user.getConfirmPassword().equals(user.getPassword())) {
            User userReal = userDAO.getUserByLogin(user.getLogin());
            if (userReal == null) {
                userDAO.insertUser(user);
                if (user.getRole().equals("Author"))
                return "successAuthor";
                else return "successCustomer";

            }
            model.addAttribute("error", "Пользователь с таким логином уже существует");
            return "registration";
        }
        model.addAttribute("error", "Пароли не совпадают");
        return "registration";
    }

    /*@RequestMapping(value = "/")
    public String main() {
        return "main";
    }*/
}

