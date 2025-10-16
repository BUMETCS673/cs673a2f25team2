package com.example.health.mapper;

import com.example.health.domain.Role;
import com.example.health.domain.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper {
    Role findById(@Param("id") Long id);
    Role findByName(@Param("name") String name);
    List<Role> findAll();
    int insert(Role role);
    int update(Role role);
    int deleteById(@Param("id") Long id);
    
    // User-Role relationships
    List<Role> findByUserId(@Param("userId") Long userId);
    int assignRoleToUser(@Param("userId") Long userId, @Param("roleId") Long roleId);
    int removeRoleFromUser(@Param("userId") Long userId, @Param("roleId") Long roleId);
    
    // Role-Permission relationships
    List<Permission> findPermissionsByRoleId(@Param("roleId") Long roleId);
    int assignPermissionToRole(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);
    int removePermissionFromRole(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);
}
