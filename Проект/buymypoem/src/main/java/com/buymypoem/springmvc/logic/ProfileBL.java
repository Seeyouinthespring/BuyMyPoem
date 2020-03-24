package com.buymypoem.springmvc.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

public class ProfileBL {

    public String getImg(String photoUrl){
        File file = new File(photoUrl);
        byte[] photo = new byte[(int)file.length()];
        FileInputStream f = null;
        try {
            f = new FileInputStream(file);
            f.read(photo);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String userPhoto = Base64.getEncoder().encodeToString(photo);
        return userPhoto;
    }

}
