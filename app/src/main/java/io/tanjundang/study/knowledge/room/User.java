package io.tanjundang.study.knowledge.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * @Author: TanJunDang
 * @Date: 2019/3/5 11:08
 * @Description:
 */
//1.Room默认把类名作为数据库的表名,用tableName可以设置其他表名
@Entity(tableName = "user")
public class User {

    //2.设置主键，并且定义自增增
    @PrimaryKey(autoGenerate = true)
    //3.字段映射具体的数据表字段名
    @ColumnInfo(name = "uid")
    int uid;

    @ColumnInfo(name = "name")
    String name;


    //4.必须指定一个构造方法，room框架需要。并且只能指定一个
//如果有其他构造方法，则其他的构造方法必须添加@Ignore注解
    public User() {
    }

    //其他构造方法要添加@Ignore注解
    @Ignore
    public User(int uid) {
        this.uid = uid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
