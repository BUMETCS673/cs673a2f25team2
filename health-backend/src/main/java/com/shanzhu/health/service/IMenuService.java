package com.shanzhu.health.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shanzhu.health.entity.Menu;

import java.util.List;

public interface IMenuService extends IService<Menu> {

    List<Menu> getAllMenu();

    List<Menu> getMenuListByUserId(Integer userId);
}
