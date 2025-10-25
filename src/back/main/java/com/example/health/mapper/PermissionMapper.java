package com.example.health.mapper;

import com.example.health.domain.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PermissionMapper {
    Permission findById(@Param("id") Long id);
    Permission findByName(@Param("name") String name);
    List<Permission> findAll();
    int insert(Permission permission);
    int update(Permission permission);
    int deleteById(@Param("id") Long id);
    
    // Find permissions by user ID (through roles)
    List<Permission> findByUserId(@Param("userId") Long userId);
}
