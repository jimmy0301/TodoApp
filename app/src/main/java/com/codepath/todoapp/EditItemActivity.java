package com.codepath.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
   EditText etText;
   int pos = 0;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_edit_item);
      String itemText = getIntent().getStringExtra("itemText");
      pos = getIntent().getIntExtra("itemPos", 0);
      Log.d("edit", ""+pos);
      etText = (EditText) findViewById(R.id.editText);
      etText.setText(itemText);
      etText.setSelection(itemText.length());
   }

   public void saveEditRes(View v) {
      String itemText = etText.getText().toString();
      Intent data = new Intent();
      data.putExtra("itemText", itemText);
      data.putExtra("itemPos", pos);

      setResult(RESULT_OK, data);
      finish();
   }
}
