package com.buymypoem.springmvc.controller;

import com.buymypoem.springmvc.dao.CompositionDAO;
import com.buymypoem.springmvc.model.Composition;
import com.buymypoem.springmvc.logic.compositionBL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class CompositionController {

    @Autowired
    CompositionDAO compositionDAO;

    @Autowired
    compositionBL compositionBL;

    @RequestMapping(value = "/start", method= RequestMethod.GET)
    public String getStartList(Model m){
        List<Composition> list=compositionDAO.getAllCompositions(1);
        m.addAttribute("list",list);
        m.addAttribute("page",1);
        int endPage=compositionBL.countTheNumberOfAll();
        m.addAttribute("end", endPage);
        return "index";
    }

    @RequestMapping(value = "/start/{page}", method= RequestMethod.GET)
    public String getList(@PathVariable int page, Model m){
        List<Composition> list=compositionDAO.getAllCompositions(page);
        m.addAttribute("list",list);
        int endPage=compositionBL.countTheNumberOfAll();
        m.addAttribute("end", endPage);
        m.addAttribute("page",page);
        return "index";
    }

    @RequestMapping(value = "/composition", method= RequestMethod.GET)
    public String getCompositionStart(Model m){
        List<Composition> list=compositionDAO.getAllCompositionsForUser(1);
        m.addAttribute("list",list);
        m.addAttribute("page",1);
        int endPage=compositionBL.countTheNumberOfPublishedComposition();
        m.addAttribute("end", endPage);
        return "composition";
    }

    @RequestMapping(value = "/composition/{page}", method= RequestMethod.GET)
    public String getComposition(@PathVariable int page, Model m){
        List<Composition> list=compositionDAO.getAllCompositionsForUser(page);
        m.addAttribute("list",list);
        int endPage=compositionBL.countTheNumberOfPublishedComposition();
        m.addAttribute("end", endPage);
        m.addAttribute("page",page);
        return "composition";
    }

}
