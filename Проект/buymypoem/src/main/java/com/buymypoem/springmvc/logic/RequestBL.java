package com.buymypoem.springmvc.logic;

import com.buymypoem.springmvc.dao.CompositionDAO;
import com.buymypoem.springmvc.dao.RequestDAO;
import com.buymypoem.springmvc.model.UserSession;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

public class RequestBL {
    @Autowired
    RequestDAO requestDAO;

    @Resource
    UserSession us;
    public static final int PAGE_SIZE = 3;

    public int countPages(boolean isPersonal){
        int i = requestDAO.countRequests(isPersonal,us.getUserSession().getUserID());
        if (i % PAGE_SIZE == 0) return i / PAGE_SIZE;
        return i / PAGE_SIZE + 1;
    }

    public int countPagesFind(int typeId, int genreId, int customerId){
        int i = requestDAO.countRequestsFind(typeId, genreId, customerId);
        if (i % PAGE_SIZE == 0) return i / PAGE_SIZE;
        return i / PAGE_SIZE + 1;
    }
}
