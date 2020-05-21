package com.buymypoem.springmvc.controller;

import com.buymypoem.springmvc.dao.*;
import com.buymypoem.springmvc.logic.ProfileBL;
import com.buymypoem.springmvc.model.*;
import com.buymypoem.springmvc.logic.compositionBL;
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

    @Autowired
    TypeDAO typeDAO;

    @Autowired
    GenreDAO genreDAO;

    @Autowired
    MarkDAO markDAO;

    private int find_a_composition_by_type=1;
    private int find_a_composition_by_genre=1;
    private String find_a_composition_by_title="";
    private int find_a_composition_by_author=0;

    @RequestMapping(value = "/composition", method= RequestMethod.GET)
    public String getStartList(Model m){
        List<Composition> list=compositionDAO.getCompositions(1,"All", 0);
        m.addAttribute("list",list);
        m.addAttribute("page",1);
        int endPage=compositionBL.countPages("countComposition");
        m.addAttribute("end", endPage);
        return "composition";
    }

    @RequestMapping(value = "/composition/{page}", method= RequestMethod.GET)
    public String getList(@PathVariable int page, Model m){
        List<Composition> list=compositionDAO.getCompositions(page,"All", 0);
        m.addAttribute("list",list);
        int endPage=compositionBL.countPages("countComposition");
        m.addAttribute("end", endPage);
        m.addAttribute("page",page);
        return "composition";
    }

    @RequestMapping(value = "/draft", method= RequestMethod.GET)
    public String getDrafts(Model m){
        List<Composition> list=compositionDAO.getCompositions(1,"Drafts", us.getUserSession().getUserID());
        m.addAttribute("list",list);
        m.addAttribute("page",1);
        int endPage=compositionBL.countPages("countDrafts");
        m.addAttribute("end", endPage);
        return "draft";
    }

    @RequestMapping(value = "/draft/{page}", method= RequestMethod.GET)
    public String getDrafts(@PathVariable int page, Model m){
        List<Composition> list=compositionDAO.getCompositions(page,"Drafts", us.getUserSession().getUserID());
        m.addAttribute("list",list);
        int endPage=compositionBL.countPages("countDrafts");
        m.addAttribute("end", endPage);
        m.addAttribute("page",page);
        return "draft";
    }

    @RequestMapping(value = "/purchases", method= RequestMethod.GET)
    public String getPurchasesStart(Model m){
        List<Composition> list=compositionDAO.getCompositions(1,"MyPurchases", us.getUserSession().getUserID());
        m.addAttribute("list",list);
        m.addAttribute("page",1);
        int endPage=compositionBL.countPages("countPurchases");
        m.addAttribute("end", endPage);
        return "purchases";
    }

    @RequestMapping(value = "/purchases/{page}", method= RequestMethod.GET)
    public String getPurchases(@PathVariable int page, Model m){
        List<Composition> list=compositionDAO.getCompositions(page,"MyPurchases", us.getUserSession().getUserID());
        m.addAttribute("list",list);
        int endPage=compositionBL.countPages("countPurchases");
        m.addAttribute("end", endPage);
        m.addAttribute("page",page);
        return "purchases";
    }

    @RequestMapping(value = "/my_purchase/{id}", method= RequestMethod.GET)
    public String getPurchaseInfo(@PathVariable int id, Model m){
        Composition c = compositionDAO.getPurchaseById(id);
        User me = us.getUserSession();
        m.addAttribute("user", me);
        m.addAttribute("text", c);
        return "purchase_info";
    }

    @RequestMapping(value = "/all_composition", method= RequestMethod.GET)
    public String getComposition_start(Model m){
        m.addAttribute("genres", genreDAO.getAllGenres());
        m.addAttribute("types", typeDAO.getAllTypes());

        m.addAttribute("fgenre", new Genre());
        m.addAttribute("ftype", new Type());

        List<Composition> list;
        int endPage;
        if ((find_a_composition_by_type!=1)||(find_a_composition_by_genre!=1)||
                (find_a_composition_by_author!=0)||(!(find_a_composition_by_title.equals("")))){
            list=compositionDAO.foundCompositions(1, find_a_composition_by_type, find_a_composition_by_genre,
                    find_a_composition_by_author, find_a_composition_by_title);
            for (Composition c: list) {
                c.getUser().setPhoto(profileBL.getImg(c.getUser().getPhoto()));
            }
            endPage=compositionBL.countFindPages(find_a_composition_by_type, find_a_composition_by_genre,
                    find_a_composition_by_author, find_a_composition_by_title);
        }else{
            list= compositionBL.allComposition(1);
            endPage=compositionBL.countPages("countPublishComp");
        }

        if (list.size()==0) m.addAttribute("msg", "В системе нет того что вы ищете (((");
        m.addAttribute("list",list);
        m.addAttribute("page",1);
        m.addAttribute("end", endPage);
        return "all_composition";
    }

    @RequestMapping(value = "/all_composition/{page}", method= RequestMethod.GET)
    public String getComposition_next(@PathVariable int page, Model m){
        m.addAttribute("genres", genreDAO.getAllGenres());
        m.addAttribute("types", typeDAO.getAllTypes());

        m.addAttribute("fgenre", new Genre());
        m.addAttribute("ftype", new Type());

        List<Composition> list;
        int endPage;
        if ((find_a_composition_by_type!=1)||(find_a_composition_by_genre!=1)||
                (find_a_composition_by_author!=0)||(!(find_a_composition_by_title.equals("")))){
            list=compositionDAO.foundCompositions(page, find_a_composition_by_type, find_a_composition_by_genre,
                    find_a_composition_by_author, find_a_composition_by_title);
            for (Composition c: list) {
                c.getUser().setPhoto(profileBL.getImg(c.getUser().getPhoto()));
            }
            endPage=compositionBL.countFindPages(find_a_composition_by_type, find_a_composition_by_genre,
                    find_a_composition_by_author, find_a_composition_by_title);
        }else{
            list= compositionBL.allComposition(page);
            endPage=compositionBL.countPages("countPublishComp");
        }

        m.addAttribute("list",list);
        m.addAttribute("end", endPage);
        m.addAttribute("page", page);
        return "all_composition";
    }

    @RequestMapping(value = "/index", method= RequestMethod.GET)
    public String getCompositionStart(Model m){
        m.addAttribute("genres", genreDAO.getAllGenres());
        m.addAttribute("types", typeDAO.getAllTypes());

        m.addAttribute("fgenre", new Genre());
        m.addAttribute("ftype", new Type());

        List<Composition> list;
        int endPage;
        if ((find_a_composition_by_type!=1)||(find_a_composition_by_genre!=1)||
                (find_a_composition_by_author!=0)||(!(find_a_composition_by_title.equals("")))){
            list=compositionDAO.foundCompositions(1, find_a_composition_by_type, find_a_composition_by_genre,
                    find_a_composition_by_author, find_a_composition_by_title);
            for (Composition c: list) {
                c.getUser().setPhoto(profileBL.getImg(c.getUser().getPhoto()));
            }
            endPage=compositionBL.countFindPages(find_a_composition_by_type, find_a_composition_by_genre,
                    find_a_composition_by_author, find_a_composition_by_title);
        }else{
            list= compositionBL.allComposition(1);
            endPage=compositionBL.countPages("countPublishComp");
        }

        if (list.size()==0) m.addAttribute("msg", "В системе нет того что вы ищете (((");
        m.addAttribute("list",list);
        m.addAttribute("page",1);
        m.addAttribute("end", endPage);
        return "index";
    }

    @RequestMapping(value = "/index/{page}", method= RequestMethod.GET)
    public String getComposition(@PathVariable int page, Model m){
        m.addAttribute("genres", genreDAO.getAllGenres());
        m.addAttribute("types", typeDAO.getAllTypes());

        m.addAttribute("fgenre", new Genre());
        m.addAttribute("ftype", new Type());

        List<Composition> list;
        int endPage;
        if ((find_a_composition_by_type!=1)||(find_a_composition_by_genre!=1)||
                (find_a_composition_by_author!=0)||(!(find_a_composition_by_title.equals("")))){
            list=compositionDAO.foundCompositions(page, find_a_composition_by_type, find_a_composition_by_genre,
                    find_a_composition_by_author, find_a_composition_by_title);
            for (Composition c: list) {
                c.getUser().setPhoto(profileBL.getImg(c.getUser().getPhoto()));
            }
            endPage=compositionBL.countFindPages(find_a_composition_by_type, find_a_composition_by_genre,
                    find_a_composition_by_author, find_a_composition_by_title);
        }else{
            list= compositionBL.allComposition(page);
            endPage=compositionBL.countPages("countPublishComp");
        }

        if (list.size()==0) m.addAttribute("msg", "В системе нет того что вы ищете (((");
        m.addAttribute("list",list);
        m.addAttribute("end", endPage);
        m.addAttribute("page", page);
        return "index";
    }

    @RequestMapping(value ="/add_composition_form", method=RequestMethod.GET)
    public String getForm(Model m){
        List<Genre> genreList = genreDAO.getAllGenres();
        List<Type> typeList = typeDAO.getAllTypes();
        m.addAttribute("genres",genreList);
        m.addAttribute("types",typeList);
        m.addAttribute("comp", new Composition());
        m.addAttribute("user", us.getUserSession());
        return "add_composition_form";
    }

    @RequestMapping(value = "/add_composition", method= RequestMethod.POST)
    public String addComposition(@ModelAttribute("comp") Composition comp){
        comp.getUser().setUserID(us.getUserSession().getUserID());
        compositionDAO.addComposition(comp);
        return "redirect:/successAuthor";
    }

    @Autowired
    ProfileBL profileBL;

    @Autowired
    CommentDAO commentDAO;

    @RequestMapping(value = "/composition_info/{id}", method= RequestMethod.GET)
    public String getCompositionInfo(@PathVariable int id, Model m){
        Composition composition = compositionDAO.getCompositionByI(id);
        composition.getUser().setPhoto(profileBL.getImg(composition.getUser().getPhoto()));
        List<Comment> commentList = commentDAO.GetCommentsForRequest(id,"composition");
        for (Comment comment: commentList){
            comment.getUser().setPhoto(profileBL.getImg(comment.getUser().getPhoto()));
        }
        User me = us.getUserSession();
        me.setPhoto(profileBL.getImg(me.getPhoto()));
        Mark mark = markDAO.getMark(id,me.getUserID());
        m.addAttribute("mark",mark);
        m.addAttribute("me", me);
        m.addAttribute("comments", commentList);
        m.addAttribute("mycomment",new Comment());
        m.addAttribute("text",composition);
        return "/composition_info";
    }

    @RequestMapping(value="/add_comment_composition/{id}",method=RequestMethod.POST)
    public String addCommentComposition(@PathVariable int id, @ModelAttribute("mycomment") Comment comment){
        User u = new User();
        u.setUserID(us.getUserSession().getUserID());
        comment.setUser(u);
        long newComment = commentDAO.addComment(comment);
        commentDAO.addCommentLink(newComment,id,"composition");
        return "redirect:/composition_info/"+id;
    }

    @Autowired
    UserDAO userDAO;

    @RequestMapping(value="/find_composition",method=RequestMethod.POST)
    public String findComposition(@ModelAttribute("ftype") Type type,
                                  @ModelAttribute("fgenre") Genre genre,
                                  @ModelAttribute("stitle") String stitle,
                                  @ModelAttribute("slogin") String slogin){

        find_a_composition_by_type=type.getTypeID();
        find_a_composition_by_genre=genre.getGenreID();
        find_a_composition_by_title=stitle;
        if (!(slogin.equals(""))){
            User u=userDAO.getUserByLogin(slogin);
            find_a_composition_by_author=userDAO.getAuthorById(u.getUserID()).getAuthorID();
        }

        if (us.getUserSession().getUserID()==0){
            return "redirect:/index";
        }
        return  "redirect:/all_composition";
    }

    @RequestMapping(value="/publish_composition/{id}",method=RequestMethod.GET)
    public String publishMyComposition(@PathVariable int id){
        compositionDAO.changeStatus(id,"published");
        return "redirect:/draft";
    }

    @RequestMapping(value ="/update_form/{id}", method=RequestMethod.GET)
    public String getUpdateForm(@PathVariable int id, Model m){
        Composition composition = compositionDAO.getCompositionByI(id);
        if((composition.getUser().getUserID()==us.getUserSession().getUserID())&(composition.getStatus().equals("В черновике"))){
            List<Genre> genreList = genreDAO.getAllGenres();
            List<Type> typeList = typeDAO.getAllTypes();
            m.addAttribute("genres",genreList);
            m.addAttribute("types",typeList);
            m.addAttribute("comp", composition);
            m.addAttribute("user", us.getUserSession());
            return "update_composition_form";
        }
        return "error";
    }

    @RequestMapping(value = "/update_composition", method= RequestMethod.POST)
    public String updateComposition(@ModelAttribute("comp") Composition comp){
        compositionDAO.updateComposition(comp);
        return "redirect:/successAuthor";
    }

    @RequestMapping(value ="/delete_draft/{id}", method=RequestMethod.GET)
    public String dropCompositionDraft(@PathVariable int id, Model m){
        Composition composition = compositionDAO.getCompositionByI(id);
        if((composition.getUser().getUserID()==us.getUserSession().getUserID())&(composition.getStatus().equals("В черновике"))){
            compositionDAO.dropComposition(id);
            return "redirect:/successAuthor";
        }
        return "error";
    }

    @RequestMapping(value = "/rating_composition", method= RequestMethod.GET)
    public String getRatingComposition(Model m){
        List<Composition> list=compositionBL.getRatingCompositionList(1);
        if (list.size()==0) m.addAttribute("msg", "Рейтинговых копозиций не обнаружено. " +
                "Навестите эту cтраницу позднее");
        for (Composition c: list){
            c.getUser().setPhoto(profileBL.getImg(c.getUser().getPhoto()));
        }
        m.addAttribute("list",list);
        m.addAttribute("page",1);
        int endPage=compositionBL.countPagesRatingComposition();
        m.addAttribute("end", endPage);
        return "rating_composition";
    }

    @RequestMapping(value = "/rating_composition/{page}", method= RequestMethod.GET)
    public String getRatingComposition(@PathVariable int page, Model m){
        List<Composition> list=compositionBL.getRatingCompositionList(page);
        for (Composition c: list){
            c.getUser().setPhoto(profileBL.getImg(c.getUser().getPhoto()));
        }
        m.addAttribute("list",list);
        int endPage=compositionBL.countPages("countDrafts");
        m.addAttribute("end", endPage);
        m.addAttribute("page",page);
        return "rating_composition";
    }

    @RequestMapping(value = "/like/{id}", method= RequestMethod.GET)
    public String likePressed(@PathVariable int id, Model m){
        Composition c = compositionDAO.getCompositionByI(id);
        int my_id = us.getUserSession().getUserID();

        if (c.getStatus().equals("Опубликовано")){
            Mark mark = markDAO.getMark(id, my_id);
            if (mark==null){
                markDAO.addMark(id,my_id,true);
                compositionDAO.changeLikeOrDislikeNumber(id,"like+");
            }else {
                if (mark.isMark()){
                    markDAO.dropMark(id,my_id);
                    compositionDAO.changeLikeOrDislikeNumber(id,"like-");
                }else if(!mark.isMark()){
                    markDAO.changeMark(id,my_id,true);
                    compositionDAO.changeLikeOrDislikeNumber(id,"dislike-");
                    compositionDAO.changeLikeOrDislikeNumber(id,"like+");
                }
            }
            return "redirect:/composition_info/"+id;
        }else return "error";
    }

    @RequestMapping(value = "/dislike/{id}", method= RequestMethod.GET)
    public String dislikePressed(@PathVariable int id, Model m){
        Composition c = compositionDAO.getCompositionByI(id);
        int my_id = us.getUserSession().getUserID();

        if (c.getStatus().equals("Опубликовано")){
            Mark mark = markDAO.getMark(id, my_id);
            if (mark==null){
                markDAO.addMark(id,my_id,false);
                compositionDAO.changeLikeOrDislikeNumber(id,"dislike+");
            }else {
                if (mark.isMark()){
                    markDAO.changeMark(id,my_id,false);
                    compositionDAO.changeLikeOrDislikeNumber(id,"like-");
                    compositionDAO.changeLikeOrDislikeNumber(id,"dislike+");
                }else if(!mark.isMark()){
                    markDAO.dropMark(id,my_id);
                    compositionDAO.changeLikeOrDislikeNumber(id,"dislike-");
                }
            }
            return "redirect:/composition_info/"+id;
        }else return "error";
    }
}


