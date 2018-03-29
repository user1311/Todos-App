package com.example.todos;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.view.View;
import android.widget.ListView;

import com.example.todos.model.Category;

import java.util.List;

/**
 * Created by Adnan on 28.3.2018.
 */

public class CategoryListBinder {
    @BindingAdapter("bind:items")

    public static void bindList(ListView view, ObservableArrayList<Category> list)
    {
        CategoryListAdapter adapter;
        if(list==null)
        {
            adapter=new CategoryListAdapter();
        }
        else{
            adapter=new CategoryListAdapter(list);
        }
        view.setAdapter(adapter);

    }
}
