package com.buymypoem.springmvc.logic;

import com.buymypoem.springmvc.dao.AuthorDAO;
import com.buymypoem.springmvc.dao.CompositionDAO;
import com.buymypoem.springmvc.model.Composition;
import com.buymypoem.springmvc.model.UserSession;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class compositionBL {

    @Autowired
    CompositionDAO compositionDAO;

    @Resource
    UserSession us;
    public static final int PAGE_SIZE = 3;

    public int countPages(String choice){
        int i;
        if (us.getUserSession()==null){
            i = compositionDAO.countCompositions(choice, 0);
        }else i = compositionDAO.countCompositions(choice, us.getUserSession().getUserID());

        if (i % PAGE_SIZE == 0) return i / PAGE_SIZE;
        return i / PAGE_SIZE + 1;
    }

    public int countFindPages(int typeId, int genreId, int authorId, String title){
        int i = compositionDAO.getCountFindCompositions(typeId, genreId, authorId, title);
        if (i % PAGE_SIZE == 0) return i / PAGE_SIZE;
        return i / PAGE_SIZE + 1;
    }

    @Autowired
    ProfileBL profileBL;

    public List<Composition> allComposition(int page){
        List<Composition> list=compositionDAO.getCompositions(page,"Published", 0);
        for (Composition c: list) {
            c.getUser().setPhoto(profileBL.getImg(c.getUser().getPhoto()));
        }
        return list;
    }

    @Autowired
    AuthorDAO authorDAO;

    public int countPagesAuthor(){
        int i;
        i = authorDAO.countPages();
        if (i % PAGE_SIZE == 0) return i / PAGE_SIZE;
        return i / PAGE_SIZE + 1;
    }

    public int countPagesAuthorById(int id){
        int i;
        i = authorDAO.countPagesAutorById(id);
        if (i % PAGE_SIZE == 0) return i / PAGE_SIZE;
        return i / PAGE_SIZE + 1;
    }

    int average_likes=0;

    public List<Composition> getRatingCompositionList(int page){
        int number_of_composition=compositionDAO.countCompositions("countPublishComp", 0);
        int sum_likes=compositionDAO.sumLikes();
        average_likes=sum_likes/number_of_composition;

        return compositionDAO.RatingOfComposition(page, average_likes);
    }

    public int countPagesRatingComposition(){
        int i;
        i = compositionDAO.countPagesRatingComposition(average_likes);
        if (i % PAGE_SIZE == 0) return i / PAGE_SIZE;
        return i / PAGE_SIZE + 1;
    }

}
