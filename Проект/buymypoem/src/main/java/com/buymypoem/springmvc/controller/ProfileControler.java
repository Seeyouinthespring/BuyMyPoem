package com.buymypoem.springmvc.controller;

import com.buymypoem.springmvc.dao.CompositionDAO;
import com.buymypoem.springmvc.dao.UserDAO;
import com.buymypoem.springmvc.logic.ProfileBL;
import com.buymypoem.springmvc.model.Composition;
import com.buymypoem.springmvc.model.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.io.*;
import java.util.List;

@Controller
public class ProfileControler {

    @Resource
    UserSession us;

    @Autowired
    CompositionDAO compositionDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    com.buymypoem.springmvc.logic.compositionBL compositionBL;

    @Autowired
    ProfileBL profileBL;

    //private String pathToSave = "D:/BuyMyPoem/BuyMyPoem/Проект/buymypoem/src/main/webapp/WEB-INF/resources/img/";
    private String pathToSave = "D:/repository/";


    @RequestMapping(value = "/successAuthor")
    public String successAuthor(Model m) {
        m.addAttribute("user", us.getUserSession());
        List<Composition> list=compositionDAO.getCompositions(1,"PublishedOfAuthor", us.getUserSession().getUserID());
        m.addAttribute("list",list);
        m.addAttribute("page",1);
        int endPage=compositionBL.countPages("countCompOfAuthor");
        m.addAttribute("end", endPage);
        m.addAttribute("photo", profileBL.getImg(us.getUserSession().getPhoto()));
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
        m.addAttribute("photo", profileBL.getImg(us.getUserSession().getPhoto()));
        return "successAuthor";
    }

    @RequestMapping(value = "/successCustomer")
    public String successCustomer(Model m) {
        m.addAttribute("user", us.getUserSession());
        m.addAttribute("photo", profileBL.getImg(us.getUserSession().getPhoto()));
        return "successCustomer";
    }

    @RequestMapping(value = "/successService")
    public String successService(Model m){
        m.addAttribute("user", us.getUserSession());
        return "successService";
    }

    @RequestMapping(value = "/edit_profile", method= RequestMethod.GET)
    public String editProfile(Model m){
        m.addAttribute("user", us);
        return "edit_profile";
    }


    @RequestMapping(value = "/edit_profile", method= RequestMethod.POST)
    public String saveEditProfile(@RequestParam("photo") MultipartFile photo, Model m){
        try {
            if (!(photo.getContentType().equals("image/jpeg"))){
                m.addAttribute("error","Добавьте файл с расширением jpeg");
                return "/edit_profile";
            }
            File userPhoto = new File(pathToSave + us.getUserSession().getLogin()+".jpg");
            userPhoto.createNewFile();
            FileOutputStream fos = new FileOutputStream(userPhoto);
            fos.write(photo.getBytes());
            fos.close();
            us.getUserSession().setPhoto(pathToSave + userPhoto.getName());
            userDAO.updateUser(us.getUserSession());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:successAuthor";
    }
}
