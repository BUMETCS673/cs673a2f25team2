package com.shanzhu.health.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanzhu.health.entity.UserRole;
import com.shanzhu.health.mapper.UserRoleMapper;
import com.shanzhu.health.service.IUserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
