package com.example.youyaoqilite.greendao

import android.content.Context
import com.example.youyaoqilite.data.Cartoon
import com.example.youyaoqilite.data.CartoonDao

class CartoonDaoOpe private constructor() {

    private object mHolder {
        val instance = CartoonDaoOpe()
    }

    companion object {
        fun getInstance(): CartoonDaoOpe {
            return mHolder.instance
        }
    }

    /**
     * 添加数据至数据库

     * @param context
     * *
     * @param cartoon
     */
    fun insertCollectedData(context: Context?, cartoon: Cartoon) {
        cartoon.collected = true
        when {
            queryHistoryForName(context,cartoon.name)?.size != 0 -> {
                cartoon.history = queryHistoryForName(context,cartoon.name)?.get(0)?.history!!
                cartoon.chapterid = queryHistoryForName(context,cartoon.name)?.get(0)?.chapterid!!
                updateData(context,cartoon)
            }
            queryCollectedForName(context,cartoon.name)?.size != 0 -> updateData(context,cartoon)
            else -> DbManager.getInstance(context!!)?.getDaoSession(context)?.cartoonDao?.insert(cartoon)
        }
    }

    /**
     * 添加数据至数据库

     * @param context
     * *
     * @param cartoon
     */
    fun insertHistoryData(context: Context?, cartoon: Cartoon) {
        cartoon.history = true
        when {
            queryCollectedForName(context,cartoon.name)?.size != 0 -> {
                cartoon.collected = queryCollectedForName(context,cartoon.name)?.get(0)?.collected!!
                cartoon.chapterid = queryCollectedForName(context,cartoon.name)?.get(0)?.chapterid!!
                updateData(context,cartoon)
            }
            queryHistoryForName(context,cartoon.name)?.size != 0 -> updateData(context,cartoon)
            else -> DbManager.getInstance(context!!)?.getDaoSession(context)?.cartoonDao?.insert(cartoon)
        }
    }


    /**
     * 将数据实体通过事务添加至数据库

     * @param context
     * *
     * @param list
     */
    fun insertData(context: Context?, list: List<Cartoon>?) {
        if (null == list || list.isEmpty()) {
            return
        }
        DbManager.getInstance(context!!)?.getDaoSession(context)?.cartoonDao?.insertInTx(list)
    }

    /**
     * 添加数据至数据库，如果存在，将原来的数据覆盖
     * 内部代码判断了如果存在就update(entity);不存在就insert(entity)；

     * @param context
     * *
     * @param cartoon
     */
    fun saveData(context: Context?, cartoon: Cartoon) {
        DbManager.getInstance(context!!)?.getDaoSession(context)?.cartoonDao?.save(cartoon)
    }

    /**
     * 删除数据至数据库

     * @param context
     * *
     * @param cartoon 删除具体内容
     */
    fun deleteCollectedData(context: Context?, cartoon: Cartoon) {
        cartoon.collected = false
        if (queryHistoryForName(context,cartoon.name)?.size != 0){
            cartoon.history = queryHistoryForName(context,cartoon.name)?.get(0)?.history!!
            cartoon.chapterid = queryHistoryForName(context,cartoon.name)?.get(0)?.chapterid!!
        }
        updateData(context,cartoon)
    }

    /**
     * 删除数据至数据库

     * @param context
     * *
     * @param cartoon 删除具体内容
     */
    fun deleteHistoryData(context: Context?, cartoon: Cartoon) {
        cartoon.history = false
        updateData(context,cartoon)
    }

    /**
     * 根据id删除数据至数据库

     * @param context
     * *
     * @param id      删除具体内容
     */
    fun deleteByKeyData(context: Context?, name: String) {
        DbManager.getInstance(context!!)?.getDaoSession(context)?.cartoonDao?.deleteByKey(name)
    }

    /**
     * 删除全部数据

     * @param context
     */
    fun deleteAllData(context: Context?) {
        DbManager.getInstance(context!!)?.getDaoSession(context)?.cartoonDao?.deleteAll()
    }

    /**
     * 更新数据库

     * @param context
     * *
     * @param cartoon
     */
    fun updateData(context: Context?, cartoon: Cartoon) {
        DbManager.getInstance(context!!)?.getDaoSession(context)?.cartoonDao?.update(cartoon)
        if (!cartoon.history && !cartoon.collected)
            DbManager.getInstance(context!!)?.getDaoSession(context)?.cartoonDao?.delete(cartoon)
    }


    /**
     * 查询收藏所有数据

     * @param context
     * *
     * @return
     */
    fun queryCollectionAll(context: Context?): MutableList<Cartoon>? {
        val builder = DbManager.getInstance(context!!)?.getDaoSession(context)?.cartoonDao?.queryBuilder()
        return builder?.where(CartoonDao.Properties.Collected.eq(true))?.list()
    }

    /**
     * 查询历史纪录所有数据

     * @param context
     * *
     * @return
     */
    fun queryHistoryAll(context: Context?): MutableList<Cartoon>? {
        val builder = DbManager.getInstance(context!!)?.getDaoSession(context)?.cartoonDao?.queryBuilder()
        return builder?.where(CartoonDao.Properties.History.eq(true))?.list()
    }

    fun queryCollectedForName(context: Context?, name:String): MutableList<Cartoon>? {
        val builder = DbManager.getInstance(context!!)?.getDaoSession(context)?.cartoonDao?.queryBuilder()
        /**
         * 返回当前id的数据集合,当然where(这里面可以有多组，做为条件);
         * 这里build.list()；与where(StudentDao.Properties.Id.eq(id)).list()结果是一样的；
         * 在QueryBuilder类中list()方法return build().list();

         */
        // Query<Student> build = builder.where(StudentDao.Properties.Id.eq(id)).build();
        // List<Student> list = build.list();
        return builder?.where(CartoonDao.Properties.Name.eq(name))?.where(CartoonDao.Properties.Collected.eq(true))?.list()
    }

    fun queryHistoryForName(context: Context?, name:String): MutableList<Cartoon>? {
        val builder = DbManager.getInstance(context!!)?.getDaoSession(context)?.cartoonDao?.queryBuilder()
        /**
         * 返回当前id的数据集合,当然where(这里面可以有多组，做为条件);
         * 这里build.list()；与where(StudentDao.Properties.Id.eq(id)).list()结果是一样的；
         * 在QueryBuilder类中list()方法return build().list();

         */
        // Query<Student> build = builder.where(StudentDao.Properties.Id.eq(id)).build();
        // List<Student> list = build.list();
        return builder?.where(CartoonDao.Properties.Name.eq(name))?.where(CartoonDao.Properties.History.eq(true))?.list()
    }
}