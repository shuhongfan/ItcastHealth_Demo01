package com.shf.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {
    @RequestMapping("/add")
    @PreAuthorize("hasAnyAuthority('add')")  // 调用此方法要求当前用户必须由add权限
    public String add(){
        System.out.println("add...");
        return "success";
    }

    @RequestMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")  // 调用此方法要求当前用户必须有ROLE_ADMIN角色
    public String delete(){
        System.out.println("delete...");
        return "success";
    }
}
