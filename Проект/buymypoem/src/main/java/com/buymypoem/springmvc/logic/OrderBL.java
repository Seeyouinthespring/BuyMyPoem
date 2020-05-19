package com.buymypoem.springmvc.logic;

import com.buymypoem.springmvc.dao.CompositionDAO;
import com.buymypoem.springmvc.model.Composition;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class OrderBL {

    @Autowired
    CompositionDAO compositionDAO;

    public float antiPlagiarism(int composition_id){
        Composition composition = compositionDAO.getCompositionByI(composition_id);
        List<Composition> compositionList = compositionDAO.foundCompositionsForAntiplagiarism
                (composition.getType().getTypeID(), composition.getGenre().getGenreID());

        String[] text = editText(composition.getText());
        float f = 1/2;
        return f;
    }

    private String[] editText(String text){
        text = text.toLowerCase();
        text = text.replaceAll("\\pP","");
        text = text.replaceAll("\n|\r|\r\n"," ");

        String[] textString = text.split("\\s+");
        return textString;
    }
}
