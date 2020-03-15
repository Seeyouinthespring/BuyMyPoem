package com.buymypoem.springmvc.controller;

import com.buymypoem.springmvc.dao.UserDAO;
import com.buymypoem.springmvc.model.User;
import com.buymypoem.springmvc.model.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
public class test {

    @Resource
    UserSession us;

    @RequestMapping(value = "/test")
    public String test(Model model) {
        model.addAttribute("usr",  us.getUserSession().getLogin());
        return "test";
    }

}
