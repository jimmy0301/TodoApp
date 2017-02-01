package com.codepath.todoapp;

import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
   private Toolbar toolbar;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      toolbar = (Toolbar) findViewById(R.id.toolbar);
      toolbar.setTitleTextColor(Color.WHITE);
      toolbar.setFitsSystemWindows(true);
      setSupportActionBar(toolbar);

      ListTodoFragment listTodoFragment = new ListTodoFragment();
      FragmentManager fragmentManager = getSupportFragmentManager();
      fragmentManager.beginTransaction().replace(R.id.flContent, listTodoFragment).commit();
   }
}
