package com.example.todos.model;

import android.databinding.ObservableArrayList;

import java.io.Serializable;
import java.util.Observable;

/**
 * Created by Adnan on 28.3.2018.
 */

public class CategoryList implements Serializable {
    public final ObservableArrayList<Category> ItemList;

    public CategoryList(){
        ItemList=new ObservableArrayList<>();

    }
    public CategoryList(ObservableArrayList<Category> iL){

        ItemList=iL;
    }


}
