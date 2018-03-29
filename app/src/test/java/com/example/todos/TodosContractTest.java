package com.example.todos;


import com.example.todos.data.TodosContract;

import org.junit.Test;

public class TodosContractTest {

    @Test
    public void TestAuthority(){

        String authority= TodosContract.CONTENT_AUTHORITY;
        org.junit.Assert.assertEquals("Unexpected authority values","com.example.todos.todosprovider",authority);

    }

    @Test
    public void TestConcat()
    {
        TodosContract todo=new TodosContract();

        String content=todo.concatContent("com.example.todos");
        org.junit.Assert.assertEquals("Concat is not doing as it is supposed!","content://com.example.todos",content);


    }


}
