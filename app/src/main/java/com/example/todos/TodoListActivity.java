package com.example.todos;

import android.app.LoaderManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.todos.data.DatabaseHelper;
import com.example.todos.data.TodosContract;
import com.example.todos.data.TodosContract.CategoriesEntry;
import com.example.todos.data.TodosContract.TodosEntry;
import com.example.todos.data.TodosQueryHandler;
import com.example.todos.model.Category;
import com.example.todos.model.CategoryList;
import com.example.todos.model.Todo;

public class TodoListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    static final int ALL_RECORDS=-1;
    static final int ALL_CATEGORIES=-1;
    private static final int URL_LOADER = 0;

    Cursor cursor;
    TodosCursorAdapter adapter;
    CategoryList list=new CategoryList();
    Spinner spinner;
    CategoryListAdapter categoryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner=(Spinner) findViewById(R.id.spinCategories);
        getLoaderManager().initLoader(URL_LOADER,null,this );
        setCategories();

        final ListView lv = (ListView) findViewById(R.id.lvTodos);
        adapter = new TodosCursorAdapter(this, cursor, false);
        lv.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        DatabaseHelper helper=new DatabaseHelper(this);
//        SQLiteDatabase db = helper.getReadableDatabase();
//        CreateTodo();

//        UpdateData();
//        DeleteData();
       // ReadData();

       // CreateCategory();
        //ReadCategories();

//adds the custom layout
//        lv.setAdapter(new ArrayAdapter<String>(this, R.layout.todo_list_item,
//                R.id.tvNote, itemname));
////adds the click event to the listView, reading the content
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {

                cursor=(Cursor) adapterView.getItemAtPosition(pos);

                int todoId=cursor.getInt(cursor.getColumnIndex(TodosEntry._ID));
                String todoText=cursor.getString(cursor.getColumnIndex(TodosEntry.COLUMN_TEXT));
                String todoExpireDate=cursor.getString(cursor.getColumnIndex(TodosEntry.COLUMN_EXPIRED));
                String todoCreated=cursor.getString(cursor.getColumnIndex(TodosEntry.COLUMN_CREATED));
                int todoDone=cursor.getInt(cursor.getColumnIndex(TodosEntry.COLUMN_DONE));
                String todoCategory=cursor.getString(cursor.getColumnIndex(TodosEntry.COLUMN_CATEGORY));

                boolean boolDone=(todoDone==1);
                Todo todo = new Todo(todoId,todoText,todoCreated,todoExpireDate,todoCategory,boolDone);

                Intent intent = new Intent(TodoListActivity.this, TodoActivity.class);
                intent.putExtra("todo",todo);
                intent.putExtra("categories", list);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Todo todo=new Todo(0,"","","","0",false);
                Intent intent=new Intent(TodoListActivity.this,TodoActivity.class);
                intent.putExtra("todo",todo);
                intent.putExtra("categories",list);
                startActivity(intent);

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                if (position >= 0) {
                    getLoaderManager().restartLoader(URL_LOADER, null, TodoListActivity.this);
                    //getLoaderManager().initLoader(URL_LOADER, null, TodoListActivity.this);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

    }


    private void CreateCategory()
    {
        ContentValues values=new ContentValues();
        values.put(TodosContract.CategoriesEntry.COLUMN_DESCRIPTION,"Work");
        Uri uri=getContentResolver().insert(TodosContract.CategoriesEntry.CONTENT_URI,values);
        Log.d("MainActivity","Inserted category "+uri);

    }

    private void CreateTestTodos(){
       for (int i=0;i<20;i++)
        {
            ContentValues values=new ContentValues();
            values.put(TodosEntry.COLUMN_TEXT, "Todo Item #"+String.valueOf(i));
            values.put(TodosEntry.COLUMN_CATEGORY,1);
            values.put(TodosEntry.COLUMN_CREATED,"2017-11-23");
            values.put(TodosEntry.COLUMN_EXPIRED,"2017-12-1");
            int done=(i%2==1)?1:0; // ovo samo testni podaci, svi parnu su zavrseni, neparni nisu
            values.put(TodosEntry.COLUMN_DONE,done);
            TodosQueryHandler todosQueryHandler=new TodosQueryHandler(this.getContentResolver());
            todosQueryHandler.startInsert(1,null,TodosEntry.CONTENT_URI,values);

        }

    }

//    private void ReadData()
//    {
//        String[] projection ={
//                TodosEntry.COLUMN_TEXT,
//                TodosEntry.TABLE_NAME+"."+TodosEntry._ID,
//            TodosEntry.COLUMN_CREATED,
//            TodosEntry.COLUMN_EXPIRED,
//            TodosEntry.COLUMN_DONE,
//                CategoriesEntry.TABLE_NAME+"."+CategoriesEntry._ID,
//            CategoriesEntry.COLUMN_DESCRIPTION
//        };
//
//        Cursor cursor=getContentResolver().query(TodosEntry.CONTENT_URI,projection,null,null,null);
//
//    }

//    public void ReadCategories()
//    {
//        SQLiteOpenHelper helper=new DatabaseHelper(this);
//        SQLiteDatabase db=helper.getReadableDatabase();
//
//        String[] projection = {
//                String.valueOf(CategoriesEntry._ID),
//            CategoriesEntry.COLUMN_DESCRIPTION
//        };
//
//        String selection = CategoriesEntry._ID+"=?";
//        String[] selectionArgs ={"0"};
//
//        Cursor cursor=db.query("categories",projection,selection,selectionArgs,null,null,null);
//
//        int i=cursor.getCount();
//        Log.e("Columns_count", String.valueOf(i));
//        String rowContent = "";
//
////        while(cursor.moveToNext()){
////            for (i=0;i<=4;i++)
////            {
////                rowContent+=cursor.getString(i)+" - ";
////            }
////            Log.e("Row "+String.valueOf(cursor.getPosition()),rowContent);
////            rowContent="";
////        }
//        cursor.close();
//
//    }

    private void UpdateData(){
        SQLiteOpenHelper helper=new DatabaseHelper(this);
        SQLiteDatabase db=helper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(TodosEntry.COLUMN_DONE,"1");
        values.put(TodosEntry.COLUMN_TEXT,"Jarane sta ces jest danas?");
        values.put(TodosEntry.COLUMN_CREATED,"2018-02-28");

        String whereClause=TodosEntry._ID+"=?";
        String[] args={"1"};
        int numRows= db.update(TodosEntry.TABLE_NAME,values,whereClause,args);

        Log.e("Update","Number of updated rows: "+String.valueOf(numRows));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_categories:
                //get the categories cursor for the
                Intent intent = new Intent(TodoListActivity.this, CategoryActivity.class);
                startActivity(intent);
                break;
            case R.id.action_delete_all_todos:
                DeleteTodo(ALL_RECORDS);
                break;
            case R.id.action_create_test_data:
                CreateTestTodos();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setCategories()
    {
        //get cursor with all the activities
        final TodosQueryHandler categoriesHandler = new TodosQueryHandler(
                this.getContentResolver()) {
            @Override
            protected void onQueryComplete(int token, Object cookie,
                                           Cursor cursor) {
                try {
                    if ((cursor != null)) {
                        int i = 0;
                        list.ItemList.add(i, new Category(ALL_CATEGORIES, "All Categories"));
                        i++;
                        while (cursor.moveToNext()) {
                            list.ItemList.add(i, new Category(
                                    cursor.getInt(0),
                                    cursor.getString(1)
                            ));
                            i++;
                        }
                    }
                } finally {
                    //cm = null;
                }
            }
        };
        categoriesHandler.startQuery(1, null, TodosContract.CategoriesEntry.CONTENT_URI, null, null, null,
                TodosContract.CategoriesEntry.COLUMN_DESCRIPTION);
    }


    private void DeleteTodo(int id){


        String[] args={String.valueOf(id)};

        if (id==ALL_RECORDS)
        {
            args=null;
        }

            TodosQueryHandler helper = new TodosQueryHandler(this.getContentResolver());
            helper.startDelete(2,null,TodosEntry.CONTENT_URI,TodosEntry._ID+"=?",args);

    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection ={
                TodosEntry.COLUMN_TEXT,
                TodosEntry.TABLE_NAME+"."+TodosEntry._ID,
                TodosEntry.COLUMN_CREATED,
                TodosEntry.COLUMN_EXPIRED,
                TodosEntry.COLUMN_DONE,
                TodosEntry.COLUMN_CATEGORY,
                CategoriesEntry.TABLE_NAME+"."+CategoriesEntry._ID,
                CategoriesEntry.COLUMN_DESCRIPTION
        };
        String selection;
        String[] arguments = new String[1];

        if (spinner.getSelectedItemId()< 0) {
            selection = null;
            arguments = null;
        }
        else {
            selection = TodosEntry.COLUMN_CATEGORY + "=?";
            arguments[0] = String.valueOf(spinner.getSelectedItemId());
        }
        return new CursorLoader(
                this,
                TodosEntry.CONTENT_URI,
                projection,
                selection, arguments, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        adapter.swapCursor(data);
        if (categoryAdapter == null){
            categoryAdapter = new CategoryListAdapter(list.ItemList);
            spinner.setAdapter(categoryAdapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
