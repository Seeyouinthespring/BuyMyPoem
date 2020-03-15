package com.buymypoem.springmvc.logic;

import com.buymypoem.springmvc.dao.CompositionDAO;
import org.springframework.beans.factory.annotation.Autowired;

public class compositionBL {

    @Autowired
    CompositionDAO compositionDAO;

    public int countPagesForComposition() {
            int i = compositionDAO.countCompositions();
            if (i % 2 == 0) return i / 2;
            return i / 2 + 1;
    }

    public int countPagesForPublishedComposition() {
        int i = compositionDAO.countPublishedCompositions();
        if (i % 2 == 0) return i / 2;
        return i / 2 + 1;
    }


}
