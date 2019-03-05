package io.tanjundang.study.knowledge.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;

/**
 * @Author: TanJunDang
 * @Date: 2019/3/5 11:34
 * @Description: 该类用于声明各种Dao，即操作表的类
 */
//Database注解指定了database的表映射实体数据以及版本等信息
@Database(entities = {User.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract UserDao getUserDao();

    /**
     * 数据库升级时，迁移用的类，需要在数据库创建时设置该对象。
     */
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //数据库的具体变动，我是在之前的user表中添加了新的column，名字是age。
            //类型是integer，不为空，默认值是0
            database.execSQL("ALTER TABLE user "
                    + " ADD COLUMN age INTEGER NOT NULL DEFAULT 0");
        }
    };
}
