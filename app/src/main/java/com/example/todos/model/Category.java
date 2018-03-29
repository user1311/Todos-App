package com.example.todos.model;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import java.io.Serializable;

/**
 * Created by Adnan on 28.3.2018.
 */

public class Category implements Serializable{
    public final ObservableInt catId=new ObservableInt();
    public final ObservableField<String> description=new ObservableField<String>();

    public Category(){
    }

    public Category(int id,String desc)
    {
        catId.set(id);
        description.set(desc);
    }

}
