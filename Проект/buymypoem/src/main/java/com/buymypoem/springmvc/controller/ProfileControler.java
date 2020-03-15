package com.buymypoem.springmvc.controller;

import com.buymypoem.springmvc.model.UserSession;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
public class ProfileControler {

    @Resource
    UserSession us;
    
}
