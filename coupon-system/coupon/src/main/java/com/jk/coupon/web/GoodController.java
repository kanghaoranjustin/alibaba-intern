package com.jk.coupon.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@RestController
@RequestMapping(value="/goods")
public class GoodController {
    @Autowired
    private GoodMapper goodMapper;

    @RequestMapping(value="/", method= RequestMethod.GET)
    public List<Good> getOnGoodList() {
        return goodMapper.get_online_goods();
    }

    @RequestMapping(value="/offline", method= RequestMethod.GET)
    public List<Good> getOffGoodList() {
        return goodMapper.get_offline_goods();
    }

    @RequestMapping(value="/types", method= RequestMethod.GET)
    public List<String> getTypes() {
        return goodMapper.get_types();
    }

    @RequestMapping(value="/", method= RequestMethod.POST)
    public String postGood( @ModelAttribute Good good) {
        goodMapper.insert_good(good.getName(), good.getType(), good.getStatus(), good.getNumber(), good.getPrice());
        return "success";
    }

    @RequestMapping(value="/byType/{type}", method= RequestMethod.GET)
    public List<Good> getTypeList(@PathVariable String type) {
        return goodMapper.type_get_good(type);
    }

    @RequestMapping(value="/byName/{name}", method=RequestMethod.GET)
    public Good getGood(@PathVariable String name) {
         return goodMapper.get_good(name);
    }

    @RequestMapping(value="/update_inventory/{number}/{name}", method= RequestMethod.PUT)
    public String update_inventory(@PathVariable int number, @PathVariable String name) {
        goodMapper.update_inventory(number, name);
        return "success";
    }

    @RequestMapping(value="/update_status/{new_status}/{name}", method= RequestMethod.PUT)
    public String update_status(@PathVariable String new_status, @PathVariable String name) {
        if(new_status.equals("on"))
            goodMapper.update_online(name);
        else if(new_status.equals("off"))
            goodMapper.update_offline(name);
        return "success";
    }

    @RequestMapping(value="/toCart/{username}/{good}", method= RequestMethod.PUT)
    public String toCart(@PathVariable String username, @PathVariable String good){
        Good item = goodMapper.get_good(good);
        goodMapper.update_inventory(item.getNumber() - 1, good);
        User user = goodMapper.get_user(username);
        String cart;
        if(user.getGood() == null || user.getGood().equals(""))
            cart = good;
        else
            cart = user.getGood() + "," + good;

        goodMapper.update_cart(cart, username);
        return "success";
    }


}
