package com.buymypoem.springmvc.controller;

import com.buymypoem.springmvc.dao.*;
import com.buymypoem.springmvc.logic.OrderBL;
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
import java.util.*;

@Controller
public class OrderController {

    @Resource
    UserSession us;

    @Autowired
    RequestDAO requestDAO;

    @Autowired
    OrderDAO orderDAO;

    @Autowired
    CommentDAO commentDAO;

    @Autowired
    CompositionDAO compositionDAO;

    @Autowired
    ProfileBL profileBL;

    @Secured("ROLE_USER_CUSTOMER")
    @RequestMapping(value = "/create_order/{uid}", method = RequestMethod.POST)
    public String createOrder(@PathVariable int uid, @ModelAttribute("req") Request request){
        orderDAO.createOrder(requestDAO.getRequestById(request.getRequestID()),uid);
        requestDAO.dropAllResponses(request.getRequestID());
        List<CommentRequest> commentRequests = commentDAO.GetCommentsToDeleteRequest(request.getRequestID());
        commentDAO.dropAllCommentRequestLinks(request.getRequestID());
        for (CommentRequest cr:commentRequests) {
            commentDAO.dropComment(cr.getCommentID());
        }
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

    String rating = "";
    String ratingCat = "";
    String color = "";

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
        List<Composition> mydrafts;
        if (us.getUserSession().getRole().equals("Author")){
            mydrafts = compositionDAO.getCompositions(0,"AllMyDrafts",us.getUserSession().getUserID());
        }else{
            mydrafts = null;
        }
        m.addAttribute("drafts", mydrafts);
        m.addAttribute("me",me);
        m.addAttribute("order",order);
        m.addAttribute("comments",commentList);
        m.addAttribute("mycomment",new Comment());
        m.addAttribute("comp",new Composition());
        m.addAttribute("rating", rating);
        m.addAttribute("ratingCat", ratingCat);
        m.addAttribute("color", color);
        return "order_details";
    }

    @RequestMapping(value="/add_comment_order/{id}",method=RequestMethod.POST)
    public String addCommentOrder(@PathVariable int id, @ModelAttribute("mycomment") Comment comment){
        User u = new User();
        u.setUserID(us.getUserSession().getUserID());
        comment.setUser(u);
        long newComment = commentDAO.addComment(comment);
        commentDAO.addCommentLink(newComment,id,"order");
        return "forward:/order_details/"+id;
    }

    @RequestMapping(value = "/add_composition_to_order/{id}", method= RequestMethod.POST)
    public String addCompositionToOrder(@PathVariable int id, @ModelAttribute("comp") Composition comp){
        compositionDAO.changeStatus(comp.getCompositionID(),"previewed");
        orderDAO.changeStatus(id,"ready");
        orderDAO.Composition_ToFrom_Order(comp.getCompositionID(),id,true);
        return "forward:/order_details/"+id;
    }


    @RequestMapping(value = "/accept_order_form/{id}", method= RequestMethod.GET)
    public String acceptOrder(@PathVariable int id, Model m){
        Order order = orderDAO.getOrderById(id);
        order.getCustomer().setPhoto(profileBL.getImg(order.getCustomer().getPhoto()));
        order.getAuthor().setPhoto(profileBL.getImg(order.getAuthor().getPhoto()));
        m.addAttribute("order",order);
        m.addAttribute("cvv",new String());
        m.addAttribute("card",new String());
        return "accept_order_page";
    }

    @RequestMapping(value = "/accept_order/{id}", method= RequestMethod.POST)
    public String payForOrder(@PathVariable int id, Model m){
        Order order = orderDAO.getOrderById(id);
        compositionDAO.changeAuthor(us.getCustomerID(),order.getComposition().getCompositionID());
        compositionDAO.changeStatus(order.getComposition().getCompositionID(),"bought");
        List<CommentOrdering> commentOrderings = commentDAO.GetCommentsToDeleteOrder(id);
        commentDAO.dropAllCommentOrderingLinks(id);
        for (CommentOrdering co:commentOrderings) {
            commentDAO.dropComment(co.getCommentID());
        }
        orderDAO.dropOrder(id);
        return "redirect:/successCustomer";
    }

    @RequestMapping(value = "/send_order_form/{id}", method= RequestMethod.GET)
    public String sendOrderForModificationsForm(@PathVariable int id, Model m){
        Order order = orderDAO.getOrderById(id);
        m.addAttribute("mycomment",new Comment());
        m.addAttribute("order",order);
        return "send_order_with_comment";
    }

    @RequestMapping(value = "/send_order/{id}", method= RequestMethod.POST)
    public String sendOrderForModifications(@PathVariable int id, @ModelAttribute("mycomment") Comment comment){
        Order order = orderDAO.getOrderById(id);
        User u = new User();
        u.setUserID(us.getUserSession().getUserID());
        comment.setUser(u);
        long newComment = commentDAO.addComment(comment);
        commentDAO.addCommentLink(newComment,id,"order");
        compositionDAO.changeStatus(order.getComposition().getCompositionID(),"draft");
        orderDAO.changeStatus(id,"processing");
        orderDAO.Composition_ToFrom_Order(order.getComposition().getCompositionID(),id,false);
        return "forward:/order_details/"+id;
    }

    @RequestMapping(value = "/cancel_order_ask/{id}", method= RequestMethod.POST)
    public String requestToCancelOrder(@PathVariable int id){
        if (us.getUserSession().getRole().equals("Author")){
            orderDAO.changeStatus(id,"canceledA");
        }
        else {
            orderDAO.changeStatus(id,"canceledC");
        }
        return "forward:/order_details/"+id;
    }

    @RequestMapping(value = "/canceling/{id}", method= RequestMethod.POST)
    public String acceptCanceling(@PathVariable int id){
        List<CommentOrdering> commentOrderings = commentDAO.GetCommentsToDeleteOrder(id);
        commentDAO.dropAllCommentOrderingLinks(id);
        for (CommentOrdering co:commentOrderings) {
            commentDAO.dropComment(co.getCommentID());
        }
        orderDAO.dropOrder(id);
        return "index";
    }

    @RequestMapping(value = "/refuse_canceling/{id}", method= RequestMethod.POST)
    public String refuseCanceling(@PathVariable int id){
        orderDAO.changeStatus(id,"processing");
        return "forward:/order_details/"+id;
    }

    @Autowired
    OrderBL orderBL;

    @RequestMapping(value = "/antiplagiat/{id_composition}/{id_order}", method = RequestMethod.POST)
    public String antiplagiat(@PathVariable int id_composition, @PathVariable int id_order, Model m){
        double noOriginality = orderBL.antiPlagiarism(id_composition);

        if (noOriginality==0) {
            rating = "0% заимствования. ";
            ratingCat="Котики в восторге.";
            color="green";
        }

        if ((noOriginality>0)&&(noOriginality<=20)) {
            rating = noOriginality + "% заимствования.";
            ratingCat =  "Котики считают, что это хорошо.";
            color="green";
        }

        if ((noOriginality>20)&&(noOriginality<=50)) {
            rating = noOriginality + "% заимствования.";
            ratingCat = "Котики считают, что это приемлимо.";
            color="orange";
        }

        if ((noOriginality>50)&&(noOriginality<=70)) {
            rating = noOriginality + "% заимствования.";
            ratingCat = "Котики не осуждают, но и не одобряют.";
            color="orange";
        }

        if ((noOriginality>70)&&(noOriginality<=100)) {
            rating = noOriginality + "% заимствования.";
            ratingCat = "Котики очень сильно осуждают.";
            color="red";
        }

        return "forward:/order_details/"+id_order;
    }
}
