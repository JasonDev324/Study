package io.tanjundang.study.knowledge.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: TanJunDang
 * @Date: 2019/3/5 11:12
 * @Description: 用于操作数据库增删改查
 * 使用：
 * 1. 用Dao注解该接口，声明为数据库操作接口
 * 2.使用Query、Insert、Update、Delete等注解设置SQL语句
 * 3.返回的List不能用ArrayList，只能用List不然会报错。
 */
//1.
@Dao
public interface UserDao {

    //-------------------Query-------------------
    //   2.
    @Query("select * from user")
    List<User> getUserList();

    //    3.方法参数和注解的sql语句参数一一对应,用“：”标识传入的参数（一个是表的字段，一个是参数）
    @Query("select * from user where uid = :uid")
    User getUserById(int uid);


    //-------------------Insert-------------------
    //    4.long表示返回的主键值
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(List<User> users);


    //-------------------update-------------------
    //5.返回类型int代表更新的条目数目，而非主键uid的值
    //更新已有数据，根据主键（uid）匹配，而非整个user对象
    @Update()
    int update(User user);

    @Update()
    int updateAll(List<User> user);


    //-------------------delete-------------------
//删除user数据，数据的匹配通过主键uid实现。
//返回int数据表示删除了多少条。非主键uid值。
    @Delete
    int delete(User user);

    @Delete
    int delete(List<User> users);

    @Query("DELETE FROM user ")
    int clear();
}
