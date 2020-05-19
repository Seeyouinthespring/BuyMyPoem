package com.buymypoem.springmvc.logic;

import com.buymypoem.springmvc.dao.CompositionDAO;
import com.buymypoem.springmvc.model.Composition;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Arrays;
import java.util.List;

public class OrderBL {

    @Autowired
    CompositionDAO compositionDAO;

    public float antiPlagiarism(int composition_id){
        Composition composition = compositionDAO.getCompositionByI(composition_id);
        List<Composition> compositionList = compositionDAO.foundCompositionsForAntiplagiarism
                (composition.getType().getTypeID(), composition.getGenre().getGenreID());
        int numberOfStolenWords = 0;
        String[] sourceText = editText(composition.getText());

        for (Composition c: compositionList) {
            String[] verifiableText = editText(c.getText());
            int quantity = sourceText.length<verifiableText.length ? matchingLetters(sourceText, verifiableText) :
                                                                     matchingLetters(sourceText, verifiableText);
            if (quantity>0){
                int i=0;
                while (i<sourceText.length){
                    int j = Arrays.asList(verifiableText).indexOf(sourceText[i]);
                    if (j>0){
                        int k = 1;
                        while (true){
                            if (sourceText[i++].equals(verifiableText[j++])) k++;
                            else break;
                        }
                        if(k>3) numberOfStolenWords+=k;
                    }else i++;
                }
            }
        }
        float f = sourceText.length/numberOfStolenWords;
        return f;
    }

    private String[] editText(String text){
        text = text.toLowerCase();
        text = text.replaceAll("\\pP","");
        text = text.replaceAll("\n|\r|\r\n"," ");

        String[] textString = text.split("\\s+");
        return textString;
    }

    private int matchingLetters(String[] arrayTextOne, String[] arrayTextTwo){
        String[] pretext = {"в", "без", "до", "из", "к", "на", "по", "о", "от", "перед", "при",
                            "через", "с", "у", "и", "нет", "за", "над", "для", "об", "под", "про"};
        int quantity = 0;
        for (String s: arrayTextOne) {
            int j = 0;
             j = Arrays.asList(pretext).indexOf(s);
            if(j>0) j = Arrays.asList(arrayTextTwo).indexOf(s);
            if (j>0) quantity+=j;
        }
        return quantity;
    }
}
