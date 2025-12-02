package com.shanzhu.health.utils;

/**
 * 密码生成工具类
 * 用于生成BCrypt加密后的密码，用于更新数据库中的明文密码
 * 
 * 使用方法：
 * 1. 运行此main方法
 * 2. 复制输出的BCrypt密码
 * 3. 在MySQL Workbench中执行SQL更新语句
 */
public class PasswordGenerator {
    public static void main(String[] args) {
        // 要加密的原始密码
        String password = "123456";
        
        // 生成BCrypt加密后的密码
        String encodedPassword = PasswordUtil.encode(password);
        
        // 输出结果
        System.out.println("==========================================");
        System.out.println("密码加密工具");
        System.out.println("==========================================");
        System.out.println("原始密码: " + password);
        System.out.println("BCrypt加密后的密码: " + encodedPassword);
        System.out.println("==========================================");
        System.out.println("\n【SQL更新语句】");
        System.out.println("请在MySQL Workbench中执行以下SQL语句：\n");
        System.out.println("-- 更新admin账户的密码");
        System.out.println("UPDATE j_user SET password = '" + encodedPassword + "' WHERE username = 'admin';");
        System.out.println("\n-- 或者更新所有使用明文密码'123456'的用户（可选）");
        System.out.println("UPDATE j_user SET password = '" + encodedPassword + "' WHERE password = '123456';");
        System.out.println("\n-- 验证更新结果");
        System.out.println("SELECT id, username, password FROM j_user WHERE username = 'admin';");
        System.out.println("==========================================");
    }
}

