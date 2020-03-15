package com.buymypoem.springmvc.controller;

import com.buymypoem.springmvc.model.User;
import com.buymypoem.springmvc.model.UserSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
public class ProfileControler {

    @Resource
    UserSession us;

    @RequestMapping(value = "/successAuthor")
    public String successAuthor(Model m) {
        m.addAttribute("user", us.getUserSession());
        return "successAuthor";
    }

    @RequestMapping(value = "/successCustomer")
    public String successCustomer(Model m) {
        m.addAttribute("user", us.getUserSession());
        return "successCustomer";
    }

    @RequestMapping(value = "/successService")
    public String successService(Model m){
        m.addAttribute("user", us.getUserSession());
        return "successService";
    }

}
