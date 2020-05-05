package com.buymypoem.springmvc.controller;

import com.buymypoem.springmvc.dao.*;
import com.buymypoem.springmvc.logic.ProfileBL;
import com.buymypoem.springmvc.logic.RequestBL;
import com.buymypoem.springmvc.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

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

    @Autowired
    ProfileBL profileBL;

    @Secured("ROLE_USER_CUSTOMER")
    @RequestMapping(value = "/create_order/{uid}", method = RequestMethod.POST)
    public String createOrder(@PathVariable int uid, @ModelAttribute("req") Request request){
        orderDAO.createOrder(requestDAO.getRequestById(request.getRequestID()),uid);
        requestDAO.dropAllResponses(request.getRequestID());
        commentDAO.dropAllCommentRequestLinks(request.getRequestID());
        requestDAO.dropRequest(request.getRequestID());
        return "redirect:/personalrequests";
    }

    @RequestMapping(value = "/my_orders", method= RequestMethod.GET)
    public String getPersonalOrders(Model m){
        int id;
        if (us.getUserSession().getRole().equals("Author")) id = us.getAuthorID();
        else id=us.getCustomerID();

        List<Order> orderList = orderDAO.getAllOrders(id,us.getUserSession().getRole());

        for (Order o: orderList){
            o.getCustomer().setPhoto(profileBL.getImg(o.getCustomer().getPhoto()));
            o.getAuthor().setPhoto(profileBL.getImg(o.getAuthor().getPhoto()));
        }

        m.addAttribute("list",orderList);

        return "personal_order";
    }

    @RequestMapping(value = "/order_details/{id}", method = RequestMethod.POST)
    public String showOrder(@PathVariable int id, Model m){
        Order order = orderDAO.getOrderById(id);
        order.getCustomer().setPhoto(profileBL.getImg(order.getCustomer().getPhoto()));
        order.getAuthor().setPhoto(profileBL.getImg(order.getAuthor().getPhoto()));
        List<Comment> commentList = commentDAO.GetCommentsForRequest(id,"order");
        for (Comment comment: commentList){
            comment.getUser().setPhoto(profileBL.getImg(comment.getUser().getPhoto()));
        }
        User me = us.getUserSession();
        me.setPhoto(profileBL.getImg(me.getPhoto()));
        m.addAttribute("me",me);
        m.addAttribute("order",order);
        m.addAttribute("comments",commentList);
        m.addAttribute("mycomment",new Comment());
        return "order_details";
    }
}
