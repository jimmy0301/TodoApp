package com.codepath.todoapp.models;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by keyulun on 2017/1/8.
 */

public class SQLiteApplication extends Application {
   @Override
   public void onCreate() {
      super.onCreate();

      FlowManager.init(new FlowConfig.Builder(this).build());
   }
}
