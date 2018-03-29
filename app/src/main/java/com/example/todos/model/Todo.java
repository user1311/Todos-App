package com.example.todos.model;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import java.io.Serializable;
import java.util.Observable;

/**
 * Created by Adnan on 28.3.2018.
 */

public class Todo implements Serializable {
    public  ObservableInt id=new ObservableInt();
    public  ObservableField<String> text = new ObservableField<String>();
    public  ObservableField<String> created = new ObservableField<String>();
    public  ObservableField<String> expired = new ObservableField<String>();
    public  ObservableField<String> category = new ObservableField<String>();
    public  ObservableBoolean done=new ObservableBoolean();

    public Todo(){}

    public Todo(int id,String text,String created,String expired,String category,Boolean done){
        this.id.set(id);
        this.text.set(text);
        this.expired.set(expired);
        this.created.set(created);
        this.category.set(category);
        this.done.set(done);


    }



}
