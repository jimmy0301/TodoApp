package com.codepath.todoapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Date;


public class MainActivity extends AppCompatActivity {
   private DrawerLayout mDrawer;
   private Toolbar toolbar;
   private NavigationView nvDrawer;
   private ActionBarDrawerToggle drawerToggle;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      toolbar = (Toolbar) findViewById(R.id.toolbar);
      toolbar.setTitleTextColor(Color.WHITE);
      setSupportActionBar(toolbar);

      ListTodoFragment listTodoFragment = new ListTodoFragment();
      FragmentManager fragmentManager = getSupportFragmentManager();
      fragmentManager.beginTransaction().replace(R.id.flContent, listTodoFragment).commit();
   }
}
