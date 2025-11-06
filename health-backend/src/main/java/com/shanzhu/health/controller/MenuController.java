package com.shanzhu.health.controller;

import com.shanzhu.health.config.Unification;
import com.shanzhu.health.entity.Menu;
import com.shanzhu.health.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private IMenuService menuService;
    @GetMapping
    public Unification<List<Menu>> getAllMenu() {
        List<Menu> menuList = menuService.getAllMenu();
        return Unification.success(menuList);
    }
}

