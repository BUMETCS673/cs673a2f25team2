package com.shanzhu.health.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanzhu.health.entity.RoleMenu;
import com.shanzhu.health.mapper.RoleMenuMapper;
import com.shanzhu.health.service.IRoleMenuService;
import org.springframework.stereotype.Service;

@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {
}
