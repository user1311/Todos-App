package com.example.todos.data;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;

/**
 * Created by Adnan on 28.3.2018.
 */

public class TodosQueryHandler extends AsyncQueryHandler {
    public TodosQueryHandler(ContentResolver cr) {
        super(cr);
    }
}
