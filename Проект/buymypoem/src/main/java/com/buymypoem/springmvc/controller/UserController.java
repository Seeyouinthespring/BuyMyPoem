package com.buymypoem.springmvc.controller;

import com.buymypoem.springmvc.dao.UserDAO;
import com.buymypoem.springmvc.model.Author;
import com.buymypoem.springmvc.model.Customer;
import com.buymypoem.springmvc.model.User;
import com.buymypoem.springmvc.model.UserSession;
import com.buymypoem.springmvc.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.HashMap;
import java.util.Map;


@Controller
public class UserController {

    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = "/sign in", method = RequestMethod.GET)
    public String sign(Model model) {
        model.addAttribute("usr", new User());
        return "sign in";
    }


    @Autowired
    private SecurityService securityService;

    @Autowired
    UserSession userSession;

    @RequestMapping(value = "/sign in", method = RequestMethod.POST)
    public String signIn(@ModelAttribute("usr") User user, Model model) {
        User userReal = userDAO.getUserByLogin(user.getLogin());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if((!(userReal == null))&&(bCryptPasswordEncoder.matches(user.getPassword(), userReal.getPassword()))) {
            Map<String, String> pages = new HashMap<String, String>();
            pages.put("Author", "redirect:successAuthor");
            pages.put("Customer", "redirect:successCustomer");
            pages.put("Service", "redirect:successService");

            userSession.setUserSession(userReal);
            securityService.autoLogin(user.getLogin(), user.getPassword());
            if (userReal.getRole().equals("Author")){
                Author author = userDAO.getAuthorById(userSession.getUserSession().getUserID());
                userSession.setAuthorID(author.getAuthorID());
                userSession.setRating(author.getRating());
                userSession.setCardNumber(author.getCardNumber());
                userSession.setFinisedcompositions(author.getFinishedcompositions());
            }

            if (userReal.getRole().equals("Customer")){
                Customer customer = userDAO.getCustomerById(userSession.getUserSession().getUserID());
                userSession.setCustomerID(customer.getCustomerID());
                userSession.setPaidcompositionnumber(customer.getPaidcompositionnumber());
            }

            String page = pages.get(userReal.getRole());
            if(page == null) return "error";

            return page;

        } else {
            model.addAttribute("error", "Вы ввели неверные данные");
            return "/sign in";
        }

    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("user") User user, Model model) {

        if (!user.getConfirmPassword().equals(user.getPassword())) {
            model.addAttribute("error", "Пароли не совпали");
            return "registration";
        }

        User userReal = userDAO.getUserByLogin(user.getLogin());
        if (userReal != null) {
            model.addAttribute("error", "Пользователь с таким логином уже существует");
            return "registration";
        }

        userDAO.insertUser(user);
        user.setPhoto("D:/repository/default.jpg");
        userSession.setUserSession(user);
        securityService.autoLogin(user.getLogin(), user.getPassword());
        if (user.getRole().equals("Author")) return "redirect:successAuthor";
        else return "redirect:successCustomer";
    }

    @RequestMapping(value = "/main")
    public String main() {
        if (userSession.getUserSession()==null){
            return "redirect:/index";
        }else return "redirect:/all_composition";
    }

    @RequestMapping(value = "/error")
    public String error() {
        return "error";
    }

    @RequestMapping(value = "/tech_support")
    public String support(Model m) {
        return "tech_support";
    }

    @RequestMapping(value = "/tech_support", method = RequestMethod.POST)
    public String support_go(@ModelAttribute("msg_err") String msg, Model model) {
        int in = userDAO.techSupport(userSession.getUserSession().getUserID(), msg);
        if (in>0) model.addAttribute("response", "Cообщение отправлено. О решении проблемы мы напишем вам на почту.");
        return "tech_support";
    }
}

