package com.codepath.todoapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.Window;

/**
 * Created by keyulun on 2017/1/31.
 */

public class DelTodoFragment extends DialogFragment {
   public DelTodoFragment() {
      // Empty constructor is required for DialogFragment
      // Make sure not to add arguments to the constructor
      // Use `newInstance` instead as shown below
   }

   public static DelTodoFragment newInstance(Long id, String TaskName) {
      DelTodoFragment frag = new DelTodoFragment();
      Bundle args = new Bundle();
      args.putString("itemTaskName", TaskName);
      args.putLong("itemId", id);
      frag.setArguments(args);
      return frag;
   }


   /*@Override
   public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
      // Defines the xml file for the fragment
      return inflater.inflate(R.layout.fragment_deltodo, parent, false);
   }*/

   @Override
   public Dialog onCreateDialog(Bundle savedInstanceState) {
      Dialog dialog = super.onCreateDialog(savedInstanceState);
      String TaskName = getArguments().getString("itemTaskName");
      final Long id = getArguments().getLong("itemId", 0);
      Log.d("deltodo", "itemId:" + id);
      AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
      dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

      String message = "Are you sure to delete the Todo " + "\"" + TaskName + "\"" + "?";

      alertDialogBuilder.setMessage(message);
      alertDialogBuilder.setPositiveButton("OK",  new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int which) {
            // on success
            TodoList todoList = new TodoList();
            todoList.id = id;
            todoList.delete();
            backToTodoList();
         }

         private void backToTodoList() {
            ListTodoFragment listTodoFragment = new ListTodoFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.flContent, listTodoFragment);
            transaction.addToBackStack(null);
            transaction.commit();
         }
      });
      alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
         }
      });

      return alertDialogBuilder.create();
   }


   @Override
   public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      // Get field from view

   }
}