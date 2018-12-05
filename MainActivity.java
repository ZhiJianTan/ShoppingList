package com.example.zaphk.shoppinglist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase mDatabase;
    private GroceryAdapter mAdapter;
    private EditText mEditTextName;
    private TextView mTextViewAmount;
    private int mAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //helper will create database
        GroceryDBHelper dbHelper = new GroceryDBHelper(this);
        //gets a writable database file
        mDatabase=dbHelper.getWritableDatabase();

        //defining recyclerview and setting layout manager
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new GroceryAdapter(this,getAllItems());
        recyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            //to remove item when swiped
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //call removeItem function to remove the item with the matching ID tag
                removeItem((long) viewHolder.itemView.getTag());
            }

        }).attachToRecyclerView(recyclerView); //attach touch helper to recycler view

        mEditTextName=findViewById(R.id.edittext_name);
        mTextViewAmount=findViewById(R.id.textview_amount);

        //defining buttons
        Button buttonIncrease = findViewById(R.id.button_increase);
        Button buttonDecrease = findViewById(R.id.button_decrease);
        Button buttonAdd = findViewById(R.id.button_add);


        //Setting onclick listener for each button
        buttonIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //defined below
                increase();
            }
        });

        buttonDecrease.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //defined below
                decrease();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //defined below
                addItem();
            }
        });
    }

    //button functions
    private void increase(){
        //increase amount count
        mAmount++;
        mTextViewAmount.setText(String.valueOf(mAmount));
    }

    private void decrease(){
        //opposite of increase() function
        if (mAmount>0) {
            mAmount--;
            mTextViewAmount.setText(String.valueOf(mAmount));
        }
    }

    private void addItem(){
        //for actually adding item to sqlite

        if (mEditTextName.getText().toString().trim().length()==0||mAmount==0){
            //Error handling to prevent adding nothing.
            //trim() allows us to remove empty spaces
            return;
        }

        String name=mEditTextName.getText().toString();
        ContentValues cv = new ContentValues();
        cv.put(GroceryContract.GroceryEntry.COLUMN_NAME,name);
        cv.put(GroceryContract.GroceryEntry.COLUMN_AMOUNT,mAmount);

        mDatabase.insert(GroceryContract.GroceryEntry.TABLE_NAME,null,cv);
        //update cursor
        mAdapter.swapCursor(getAllItems());

        mEditTextName.getText().clear();

    }

    private void removeItem(long id){
        //remove from database where ID in database matches swipe id
        mDatabase.delete(GroceryContract.GroceryEntry.TABLE_NAME,
                GroceryContract.GroceryEntry._ID+"="+id,null);
        mAdapter.swapCursor(getAllItems());
    }

    //make method to return cursor
    private Cursor getAllItems(){
        return mDatabase.query(
                GroceryContract.GroceryEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                GroceryContract.GroceryEntry.COLUMN_TIMESTAMP+ " DESC"
        );
    }

}
