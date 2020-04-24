package com.buymypoem.springmvc.logic;

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
        int i = compositionDAO.countCompositions(choice, us.getUserSession().getUserID());
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
}
