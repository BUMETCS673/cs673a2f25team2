package com.shanzhu.health.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shanzhu.health.entity.RoleMenu;

import java.util.List;

public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    //这个方法是为了根据角色ID获取该角色拥有的菜单ID列表。
    public List<Integer> getMenuIdListByRoleId(Integer roleId);
}
