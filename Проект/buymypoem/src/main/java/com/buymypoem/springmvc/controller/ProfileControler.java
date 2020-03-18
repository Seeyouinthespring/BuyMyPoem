package com.buymypoem.springmvc.controller;

import com.buymypoem.springmvc.dao.CompositionDAO;
import com.buymypoem.springmvc.logic.compositionBL;
import com.buymypoem.springmvc.model.Composition;
import com.buymypoem.springmvc.model.User;
import com.buymypoem.springmvc.model.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Controller
public class ProfileControler {

    @Resource
    UserSession us;

    @Autowired
    CompositionDAO compositionDAO;

    @Autowired
    com.buymypoem.springmvc.logic.compositionBL compositionBL;

    @RequestMapping(value = "/successAuthor")
    public String successAuthor(Model m) {
        m.addAttribute("user", us.getUserSession());
        List<Composition> list=compositionDAO.getCompositions(1,"PublishedOfAuthor", us.getUserSession().getUserID());
        m.addAttribute("list",list);
        m.addAttribute("page",1);
        int endPage=compositionBL.countPages("countCompOfAuthor");
        m.addAttribute("end", endPage);
        return "successAuthor";
    }

    @RequestMapping(value = "/successAuthor/{page}", method= RequestMethod.GET)
    public String getcompositionOfAuthor(@PathVariable int page, Model m){
        m.addAttribute("user", us.getUserSession());
        List<Composition> list=compositionDAO.getCompositions(page, "PublishedOfAuthor", us.getUserSession().getUserID());
        m.addAttribute("list",list);
        int endPage=compositionBL.countPages("countCompOfAuthor");
        m.addAttribute("end", endPage);
        m.addAttribute("page",page);
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
