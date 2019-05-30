package com.wjy329.wshiro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author wjy329
 * @Time 2019/1/207:00 PM
 * @description
 */

@Controller
@RequestMapping("/perm")
public class PermissionController {
    @RequestMapping("/list")
    public String list(){
        return "perm/list";
    }
}
