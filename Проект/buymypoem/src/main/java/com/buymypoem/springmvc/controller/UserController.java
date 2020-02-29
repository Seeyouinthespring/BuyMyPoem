package com.buymypoem.springmvc.controller;

import com.buymypoem.springmvc.dao.CompositionDAO;
import com.buymypoem.springmvc.dao.UserDAO;
import com.buymypoem.springmvc.model.Composition;
import com.buymypoem.springmvc.model.User;
import com.sun.tracing.dtrace.ModuleAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserDAO userDAO;

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

    @RequestMapping(value="/", method=RequestMethod.GET)
    public String greetingForm(Model model) {
        model.addAttribute("usr",new User());
        return "sign in";
    }
}

