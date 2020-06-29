package com.jk.coupon.web;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM USERS")
    List<User> check_users();

    @Select("SELECT * FROM USERS WHERE USERNAME = #{name}")
    User get_user(@Param("name") String name);

    @Insert("INSERT INTO USERS(USERNAME, PASSWORD) VALUES(#{name}, #{password})")
    void insert_user(@Param("name") String name, @Param("password") String password);

    @Insert("INSERT INTO coupon(name, num, type, instruction, detail, start_date, end_date) VALUES(#{name}, #{num}, #{type}, #{instruction}, #{detail}, #{start}, #{end})")
    void insert_coupon(@Param("name") String name, @Param("num") int num, @Param("type") String type,
                       @Param("instruction") String instruction, @Param("detail") String detail,
                       @Param("start") String start, @Param("end") String end);

    @Select("SELECT * FROM coupon")
    List<Coupon> get_coupons();

    @Update("UPDATE USERS SET BALANCE=#{balance} WHERE USERNAME=#{name}")
    void update_balance(@Param("balance") int balance, @Param("name") String name);

    @Update("UPDATE USERS SET GOOD=#{good} WHERE USERNAME=#{name}")
    void update_cart(@Param("good") String good, @Param("name") String name);

    @Delete("DELETE FROM USERS WHERE USERNAME = #{name}")
    void delete_user(@Param("name") String name);

    @Select("SELECT EXISTS(SELECT * FROM users WHERE username=#{name})")
    boolean checkUserExists(@Param("name") String name);

    @Update("UPDATE COUPON SET NUM=#{number} WHERE NAME=#{name}")
    void update_coupon(@Param("number") int number, @Param("name") String name);

    @Update("UPDATE USERS SET COUPON=#{coupons} WHERE USERNAME=#{name}")
    void update_wallet(@Param("coupons") String coupons, @Param("name") String name);

    @Select("SELECT * FROM COUPON WHERE NAME = #{name}")
    Coupon get_coupon(@Param("name") String name);
}

