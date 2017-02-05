package com.codepath.todoapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.codepath.todoapp.R;
import com.codepath.todoapp.models.TodoList;

import java.sql.Date;

/**
 * Created by keyulun on 2017/1/30.
 */

public class AddTodoFragment extends Fragment implements AdapterView.OnItemSelectedListener {
   // The onCreateView method is called when Fragment should create its View object hierarchy,
   // either dynamically or via XML layout inflation.
   String taskName;
   Date dueDate;
   int status;
   int priority;

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      getActivity().setTitle("Add Todo");
      setHasOptionsMenu(true);
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
      // Defines the xml file for the fragment
      return inflater.inflate(R.layout.fragment_edittodo, parent, false);
   }

   @Override
   public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
      inflater.inflate(R.menu.fragment_edittodo_menu, menu);
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      // handle item selection
      switch (item.getItemId()) {
         case R.id.menu_save:
            writeTaskToDB();
            backToTodoList();
            return true;
         case R.id.menu_cancel:
            backToTodoList();
            return true;
         default:
            return super.onOptionsItemSelected(item);
      }
   }

   private void backToTodoList() {
      ListTodoFragment listTodoFragment = new ListTodoFragment();
      FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
      transaction.replace(R.id.flContent, listTodoFragment);
      transaction.addToBackStack(null);
      transaction.commit();
   }

   // This event is triggered soon after onCreateView().
   // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
   @Override
   public void onViewCreated(View view, Bundle savedInstanceState) {
      // Setup any handles to view objects here
      // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
      Spinner spinnerPriority;
      Spinner spinnerStatus;
      int pos;

      spinnerPriority = (Spinner) getView().findViewById(R.id.spinner);
      spinnerPriority.setOnItemSelectedListener(this);

      // Creating adapter for spinner
      ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.priority,
              android.R.layout.simple_spinner_dropdown_item);

      // attaching data adapter to spinner
      spinnerPriority.setAdapter(priorityAdapter);

      spinnerStatus = (Spinner) getView().findViewById(R.id.spinnerStatus);
      spinnerStatus.setOnItemSelectedListener(this);

      ArrayAdapter <CharSequence> statusAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.status,
              android.R.layout.simple_spinner_dropdown_item);

      spinnerStatus.setAdapter(statusAdapter);
   }

   private void writeTaskToDB() {
      EditText editText = (EditText) getView().findViewById(R.id.editTaskName);
      taskName = editText.getText().toString();

      DatePicker datePicker = (DatePicker) getView().findViewById(R.id.datePicker);
      int month = datePicker.getMonth() + 1;
      String dateInfo = datePicker.getYear() + "-" + month + "-" + datePicker.getDayOfMonth();
      dueDate = java.sql.Date.valueOf(dateInfo);

      Log.d("writeToDB", "Year: " + datePicker.getYear());
      Log.d("writeToDB", "Month: " + datePicker.getMonth());
      Log.d("writeToDB", "Day: " + datePicker.getDayOfMonth());
      Log.d("writeToDB", "dateInfo: " + dateInfo);
      Log.d("writeToDB", "taskName: " + taskName);
      Log.d("writeToDB", "dueDate: " + dueDate.toString());
      Log.d("writeToDB", "status: " + status);
      Log.d("writeToDB", "priority: " + priority);
      TodoList todoList = new TodoList();
      todoList.setTask(taskName);
      todoList.setDueDate(dueDate);
      todoList.setStatus(status);
      todoList.setPrority(priority);
      todoList.save();
   }

   @Override
   public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
      String item = adapterView.getSelectedItem().toString();
      switch (adapterView.getId()) {
         case R.id.spinner:
            if (item.compareTo("High") == 0) {
               priority = 0;
            }
            else if (item.compareTo("Medium") == 0) {
               priority = 1;
            }
            else {
               priority = 2;
            }
            break;
         case R.id.spinnerStatus:
            if (item.compareTo("Todo") == 0) {
               status = 0;
            }
            else if (item.compareTo("Done") == 0) {
               status = 1;
            }
            else if (item.compareTo("Expired") == 0) {
               status = 2;
            }
            else {
               status = 3;
            }
            break;
      }
   }

   @Override
   public void onNothingSelected(AdapterView<?> adapterView) {

   }
}
