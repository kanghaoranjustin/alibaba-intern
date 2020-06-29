package com.jk.coupon.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value="/users")     // 通过这里配置使下面的映射都在/users下
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value="/", method= RequestMethod.GET)
    public List<User> getUserList() {
        // 处理"/users/"的GET请求，用来获取用户列表
        // 还可以通过@RequestParam从页面中传递参数来进行查询条件或者翻页信息的传递
        List<User> r = userMapper.check_users();
        return r;
    }

    @RequestMapping(value="/", method=RequestMethod.POST)
    public String postUser(@ModelAttribute User user) {
        if (userMapper.checkUserExists(user.getUsername())){
            return "fail";
            }
        else{
            userMapper.insert_user(user.getUsername(), user.getPassword());
            return "success";
        }


    }

    @RequestMapping(value="/{username}", method=RequestMethod.GET)
    public User getUser(@PathVariable String username) {
        // 处理"/users/{id}"的GET请求，用来获取url中id值的User信息
        // url中的id可通过@PathVariable绑定到函数的参数中
        User user = userMapper.get_user(username);
        return user;
    }

    @RequestMapping(value="/balance/{change}/{username}", method=RequestMethod.PUT)
    public String update_balance(@PathVariable int change, @PathVariable String username){
        User user = userMapper.get_user(username);
        int new_balance = user.getBalance() + change;
        userMapper.update_balance(new_balance, username);
        return "success";
    }

    @RequestMapping(value="/{username}", method=RequestMethod.DELETE)
    public String deleteUser(@PathVariable String username) {
        // 处理"/users/{id}"的DELETE请求，用来删除User
        userMapper.delete_user(username);
        return "success";
    }

    @RequestMapping(value="/purchase/{good}/{username}", method=RequestMethod.PUT)
    public String update_cart(@PathVariable String good, @PathVariable String username){
        User user = userMapper.get_user(username);
        String cart = user.getGood();
        String[] cart_goods = cart.split(",");
        String result_cart = "";
        for (String cart_good : cart_goods) {
            if (!cart_good.equals(good))
                result_cart += cart_good + ",";
        }
        String end = "";
        if(!result_cart.equals(""))
            end = result_cart.substring(0, result_cart.length() - 1);
        userMapper.update_cart(end, username);
        return "success";
    }



}
