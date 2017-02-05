package com.codepath.todoapp.models;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by keyulun on 2017/1/8.
 */

@Database(name=MyDatabase.NAME, version=MyDatabase.VERSION)
public class MyDatabase {
   public static final String NAME = "MyDatabase";
   public static final int VERSION = 1;
}
