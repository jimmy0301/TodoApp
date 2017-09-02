package com.codepath.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
   private static final int REQUEST_CODE = 200;
   ArrayList<String> items;
   ArrayAdapter<String> itemsAdapter;
   ListView lvItems;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      lvItems = (ListView) findViewById(R.id.lvItems);
      readItems();
      itemsAdapter = new ArrayAdapter<>(this,
              android.R.layout.simple_list_item_1, items);
      lvItems.setAdapter(itemsAdapter);
      setupListViewListener();
   }

   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
         String itemText = data.getExtras().getString("itemText");
         int pos = data.getExtras().getInt("itemPos");
         items.set(pos, itemText);
         itemsAdapter.notifyDataSetChanged();
         writeItems();
      }
   }

   private void readItems() {
      File filesDir = getFilesDir();
      File todoFile = new File(filesDir, "todo.txt");
      try {
         items = new ArrayList<String>(FileUtils.readLines(todoFile));
      } catch (IOException e) {
         items = new ArrayList<String>();
      }
   }

   private void writeItems() {
      File filesDir = getFilesDir();
      File todoFile = new File(filesDir, "todo.txt");
      try {
         FileUtils.writeLines(todoFile, items);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   private void setupListViewListener() {
      /* one click listener */
      lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

         @Override
         public void onItemClick(AdapterView<?> adapterView, View item, int pos, long id) {
            Intent i = new Intent(MainActivity.this, EditItemActivity.class);
            Log.d("edit", items.get(pos));
            i.putExtra("itemText", items.get(pos));
            i.putExtra("itemPos", pos);
            startActivityForResult(i, REQUEST_CODE);
         }
      });

      /* long click listener */
      lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

         @Override
         public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
            items.remove(pos);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
            return true;
         }
      });
   }

   public void onAddItem(View v) {
      EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
      String itemText = etNewItem.getText().toString();
      itemsAdapter.add(itemText);
      etNewItem.setText("");
      writeItems();
   }
}