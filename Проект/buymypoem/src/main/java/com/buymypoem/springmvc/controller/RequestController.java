package com.buymypoem.springmvc.controller;

import com.buymypoem.springmvc.dao.CommentDAO;
import com.buymypoem.springmvc.dao.GenreDAO;
import com.buymypoem.springmvc.dao.RequestDAO;
import com.buymypoem.springmvc.dao.TypeDAO;
import com.buymypoem.springmvc.logic.ProfileBL;
import com.buymypoem.springmvc.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.buymypoem.springmvc.logic.RequestBL;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class RequestController {

    @Resource
    UserSession us;

    @Autowired
    RequestDAO requestDAO;

    @Autowired
    RequestBL requestBL;

    @Autowired
    TypeDAO typeDAO;

    @Autowired
    GenreDAO genreDAO;

    @Autowired
    ProfileBL profileBL;

    @Autowired
    CommentDAO commentDAO;

    @RequestMapping(value = "/requests", method = RequestMethod.GET)
    public String getAllRequestsStart(Model m){
        List<Request> list= requestDAO.getRequests(1,false,1);
        String p = list.get(0).getUser().getPhoto();

        p=p;
        for (Request request: list){
            request.getUser().setPhoto(profileBL.getImg(request.getUser().getPhoto()));
        }
        m.addAttribute("list",list);
        m.addAttribute("page",1);
        int endPage= requestBL.countPages(false);
        m.addAttribute("end", endPage);
        return "request";
    }

    @RequestMapping(value = "/requests/{page}", method = RequestMethod.GET)
    public String getAllRequests(@PathVariable int page, Model m){
        List<Request> list= requestDAO.getRequests(page,false,1);
        for (Request request: list){
            request.getUser().setPhoto(profileBL.getImg(request.getUser().getPhoto()));
        }
        m.addAttribute("list",list);
        int endPage= requestBL.countPages(false);
        m.addAttribute("end", endPage);
        m.addAttribute("page",page);
        return "request";
    }

    @RequestMapping(value = "/personalrequests", method= RequestMethod.GET)
    public String getPersonalRequestsStart(Model m){
        List<Request> list=requestDAO.getRequests(1, true, us.getUserSession().getUserID());
        m.addAttribute("list",list);
        m.addAttribute("page",1);
        int endPage=requestBL.countPages(true);
        m.addAttribute("end", endPage);
        return "personal_request";
    }

    @RequestMapping(value = "/personalrequests/{page}", method= RequestMethod.GET)
    public String getPersonalRequests(@PathVariable int page, Model m){
        List<Request> list=requestDAO.getRequests(page, true, us.getUserSession().getUserID());
        m.addAttribute("list",list);
        int endPage=requestBL.countPages(true);
        m.addAttribute("end", endPage);
        m.addAttribute("page",page);
        return "personal_request";
    }

    @RequestMapping(value = "/add_request_form", method = RequestMethod.GET)
    public String getForm(Model m){
        List<Genre> genreList = genreDAO.getAllGenres();
        List<Type> typeList = typeDAO.getAllTypes();
        m.addAttribute("genres", genreList);
        m.addAttribute("types", typeList);
        m.addAttribute("req", new Request());
        m.addAttribute("user", us.getUserSession());
        return "add_request_form";
    }

    @RequestMapping(value = "/add_request", method = RequestMethod.POST)
    public String addRequest(@ModelAttribute("req") Request req){
        req.getUser().setUserID(us.getUserSession().getUserID());
        requestDAO.addRequest(req);
        return "redirect:/successCustomer";
    }

    @Secured("ROLE_USER_AUTHOR")
    @RequestMapping(value = "/add_response/{id}", method = RequestMethod.POST)
    public String addResponse(@PathVariable int id){
        AuthorRequest ar = new AuthorRequest();
        ar.setRequestID(id);
        ar.setAuthorID(us.getUserSession().getUserID());
        requestDAO.addResponse(ar);
        return "redirect:/successAuthor";
    }

    @RequestMapping(value = "/all_responses/{id}", method = RequestMethod.GET)
    public String getAllResponses(@PathVariable int id, Model model){
        List<User> ulist = requestDAO.getAllResponses(id);
        model.addAttribute("ulist",ulist);
        return "all_responses";
    }

    @RequestMapping(value="/request/{id}", method = RequestMethod.POST)
    public String showRequest(@PathVariable int id, Model m){
        Request request = requestDAO.getRequestById(id);
        request.getUser().setPhoto(profileBL.getImg(request.getUser().getPhoto()));
        List<Comment> commentList = commentDAO.GetCommentsForRequest(id,"request");
        for (Comment comment: commentList){
            comment.getUser().setPhoto(profileBL.getImg(comment.getUser().getPhoto()));
        }
        m.addAttribute("req",request);
        m.addAttribute("comments",commentList);
        m.addAttribute("mycomment",new Comment());
        return "request_details";
    }

    @RequestMapping(value="/add_comment_request/{id}",method=RequestMethod.POST)
    public String addCommentRequest(@PathVariable int id, @ModelAttribute("mycomment") Comment comment){
        User u = new User();
        u.setUserID(us.getUserSession().getUserID());
        comment.setUser(u);
        long newComment = commentDAO.addComment(comment);
        commentDAO.addCommentLink(newComment,id,"request");
        return "forward:/request/"+id;
    }
}
