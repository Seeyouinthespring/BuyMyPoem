package com.buymypoem.springmvc.controller;

import com.buymypoem.springmvc.dao.*;
import com.buymypoem.springmvc.logic.RequestBL;
import com.buymypoem.springmvc.model.AuthorRequest;
import com.buymypoem.springmvc.model.Request;
import com.buymypoem.springmvc.model.User;
import com.buymypoem.springmvc.model.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
public class OrderController {

    @Resource
    UserSession us;

    @Autowired
    RequestDAO requestDAO;

    @Autowired
    OrderDAO orderDAO;

    @Autowired
    TypeDAO typeDAO;

    @Autowired
    GenreDAO genreDAO;

    @Autowired
    CommentDAO commentDAO;

    @Secured("ROLE_USER_CUSTOMER")
    @RequestMapping(value = "/create_order/{uid}", method = RequestMethod.POST)
    public String createOrder(@PathVariable int uid, @ModelAttribute("req") Request request){
        orderDAO.createOrder(requestDAO.getRequestById(request.getRequestID()),uid);
        requestDAO.dropAllResponses(request.getRequestID());
        commentDAO.dropAllCommentRequestLinks(request.getRequestID());
        requestDAO.dropRequest(request.getRequestID());
        return "redirect:/personalrequests";
    }
}
