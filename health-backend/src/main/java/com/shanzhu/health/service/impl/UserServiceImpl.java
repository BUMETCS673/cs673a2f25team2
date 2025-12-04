package com.shanzhu.health.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanzhu.health.config.JwtConfig;
import com.shanzhu.health.entity.Body;
import com.shanzhu.health.entity.Menu;
import com.shanzhu.health.entity.User;
import com.shanzhu.health.entity.UserRole;
import com.shanzhu.health.mapper.UserMapper;
import com.shanzhu.health.mapper.UserRoleMapper;
import com.shanzhu.health.service.IBodyService;
import com.shanzhu.health.service.IMenuService;
import com.shanzhu.health.service.IUserRoleService;
import com.shanzhu.health.service.IUserService;
import com.shanzhu.health.utils.DataEncryptionUtil;
import com.shanzhu.health.utils.PasswordUtil;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Autowired
    private IMenuService menuService;


    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private JwtConfig jwtConfig;


    private User loginUser = null;
    @Autowired
    private IBodyService bodyMapper;

    private static final String PAYMENT_STATUS_UNPAID = "UNPAID";
    private static final String PAYMENT_STATUS_ACTIVE = "ACTIVE";
    private static final String PAYMENT_STATUS_EXPIRED = "EXPIRED";
    
    /**
     * Decrypt user sensitive information (email, phone)
     * @param user User object
     */
    private void decryptUserSensitiveData(User user) {
        if (user != null) {
            if (user.getEmail() != null && !user.getEmail().isEmpty()) {
                user.setEmail(DataEncryptionUtil.decrypt(user.getEmail()));
            }
            if (user.getPhone() != null && !user.getPhone().isEmpty()) {
                user.setPhone(DataEncryptionUtil.decrypt(user.getPhone()));
            }
        }
    }
    
    /**
     * Encrypt user sensitive information (email, phone)
     * @param user User object
     */
    private void encryptUserSensitiveData(User user) {
        if (user != null) {
            if (user.getEmail() != null && !user.getEmail().isEmpty()) {
                user.setEmail(DataEncryptionUtil.encrypt(user.getEmail()));
            }
            if (user.getPhone() != null && !user.getPhone().isEmpty()) {
                user.setPhone(DataEncryptionUtil.encrypt(user.getPhone()));
            }
            if (user.getPaymentStatus() == null || user.getPaymentStatus().isEmpty()) {
                user.setPaymentStatus(PAYMENT_STATUS_UNPAID);
            }
        }
    }


    @Override
    public Map<String, Object> login(User user) {
        // Query user by username
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        User loginUser = this.baseMapper.selectOne(wrapper);

        // Verify password (using BCrypt encryption verification)
        if (loginUser != null && PasswordUtil.matches(user.getPassword(), loginUser.getPassword())) {
            // Set user password to null to avoid password leakage
            loginUser.setPassword(null);

            refreshPaymentStatus(loginUser);

            String token = jwtConfig.createToken(loginUser); // Create Token
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            data.put("username", loginUser.getUsername()); // Add username
            data.put("id", loginUser.getId()); // Add user ID
            data.put("paymentStatus", loginUser.getPaymentStatus());
            data.put("accessExpiry", loginUser.getAccessExpiry());
            return data;
        }
        return null;
    }


    @Override
    public Map<String, Object> Wxlogin(User user) {
        // Query user by username
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        User loginUser = this.baseMapper.selectOne(wrapper);

        // Verify password (using BCrypt encryption verification)
        if (loginUser != null && PasswordUtil.matches(user.getPassword(), loginUser.getPassword())) {
            // Set user password to null to avoid password leakage
            loginUser.setPassword(null);

            String token = jwtConfig.createToken(loginUser); // Create Token
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            return data;
        }
        return null;
    }


    @Override
    public Map<String, Object> getUserInfo(String token) {
        try {
            // Parse token through JWT to get user information
            loginUser = jwtConfig.parseToken(token, User.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (loginUser != null) {
            refreshPaymentStatus(loginUser);

            // Decrypt sensitive fields before returning to clients
            decryptUserSensitiveData(loginUser);

            // If user information is obtained, assemble return data
            Map<String, Object> data = new HashMap<>();
            data.put("name", loginUser.getUsername());
            data.put("username", loginUser.getUsername());
            data.put("avatar", loginUser.getAvatar());
            data.put("id", loginUser.getId());
            data.put("email", loginUser.getEmail());
            data.put("phone", loginUser.getPhone());
            data.put("status", loginUser.getStatus());
            data.put("paymentStatus", loginUser.getPaymentStatus());
            data.put("accessExpiry", loginUser.getAccessExpiry());
            // Get user role list
            List<String> roleList = this.baseMapper.getRoleNameByUserId(loginUser.getId());
            data.put("roles", roleList);

            // Get user menu list
            List<Menu> menuList = menuService.getMenuListByUserId(loginUser.getId());
            data.put("menuList", menuList);
            return data;
        }
        return null;
    }


    @Override
    public void logout(String token) {

    }

    @Override
    public boolean addUser(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        int count = this.baseMapper.selectCount(wrapper).intValue();
        if (count > 0) {
            return false;
        } else {
            // Encrypt password
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                user.setPassword(PasswordUtil.encode(user.getPassword()));
            }
            // Encrypt personal information (email, phone)
            encryptUserSensitiveData(user);
            // Write to user table
            this.baseMapper.insert(user);
            // Write to user role table
            List<Integer> roleIdList = user.getRoleIdList();// Get user role ID list
            if (roleIdList != null) {
                for (Integer roleId : roleIdList) {
                    // Insert role ID and user ID into user role table
                    userRoleMapper.insert(new UserRole(null, user.getId(), roleId));
                }
            }
        }
        return true;

    }

    @Override
    public User getUserById(Integer id) {

        // Query user information by user ID
        User user = this.baseMapper.selectById(id);
        System.out.println(user);
        // Query user role list by user ID
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, id);
        List<UserRole> userRoleList = userRoleMapper.selectList(wrapper); // Query all user roles from user role table and assign to userRoleList variable

        // Set user role ID list to user object
        List<Integer> roleIdList = userRoleList.stream() // Convert userRoleList to a Stream<UserRole> object to operate on each element
                .map(userRole -> {
                    return userRole.getRoleId();
                })
                .collect(Collectors.toList()); // Collect each roleId value into a List<Integer> object and assign to roleIdList variable
        user.setRoleIdList(roleIdList); // Set roleIdList to the roleIdList property of user object

        // Decrypt sensitive information
        decryptUserSensitiveData(user);
        
        return user;
    }


    @Override
    @Transactional
    public void updateUser(User user) {
        // Encrypt personal information (email, phone)
        encryptUserSensitiveData(user);
        // Update user table
        this.baseMapper.updateById(user);
        // Clear existing roles
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, user.getId());
        userRoleMapper.delete(wrapper);
        // Set new roles
        List<Integer> roleIdList = user.getRoleIdList(); // Get user's new role ID list
        if (roleIdList != null) {
            for (Integer roleId : roleIdList) {
                // Set new role
                userRoleMapper.insert(new UserRole(null, user.getId(), roleId));
            }
        }
    }


    @Override
    public void deletUserById(Integer id) {
        // Delete user by user ID
        this.baseMapper.deleteById(id);
        // Clear existing roles
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, id); // Query condition: user ID equals id
        userRoleMapper.delete(wrapper); // Execute delete operation
    }


    @Override
    public Map<String, Object> register(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        Map<String, Object> map = new HashMap<>();
        // Check if username already exists
        wrapper.eq(User::getUsername, user.getUsername());
        Long count = this.baseMapper.selectCount(wrapper);
        if (count > 0) {
            map.put("fail", false);
            return map;
        } else {
            // Encrypt password
            user.setPassword(PasswordUtil.encode(user.getPassword()));
            // Encrypt personal information (email, phone)
            encryptUserSensitiveData(user);
            user.setPaymentStatus(PAYMENT_STATUS_UNPAID);
            user.setAccessExpiry(null);
            user.setAvatar("https://bpic.51yuansu.com/pic2/cover/00/35/43/58119f542530c_610.jpg");
            this.baseMapper.insert(user);
            // Get ID after inserting data
            Integer userId = user.getId();
            // Create UserRole object and set role
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(3);

            // Insert into database
            boolean result = userRoleService.save(userRole);
            if (result) {
                map.put("success", true);
            } else {
                map.put("fail", false);
            }
            return map;
        }
    }


    @Override
    public Map<String, Object> getUserId() {
        Map<String, Object> data = new HashMap<>();
        // Add key "id" with value of current logged-in user's ID
        if (data != null) {
            data.put("id", loginUser.getId());
            return data;
        }
        return null;
    }


    @Override
    public Map<String, Object> WxgetUserId(String token) {
        User WxloginUser = null;
        try {
            // Parse token through JWT to get user information
            WxloginUser = jwtConfig.parseToken(token, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (WxloginUser != null) {
            // If user information is obtained, assemble return data
            Map<String, Object> data = new HashMap<>();
            data.put("id", WxloginUser.getId());
            return data;
        }
        return null;
    }


    @Override
    public Map<String, Object> getBodyInfo() {
        Map<String, Object> data = new HashMap<>();
        data.put("id", loginUser.getId());
        Integer pid = (Integer) data.get("id");

        List<Body> bodyList = bodyMapper.getBodyListByUserId(pid);

        Map<String, Object> result = new HashMap<>();
        result.put("bodyList", bodyList);
        return result;
    }

    @Override
    public boolean updateuser(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        // Query user
        User oldPassword = this.baseMapper.selectOne(wrapper);
        // Verify old password
        if (oldPassword != null && PasswordUtil.matches(user.getPassword(), oldPassword.getPassword())) {
            // Encrypt new password
            oldPassword.setPassword(PasswordUtil.encode(user.getNewPassword()));
            this.baseMapper.updateById(oldPassword);
            return true;
        }
        return false;
    }

    private void refreshPaymentStatus(User user) {
        if (user == null) {
            return;
        }

        if (PAYMENT_STATUS_ACTIVE.equalsIgnoreCase(user.getPaymentStatus())) {
            // Already normalized
        } else if ("PAID".equalsIgnoreCase(user.getPaymentStatus())) {
            // Normalize legacy PAID status to ACTIVE and ensure an expiry date exists
            user.setPaymentStatus(PAYMENT_STATUS_ACTIVE);

            User toUpdate = new User();
            toUpdate.setId(user.getId());
            toUpdate.setPaymentStatus(PAYMENT_STATUS_ACTIVE);

            if (user.getAccessExpiry() == null) {
                LocalDateTime defaultExpiry = LocalDateTime.now().plusMonths(1);
                user.setAccessExpiry(defaultExpiry);
                toUpdate.setAccessExpiry(defaultExpiry);
            }

            this.baseMapper.updateById(toUpdate);
        }

        LocalDateTime expiry = user.getAccessExpiry();
        if (expiry != null && expiry.isBefore(LocalDateTime.now())) {
            user.setPaymentStatus(PAYMENT_STATUS_EXPIRED);
            User toUpdate = new User();
            toUpdate.setId(user.getId());
            toUpdate.setPaymentStatus(PAYMENT_STATUS_EXPIRED);
            this.baseMapper.updateById(toUpdate);
        }
        if (user.getPaymentStatus() == null || user.getPaymentStatus().isEmpty()) {
            user.setPaymentStatus(PAYMENT_STATUS_UNPAID);
            User toUpdate = new User();
            toUpdate.setId(user.getId());
            toUpdate.setPaymentStatus(PAYMENT_STATUS_UNPAID);
            this.baseMapper.updateById(toUpdate);
        }
    }

    @Override
    public Map<String, Object> completePurchase(String token, String planType, String paymentMethod) {
        User purchaser;
        try {
            purchaser = jwtConfig.parseToken(token, User.class);
        } catch (Exception e) {
            return null;
        }

        if (purchaser == null) {
            return null;
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiry = calculateExpiry(now, planType);

        purchaser.setPaymentStatus(PAYMENT_STATUS_ACTIVE);
        purchaser.setAccessExpiry(expiry);
        User toUpdate = new User();
        toUpdate.setId(purchaser.getId());
        toUpdate.setPaymentStatus(PAYMENT_STATUS_ACTIVE);
        toUpdate.setAccessExpiry(expiry);
        this.baseMapper.updateById(toUpdate);

        Map<String, Object> data = new HashMap<>();
        data.put("paymentStatus", purchaser.getPaymentStatus());
        data.put("accessExpiry", purchaser.getAccessExpiry());
        data.put("planType", planType);
        data.put("paymentMethod", paymentMethod);
        return data;
    }

    private LocalDateTime calculateExpiry(LocalDateTime now, String planType) {
        if ("weekly".equalsIgnoreCase(planType)) {
            return now.plusWeeks(1);
        }
        if ("monthly".equalsIgnoreCase(planType)) {
            return now.plusMonths(1);
        }
        // Default single session: 24 hours of validity
        return now.plusDays(1);
    }


}
