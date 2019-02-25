package io.tanjundang.study.knowledge.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * @Author: TanJunDang
 * @Date: 2019/2/25 15:26
 * @Description:
 */
public class DaoManager {

    private static DaoManager instance;
    private static DaoSession daoSession;
    private static Context mContext;

    public static DaoManager getInstance(Context context) {
        mContext = context.getApplicationContext();
        if (instance == null) {
            synchronized (DaoManager.class) {
                if (instance == null) {
                    instance = new DaoManager();
                    dbInit();
                }
            }
        }
        return instance;
    }

    private static void dbInit() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(mContext, "study.db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
