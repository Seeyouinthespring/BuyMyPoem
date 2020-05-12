package com.buymypoem.springmvc.controller;

import com.buymypoem.springmvc.dao.AuthorDAO;
import com.buymypoem.springmvc.dao.CompositionDAO;
import com.buymypoem.springmvc.dao.UserDAO;
import com.buymypoem.springmvc.logic.ProfileBL;
import com.buymypoem.springmvc.logic.compositionBL;
import com.buymypoem.springmvc.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class AuthorController {

    @Autowired
    AuthorDAO authorDAO;

    @Autowired
    compositionBL compositionBL;

    @Autowired
    ProfileBL profileBL;

    @RequestMapping(value = "/all_authors", method= RequestMethod.GET)
    public String getAuthor_start(Model m){
        List<User> list = authorDAO.getAllAuthor(1);
        int endPage = compositionBL.countPagesAuthor();
        for (User user: list){
            user.setPhoto(profileBL.getImg(user.getPhoto()));
        }
        m.addAttribute("list",list);
        m.addAttribute("page",1);
        m.addAttribute("end", endPage);
        return "all_authors";
    }

    @RequestMapping(value = "/all_authors/{page}", method= RequestMethod.GET)
    public String getAuthor_next(@PathVariable int page, Model m){
        List<User> list = authorDAO.getAllAuthor(page);
        int endPage = compositionBL.countPagesAuthor();
        for (User user: list){
            user.setPhoto(profileBL.getImg(user.getPhoto()));
        }
        m.addAttribute("list",list);
        m.addAttribute("end", endPage);
        m.addAttribute("page", page);
        return "all_authors";
    }

    @Autowired UserDAO userDAO;
    @Autowired CompositionDAO compositionDAO;

    @RequestMapping(value = "/author/{login}", method= RequestMethod.GET)
    public String getAuthor(@PathVariable String login, Model m){
        User user = userDAO.getUserByLogin(login);
        m.addAttribute("user", user);
        List<Composition> list=compositionDAO.getCompositions(1, "PublishedOfAuthor", user.getUserID());
        m.addAttribute("list",list);
        int endPage=compositionBL.countPagesAuthorById(user.getUserID());
        m.addAttribute("end", endPage);
        m.addAttribute("page",1);
        m.addAttribute("photo", profileBL.getImg(user.getPhoto()));
        return "author";
    }

    @RequestMapping(value = "/author/{page}/{login}", method= RequestMethod.GET)
    public String getAuthor_next(@PathVariable int page, @PathVariable String login, Model m){
        User user = userDAO.getUserByLogin(login);
        m.addAttribute("user", user);
        List<Composition> list=compositionDAO.getCompositions(page, "PublishedOfAuthor", user.getUserID());
        m.addAttribute("list",list);
        int endPage=compositionBL.countPagesAuthorById(user.getUserID());
        m.addAttribute("end", endPage);
        m.addAttribute("page",page);
        m.addAttribute("photo", profileBL.getImg(user.getPhoto()));
        return "author";
    }

    int endPageRating=0;
    @RequestMapping(value = "/rating_authors", method= RequestMethod.GET)
    public String getAuthorRating_start(Model m){
        List<User> list = compositionBL.getAuthorRating(1);
        endPageRating = compositionBL.countPagesAuthorRating();
        for (User user: list){
            user.setPhoto(profileBL.getImg(user.getPhoto()));
        }
        m.addAttribute("list",list);
        m.addAttribute("page",1);
        m.addAttribute("end", endPageRating);
        return "rating_authors";
    }
}
