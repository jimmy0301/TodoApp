package com.codepath.todoapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static com.codepath.todoapp.R.id.tvTaskName;

/**
 * Created by keyulun on 2017/1/31.
 */

public class TodoListAdapter extends ArrayAdapter<ShowTodoList> {
   public TodoListAdapter(Context context, ArrayList<ShowTodoList> todoList) {
      super(context, 0, todoList);
   }

   @Override
   public View getView(int position, View convertView, ViewGroup parent) {
      ShowTodoList todoList = getItem(position);

      if (convertView == null) {
         convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todolist, parent, false);
      }

      TextView tvId = (TextView) convertView.findViewById(R.id.tvId);
      TextView tvTaskName = (TextView) convertView.findViewById(R.id.tvTaskName);
      TextView tvDueDate = (TextView) convertView.findViewById(R.id.tvDueDate);
      TextView tvPriority = (TextView) convertView.findViewById(R.id.tvPriority);

      String dueDate = "Due Date: " + todoList.dueDate;
      tvTaskName.setText(todoList.taskName);
      tvDueDate.setText(dueDate);
      tvPriority.setText(todoList.priority);
      if (todoList.priority.compareTo("H") == 0) {
         tvPriority.setTextColor(Color.RED);
      }
      else if (todoList.priority.compareTo("L") == 0) {
         tvPriority.setTextColor(Color.BLUE);
      }
      else {
         tvPriority.setTextColor(Color.YELLOW);
      }

      return convertView;
   }
}
