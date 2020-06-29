package com.jk.coupon.web;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodMapper {
    @Select("SELECT * FROM goods where status='上线'")
    List<Good> get_online_goods();

    @Select("SELECT * FROM goods where status='下线'")
    List<Good> get_offline_goods();

    @Select("SELECT distinct type FROM goods where status='上线'")
    List<String> get_types();

    @Select("SELECT * FROM USERS WHERE USERNAME = #{name}")
    User get_user(@Param("name") String name);

    @Select("SELECT * FROM goods WHERE type = #{type} and status='上线'")
    List<Good> type_get_good(@Param("type") String type);

    @Select("SELECT * FROM goods WHERE NAME = #{name}")
    Good get_good(@Param("name") String name);

    @Update("UPDATE GOODS SET status='下线' WHERE NAME=#{name}")
    void update_offline(@Param("name") String name);

    @Update("UPDATE GOODS SET status='上线' WHERE NAME=#{name}")
    void update_online(@Param("name") String name);

    @Update("UPDATE GOODS SET number=#{num} WHERE NAME=#{name}")
    void update_inventory(@Param("num") int num, @Param("name") String name);

    @Insert("INSERT INTO GOODS(NAME, TYPE, STATUS, NUMBER, PRICE) VALUES(#{name}, #{type}, #{status}, #{number}, #{price})")
    void insert_good(@Param("name") String name, @Param("type") String type, @Param("status") String status, @Param("number") int number, @Param("price") int price);

    @Update("UPDATE USERS SET good=#{goods} WHERE USERNAME=#{name}")
    void update_cart(@Param("goods") String goods, @Param("name") String name);

}
