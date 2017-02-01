package com.codepath.todoapp;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;


import java.sql.Date;

/**
 * Created by keyulun on 2017/1/8.
 */
@Table(database = MyDatabase.class)
public class TodoList extends BaseModel{
   @Column
   @PrimaryKey(autoincrement = true)
   Long id;

   @Column
   String task;

   @Column
   Date dueDate;

   @Column
   int status;

   @Column
   int priority;

   public void setId(Long id) { this.id = id; }

   public void setTask(String task) {
      this.task = task;
   }

   public void setDueDate(Date dueDate) {
      this.dueDate = dueDate;
   }

   public void setStatus(int status) {
      this.status = status;
   }

   public void setPrority(int priority) {
      this.priority = priority;
   }
}
