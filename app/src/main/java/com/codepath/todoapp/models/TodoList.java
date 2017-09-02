package com.codepath.todoapp.models;

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
   long id;

   @Column
   String task;

   @Column
   Date dueDate;

   @Column
   int status;

   @Column
   int priority;

   public void setId(Long id) { this.id = id; }

   public Long getId() { return this.id;}

   public void setTask(String task) {
      this.task = task;
   }

   public String getTask() { return this.task; }

   public void setDueDate(Date dueDate) {
      this.dueDate = dueDate;
   }

   public Date getDueDate() { return this.dueDate; }

   public void setStatus(int status) {
      this.status = status;
   }

   public int getStatus() { return this.status; }

   public void setPrority(int priority) {
      this.priority = priority;
   }

   public int getPriority() { return this.priority; }
}
