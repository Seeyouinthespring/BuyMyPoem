package com.buymypoem.springmvc.logic;

import com.buymypoem.springmvc.dao.CompositionDAO;
import com.buymypoem.springmvc.model.UserSession;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

public class compositionBL {

    @Autowired
    CompositionDAO compositionDAO;

    @Resource
    UserSession us;

    public int countPages(String choice){
        int i = compositionDAO.countCompositions(choice, us.getUserSession().getUserID());
        if (i % 2 == 0) return i / 2;
        return i / 2 + 1;
    }
}
