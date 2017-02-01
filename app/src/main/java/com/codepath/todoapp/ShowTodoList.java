package com.codepath.todoapp;

/**
 * Created by keyulun on 2017/1/31.
 */

public class ShowTodoList {
   public Long Id;
   public String taskName;
   public String dueDate;
   public String priority;

   public ShowTodoList(Long Id, String taskName, String dueDate, String priority) {
      this.Id = Id;
      this.taskName = taskName;
      this.dueDate = dueDate;
      this.priority = priority;
   }

   public String toString() {
      return this.taskName;
   }
}
