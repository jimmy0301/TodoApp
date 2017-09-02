package com.codepath.todoapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.codepath.todoapp.R;
import com.codepath.todoapp.adapers.ShowTodoList;
import com.codepath.todoapp.adapers.TodoListAdapter;
import com.codepath.todoapp.models.TodoList;
import com.codepath.todoapp.models.TodoList_Table;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * Created by keyulun on 2017/1/30.
 */

public class ListTodoFragment extends ListFragment {
   ArrayList<ShowTodoList> todoLists;
   TodoListAdapter todoListAdapter;
   List <TodoList> todoList;
   ListView lvItems;
   boolean hasSorted = false;

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      getActivity().setTitle("List Todo");
      setHasOptionsMenu(true);
   }

   // The onCreateView method is called when Fragment should create its View object hierarchy,
   // either dynamically or via XML layout inflation.

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
      // Defines the xml file for the fragment
      return inflater.inflate(R.layout.fragment_listtodo, parent, false);
   }

   @Override
   public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

      inflater.inflate(R.menu.fragment_listtodo_menu, menu);
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      // handle item selection
      switch (item.getItemId()) {
         case R.id.menu_add:
            goToAddTodo();
            return true;
         case R.id.menu_search:
            searchTodo();
            return true;
         case R.id.menu_sort_by_dueDate:
            hasSorted = true;
            sortTodoList();
            return true;
         default:
            return super.onOptionsItemSelected(item);
      }
   }

   private void searchTodo() {
      final EditText inputSearch = (EditText) getView().findViewById(R.id.inputSearch);
      if (inputSearch.getVisibility() == View.GONE)
         inputSearch.setVisibility(View.VISIBLE);

      inputSearch.requestFocus();
      inputSearch.addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (hasSorted) {
               sortTodoList();
            }
         }

         @Override
         public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.toString().compareTo("") == 0) {
               initList();
            }
            else {
               todoListAdapter.getFilter().filter(charSequence);
            }
         }

         @Override
         public void afterTextChanged(Editable editable) {
            if (hasSorted) {
               sortTodoList();
            }
         }
      });
   }

   private void sortTodoList() {
      todoListAdapter.sort(new Comparator<ShowTodoList>(){
         @Override
         public int compare(ShowTodoList showTodoList, ShowTodoList t1) {
            return t1.dueDate.compareTo(showTodoList.dueDate);
         }
      });

      todoListAdapter.notifyDataSetChanged();
      lvItems.setAdapter(todoListAdapter);
   }

   private void goToAddTodo() {
      AddTodoFragment addTodoFragment = new AddTodoFragment();
      FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
      transaction.replace(R.id.flContent, addTodoFragment);
      transaction.addToBackStack(null);
      transaction.commit();
   }

   @Override
   public void onActivityCreated(Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);
      final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
      initList();
   }

   private void initList() {
      readTodoFromDB();

      lvItems = (ListView) getActivity().findViewById(R.id.lvItems);
      todoListAdapter = new TodoListAdapter(getContext(), todoLists);
      if (hasSorted) {
         sortTodoList();
      }
      lvItems.setAdapter(todoListAdapter);
      setupListViewListener();
   }

   private void readTodoFromDB() {
      int i;
      todoList = new Select().from(TodoList.class).where(TodoList_Table.status.isNot(3)).queryList();
      todoLists = new ArrayList<ShowTodoList>();

      for (i = 0; i < todoList.size(); i++) {
         String priorityInLoop;
         ShowTodoList showTodoList;

         if (todoList.get(i).getPriority() == 0)
            priorityInLoop = "H";
         else if (todoList.get(i).getPriority() == 1) {
            priorityInLoop = "M";
         }
         else {
            priorityInLoop = "L";
         }
         String dueDateShow;

         if (todoList.get(i).getDueDate() == null) {
            dueDateShow = "1987-01-23";
         }
         else {
            dueDateShow = todoList.get(i).getDueDate().toString();
         }

         showTodoList = new ShowTodoList(todoList.get(i).getId(), todoList.get(i).getTask(), dueDateShow, priorityInLoop);
         todoLists.add(showTodoList);
      }
   }

   // This event is triggered soon after onCreateView().
   // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
   @Override
   public void onViewCreated(View view, Bundle savedInstanceState) {
      // Setup any handles to view objects here
      // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
   }

   private void setupListViewListener() {
      /* one click listener */
      lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

         @Override
         public void onItemClick(AdapterView<?> adapterView, View item, int pos, long id) {
            EditTodoFragment editTodoFragment = new EditTodoFragment();
            ShowTodoList todoListItem = todoListAdapter.getItem(pos);

            Bundle args = new Bundle();
            args.putLong("itemId", todoListItem.Id);
            args.putString("itemText", todoListItem.taskName);
            args.putString("itemDueDate", todoListItem.dueDate);
            if (todoListItem.priority.compareTo("H") == 0) {
               args.putInt("itemPriority", 0);
            }
            else if (todoListItem.priority.compareTo("M") == 0) {
               args.putInt("itemPriority", 1);
            }
            else {
               args.putInt("itemPriority", 2);
            }
            editTodoFragment.setArguments(args);

            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.flContent, editTodoFragment);
            transaction.addToBackStack(null);
            transaction.commit();
         }
      });

      /* long click listener */
      lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

         @Override
         public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
            showAlertDiaolog(todoList.get(pos).getId(), todoLists.get(pos).taskName);
            return true;
         }

         private void showAlertDiaolog(Long id, String taskName) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            DelTodoFragment alertDialog = DelTodoFragment.newInstance(id, taskName);
            alertDialog.show(fm, "fragment_alert");
         }
      });
   }
}
