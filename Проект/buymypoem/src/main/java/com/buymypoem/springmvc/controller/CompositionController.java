package com.buymypoem.springmvc.controller;

import com.buymypoem.springmvc.dao.CompositionDAO;
import com.buymypoem.springmvc.model.Composition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class CompositionController {

    @Autowired
    CompositionDAO compositionDAO;

    @RequestMapping("/")
    public String getList(Model m){
        List<Composition> list=compositionDAO.getAllCompositions();
        m.addAttribute("list",list);
        return "index";
    }
}
