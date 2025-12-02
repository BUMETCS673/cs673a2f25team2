package com.shanzhu.health.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shanzhu.health.config.Unification;
import com.shanzhu.health.entity.Body;
import com.shanzhu.health.entity.BodyNotes;
import com.shanzhu.health.entity.SportInfo;
import com.shanzhu.health.entity.User;
import com.shanzhu.health.dto.PurchaseRequest;
import com.shanzhu.health.service.IBodyNotesService;
import com.shanzhu.health.service.IBodyService;
import com.shanzhu.health.service.IUserService;
import com.shanzhu.health.utils.DataEncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IBodyService bodyService;

    @Autowired
    private IBodyNotesService bodyNotesService;


    @GetMapping("/all")
    public Unification<List<User>> getAllUser() {
        List<User> list = userService.list();
        return Unification.success(list, "Query successful");
    }


    @PostMapping("/login")
    public Unification<Map<String, Object>> login(@RequestBody User user) {
        Map<String, Object> data = userService.login(user);
        if (data != null) {
            return Unification.success(data);
        }
        return Unification.fail(20002, "Username or password incorrect");
    }


    @PostMapping("/Wxlogin")
    public Unification<Map<String, Object>> Wxlogin(@RequestBody User user) {
        Map<String, Object> data = userService.login(user);
        if (data != null) {
            return Unification.success(data);
        }
        return Unification.fail();
    }


    @PostMapping("/register")
    public Unification<Map<String, Object>> register(@RequestBody User register) {
        Map<String, Object> data = userService.register(register);
        if (data.get("success") != null) {
            return Unification.success("Registration successful");
        } else {
            return Unification.fail(20004, "Registration failed, username already exists");
        }
    }


    @GetMapping("/info")
    public Unification<Map<String, Object>> getUserInfo(@RequestParam("token") String token) {
        // Get user information based on token
        Map<String, Object> data = userService.getUserInfo(token); // Call userService's getUserInfo method, pass token parameter, return a Map<String,Object> type data
        if (data != null) {
            return Unification.success(data); // If data is not null, return success response with data as response data
        }
        return Unification.fail(20003, "Login information incorrect, please login again"); // If data is null, return failure response with error code and error message
    }


    @PostMapping("/logout")
    public Unification<?> logout(@RequestHeader("X-Token") String token) {
        userService.logout(token);// Logout current user's login status from the system
        return Unification.success();
    }

    @PostMapping("/purchase")
    public Unification<Map<String, Object>> completePurchase(@RequestHeader("X-Token") String token,
                                                             @RequestBody PurchaseRequest purchaseRequest) {
        Map<String, Object> data = userService.completePurchase(token, purchaseRequest.getPlanType(), purchaseRequest.getPaymentMethod());
        if (data != null) {
            return Unification.success(data, "Payment successful");
        }
        return Unification.fail(20005, "Payment failed or user not authenticated");
    }


    /**
     * Get user list based on query conditions, paginated query
     *
     * @param username Query condition: username, optional
     * @param phone    Query condition: phone number, optional
     * @param pageNo   Current page number
     * @param pageSize Page size
     * @return Returns Unification wrapped user list, containing total count and user information list for current page
     */
    @GetMapping("/list")
    public Unification<Map<String, Object>> getUserList(@RequestParam(value = "username", required = false) String username,
                                                        @RequestParam(value = "phone", required = false) String phone,
                                                        @RequestParam("pageNo") Long pageNo,
                                                        @RequestParam("pageSize") Long pageSize) {

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(StringUtils.hasLength(username), User::getUsername, username);
        // If phone is used as query condition, need to encrypt before querying (because database stores encrypted values)
        if (StringUtils.hasLength(phone)) {
            String encryptedPhone = DataEncryptionUtil.encrypt(phone);
            wrapper.eq(User::getPhone, encryptedPhone);
        }
        Page<User> page = new Page<>(pageNo, pageSize);

        userService.page(page, wrapper);
        
        // Decrypt sensitive information in returned results
        if (page.getRecords() != null) {
            for (User user : page.getRecords()) {
                if (user.getEmail() != null && !user.getEmail().isEmpty()) {
                    user.setEmail(DataEncryptionUtil.decrypt(user.getEmail()));
                }
                if (user.getPhone() != null && !user.getPhone().isEmpty()) {
                    user.setPhone(DataEncryptionUtil.decrypt(user.getPhone()));
                }
            }
        }
        
        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal()); // Total number of users
        data.put("rows", page.getRecords()); // User list
        return Unification.success(data);
    }


    @PostMapping("/add")
    public Unification<?> addUser(@RequestBody User user) {
        boolean result = userService.addUser(user);
        if (result) {
            return Unification.success("Add successful");
        } else {
            return Unification.fail("Username already exists");
        }
    }


    @PutMapping("/update")
    public Unification<?> updateUser(@RequestBody User user) {
        user.setPassword(null); // Prevent password from being modified, set password to null
        userService.updateUser(user);
        return Unification.success("Update successful");
    }


    @GetMapping("/{id}")
    public Unification<User> getUserById(@PathVariable("id") Integer id) {
        // Get user information by calling userService's getUserById method with user id
        User user = userService.getUserById(id);
        // Wrap the obtained user information into Unification type and return
        return Unification.success(user);
    }


    @GetMapping("/getBodyNotes/{id}")
    public Unification<List<BodyNotes>> getBodyNotes(@PathVariable("id") Integer id) {
        List<BodyNotes> bodyNotesList = bodyNotesService.getBodyNotes(id);
        if (bodyNotesList == null || bodyNotesList.isEmpty()) { // Check if list is empty
            return Unification.fail("No additional records found");
        }
        return Unification.success(bodyNotesList);
    }


    @GetMapping("/WxgetBodyNotes/{token}")
    public Unification<Map<String, Object>> WxgetBodyNotes(@PathVariable("token") String token) {
        // Get user information based on token
        Map<String, Object> data = userService.WxgetUserId(token);
        Integer userId = Integer.parseInt(data.get("id").toString());
        List<BodyNotes> bodyNotes = bodyNotesService.getBodyNotes(userId);
        data.put("bodyNotes", bodyNotes);
        System.out.println(data);
        if (data != null) {
            return Unification.success(data);
        }
        return Unification.fail();
    }


    @DeleteMapping("/{id}")
    public Unification<User> deleteUserById(@PathVariable("id") Integer id) {
        userService.deletUserById(id);
        return Unification.success("Delete successful");
    }


    @PostMapping("/BodyInformation")
    public Unification<?> BodyInfomationUp(@RequestBody Body body) {
        boolean result = bodyService.insert(body);
        if (result == true) {
            return Unification.success("Upload successful");
        } else {
            return Unification.success("Update successful");
        }
    }


    @PostMapping("/BodyInformationNotes")
    public Unification<?> BodyInformationNotes(@RequestBody BodyNotes bodyNotes) {
        bodyNotesService.insert(bodyNotes);
        return Unification.success();
    }


    @GetMapping("/getUserId")
    public Unification<Map<String, Object>> getUserId() {
        Map<String, Object> data = userService.getUserId();
        System.out.println("id" + data);
        if (data != null) {
            return Unification.success(data);
        } else {
            return Unification.fail("Failed to get user ID");
        }
    }


    @GetMapping("/getBodyInfo")
    public Unification<Map<String, Object>> getBodyInfo() {
        Map<String, Object> data = userService.getBodyInfo();
        if (data != null) {
            return Unification.success(data);
        } else {
            return Unification.fail(20002);
        }
    }


    @GetMapping("/getBodyList")
    public Unification<Map<String, Object>> getBodyList(@RequestParam(value = "name", required = false) String name,
                                                        @RequestParam(value = "id", required = false) String id,
                                                        @RequestParam("pageNo") Long pageNo,
                                                        @RequestParam("pageSize") Long pageSize) {

        LambdaQueryWrapper<Body> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(name), Body::getName, name);
        wrapper.eq(StringUtils.hasLength(id), Body::getId, id);
        Page<Body> page = new Page<>(pageNo, pageSize); // Build pagination object, specify page number and page size

        bodyService.page(page, wrapper); // Call userService's pagination query method to query user list with specified page number, page size and query conditions
        Map<String, Object> data = new HashMap<>();

        data.put("total", page.getTotal()); // Put the queried total number of users into response data
        data.put("rows", page.getRecords()); // Put the queried user list into response data
        return Unification.success(data);
    }


    @GetMapping("/getBodyById/{id}")
    public Unification<Body> getBodyById(@PathVariable("id") Integer id) {
        // Get user information by calling userService's getUserById method with user id
        Body body = bodyService.getBodyById(id);
        // Wrap the obtained user information into Unification type and return
        return Unification.success(body);
    }


    @RequestMapping("/updateBody")
    public Unification<?> updateBody(@RequestBody Body body) {
        bodyService.updateBody(body);
        return Unification.success("Update successful");
    }


    @DeleteMapping("/deleteBodyById/{id}")
    public Unification<SportInfo> deleteBodyById(@PathVariable("id") Integer id) {
        bodyService.deletBodyById(id);
        bodyNotesService.delete(id);
        return Unification.success("Delete successful");
    }


    @PutMapping("/changePassword")
    public Unification<?> changePassword(@RequestBody User user) {
        if (userService.updateuser(user)) {
            return Unification.success("Update successful, you are now logged in, please use your new password for next login");
        }
        return Unification.fail("Update failed, username or password incorrect");
    }


    @GetMapping("/getUserBodyList")
    public Unification<Map<String, Object>> getUserBodyList(
            @RequestParam("pageNo") Long pageNo,
            @RequestParam("pageSize") Long pageSize) {

        LambdaQueryWrapper<BodyNotes> wrapper = new LambdaQueryWrapper<>();
        Map<String, Object> userid = userService.getUserId();

        if (userid.get("id") != null) {
            wrapper.eq(BodyNotes::getId, userid.get("id"));
        } else {
            // If userid.get("id") is null, return an empty query condition
            wrapper.isNull(BodyNotes::getId);
        }

        Page<BodyNotes> page = new Page<>(pageNo, pageSize); // Build pagination object, specify page number and page size
        bodyNotesService.page(page, wrapper); // Call userService's pagination query method to query user list with specified page number, page size and query conditions

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal()); // Put the queried total number of users into response data
        data.put("rows", page.getRecords()); // Put the queried user list into response data
        return Unification.success(data);
    }


    @GetMapping("/getUserBodyById/{notesid}")
    public Unification<BodyNotes> getUserBodyById(@PathVariable("notesid") Integer notesid) {
        System.out.println(notesid);
        BodyNotes bodyNotes = bodyNotesService.getUserBodyById(notesid);
        return Unification.success(bodyNotes);
    }

    @RequestMapping("/updateUserBody")
    public Unification<?> updateUserBody(@RequestBody BodyNotes bodyNotes) {
        bodyNotesService.updateUserBody(bodyNotes);
        return Unification.success("Update successful");
    }


    @DeleteMapping("/deleteUserBodyById/{notesid}")
    public Unification<SportInfo> deleteUserBodyById(@PathVariable("notesid") Integer notesid) {
        bodyNotesService.deleteUserBodyById(notesid);
        return Unification.success("Delete successful");
    }


}
