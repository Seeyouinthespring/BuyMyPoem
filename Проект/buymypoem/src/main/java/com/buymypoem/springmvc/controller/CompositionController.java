package com.buymypoem.springmvc.controller;

import com.buymypoem.springmvc.dao.CompositionDAO;
import com.buymypoem.springmvc.model.Composition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class CompositionController {

    @Autowired
    CompositionDAO compositionDAO;

    @RequestMapping("/")
    public String getStartList(Model m){
        List<Composition> list=compositionDAO.getAllCompositions(1);
        m.addAttribute("list",list);
        m.addAttribute("page",1);
        return "index";
    }

    @RequestMapping("/{id}")
    public String getList(@PathVariable int page, Model m){
        List<Composition> list=compositionDAO.getAllCompositions(page+1);
        m.addAttribute("list",list);
        m.addAttribute("page",page+1);
        return "index";
    }
}
