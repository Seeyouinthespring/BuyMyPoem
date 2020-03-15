package com.buymypoem.springmvc.controller;

import com.buymypoem.springmvc.dao.CompositionDAO;
import com.buymypoem.springmvc.model.Composition;
import com.buymypoem.springmvc.logic.compositionBL;
import com.buymypoem.springmvc.model.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class CompositionController {

    @Resource
    UserSession us;

    @Autowired
    CompositionDAO compositionDAO;

    @Autowired
    compositionBL compositionBL;

    @RequestMapping(value = "/start", method= RequestMethod.GET)
    public String getStartList(Model m){
        List<Composition> list=compositionDAO.getCompositions(1,true);
        m.addAttribute("list",list);
        m.addAttribute("page",1);
        int endPage=compositionBL.countPagesForComposition();
        m.addAttribute("end", endPage);
        return "index";
    }

    @RequestMapping(value = "/start/{page}", method= RequestMethod.GET)
    public String getList(@PathVariable int page, Model m){
        List<Composition> list=compositionDAO.getCompositions(page,true);
        m.addAttribute("list",list);
        int endPage=compositionBL.countPagesForComposition();
        m.addAttribute("end", endPage);
        m.addAttribute("page",page);
        return "index";
    }

    @RequestMapping(value = "/composition", method= RequestMethod.GET)
    public String getCompositionStart(Model m){
        List<Composition> list=compositionDAO.getCompositions(1,false);
        m.addAttribute("list",list);
        m.addAttribute("page",1);
        int endPage=compositionBL.countPagesForPublishedComposition();
        m.addAttribute("end", endPage);
        return "composition";
    }

    @RequestMapping(value = "/composition/{page}", method= RequestMethod.GET)
    public String getComposition(@PathVariable int page, Model m){
        List<Composition> list=compositionDAO.getCompositions(page, false);
        m.addAttribute("list",list);
        int endPage=compositionBL.countPagesForPublishedComposition();
        m.addAttribute("end", endPage);
        m.addAttribute("page",page);
        return "composition";
    }

    @RequestMapping(value ="/add_composition_form", method=RequestMethod.GET)
    public String getForm(Model m){
        m.addAttribute("comp", new Composition());
        m.addAttribute("user", us.getUserSession());
        return "add_composition_form";
    }

    @RequestMapping(value = "/add_composition", method= RequestMethod.POST)
    public String addCompositiopn(@ModelAttribute("comp") Composition comp){
        comp.getUser().setUserID(us.getUserSession().getUserID());
        compositionDAO.addComposition(comp);
        return "redirect:/start";
    }
}
