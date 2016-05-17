package com.eenie.mob.greendao;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.ssp.greendao.dao.DaoMaster;
import com.ssp.greendao.dao.DaoSession;

/**
 * Project: GreenDao
 * Desc:
 * Author:Eenie
 * Email:472279981@qq.com
 * Data:2016/5/17
 * Backup:
 */
public class GreenDaoApplication extends Application {
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    public DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "school", null);//创建数据库
            SQLiteDatabase db = helper.getWritableDatabase();
            daoMaster = new DaoMaster(db);
        }
        return daoMaster;
    }

    public DaoSession getDaoSession() {
        if (daoSession == null) {
            daoSession = getDaoMaster().newSession();
        }
        return daoSession;
    }
}
