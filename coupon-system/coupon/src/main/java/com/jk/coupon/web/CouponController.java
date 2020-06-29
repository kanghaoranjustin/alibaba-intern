package com.jk.coupon.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@RestController
@RequestMapping(value="/coupons")
public class CouponController {
    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value="/", method= RequestMethod.GET)
    public List<Coupon> getCouponList() {
        List<Coupon> c = userMapper.get_coupons();
        return c;
    }

    @RequestMapping(value="/", method= RequestMethod.POST)
    public String postCoupon( @ModelAttribute Coupon coupon) {
        userMapper.insert_coupon(coupon.getName(), coupon.getNum(), coupon.getType(), coupon.getInstruction(), coupon.getDetail(),
                coupon.getStart_date(), coupon.getEnd_date());
        return "success";
    }


    @RequestMapping(value="/{name}", method=RequestMethod.GET)
    public Coupon getUser(@PathVariable String name) {
        Coupon coupon = userMapper.get_coupon(name);
        return coupon;
    }

    @RequestMapping(value="/get/{username}/{coupon_name}", method= RequestMethod.PUT)
    public String getCoupon(@PathVariable String username, @PathVariable String coupon_name){
        Coupon coupon = userMapper.get_coupon(coupon_name);
        userMapper.update_coupon(coupon.getNum() - 1, coupon_name);
        User user = userMapper.get_user(username);
        String wallet;
        if(user.getCoupon() == null)
            wallet = coupon_name;
        else
            wallet = user.getCoupon() + "," + coupon_name;


        userMapper.update_wallet(wallet, username);
        return "success";
    }


}
