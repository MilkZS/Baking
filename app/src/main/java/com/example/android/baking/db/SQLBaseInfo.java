package com.example.android.baking.db;

/**
 * Created by milkdz on 2018/2/23.
 */

public interface SQLBaseInfo {

    String CREATE_TABLE = "CREATE TABLE ";

    String INT_PRIMARY_KEY = " INTEGER PRIMARY KEY AUTOINCREMENT ";

    String TEXT_NO_NULL = " TEXT NOT NULL ";

    String DROP_TABLE = " DROP TABLE IF EXISTS ";

    String SORT_AES = " ASC ";

    String OR = " OR ";
}
