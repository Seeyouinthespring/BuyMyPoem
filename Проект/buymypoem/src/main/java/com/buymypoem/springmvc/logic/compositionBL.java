package com.buymypoem.springmvc.logic;

import com.buymypoem.springmvc.dao.AuthorDAO;
import com.buymypoem.springmvc.dao.CompositionDAO;
import com.buymypoem.springmvc.dao.UserDAO;
import com.buymypoem.springmvc.model.Composition;
import com.buymypoem.springmvc.model.User;
import com.buymypoem.springmvc.model.UserSession;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.*;

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

    private int average_likes(){
        int number_of_composition=compositionDAO.countCompositions("countPublishComp", 0);
        int sum_likes=compositionDAO.sumLikes();
        return sum_likes/number_of_composition;
    }

    public List<Composition> getRatingCompositionList(int page){
        return compositionDAO.RatingOfComposition(page, average_likes());
    }

    public int countPagesRatingComposition(){
        int i;
        i = compositionDAO.countPagesRatingComposition(average_likes);
        if (i % PAGE_SIZE == 0) return i / PAGE_SIZE;
        return i / PAGE_SIZE + 1;
    }

    @Autowired UserDAO userDAO;
    int countPagesAuthorRating;
    public List<User> getAuthorRating(int page){
        List<Composition> compositionList = compositionDAO.RatingOfCompositionAll(average_likes());
        Map<String, Integer> authors = new HashMap<String, Integer>();
        for (Composition c: compositionList) {
            if(authors.containsKey(c.getUser().getLogin())){
                authors.put(c.getUser().getLogin(), authors.get(c.getUser().getLogin())+1);
            }else {
                authors.put(c.getUser().getLogin(), 1);
            }
        }
        List<User> authorList = new ArrayList<>();
        int max = Collections.max(authors.values());
        while (max!=0){
            String login=null;
            for (String slogin: authors.keySet())
                if(authors.get(slogin)==max) login = slogin;
            User user =  userDAO.getUserByLogin(login);
            user.setNumb_composition(max);
            if(authors.containsValue(max)) authorList.add(user);
            authors.remove(login);
            if(authors.size()!=0) max = Collections.max(authors.values());
            else max=0;
        }
        countPagesAuthorRating=authorList.size();
        List<User> authorListFinish = new ArrayList<>();
        int end=page+3-1<countPagesAuthorRating ? (page-1)*3 + 2 : countPagesAuthorRating-1;
        for(int i=(page-1)*3; i<=end; i++){
            authorListFinish.add(authorList.get(i));
        }
        return authorListFinish;
    }

    public int countPagesAuthorRating(){
        int i = countPagesAuthorRating;
        if (i % PAGE_SIZE == 0) return i / PAGE_SIZE;
        return i / PAGE_SIZE + 1;
    }
}
