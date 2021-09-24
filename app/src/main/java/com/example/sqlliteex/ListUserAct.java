package com.example.sqlliteex;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ListUserAct extends AppCompatActivity {
    private DBHelper db;
    private Cursor c;
    private SimpleCursorAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);
        db =  new DBHelper(this);
        ListView lvUser = findViewById(R.id.lvUser);
        c = db.getAllUser();
        adapter = new SimpleCursorAdapter(this,R.layout.item_user,c,new String[]{DBHelper.ID,DBHelper.NAME,DBHelper.GENDER},new int[]{
                R.id.tvId,R.id.tvName,R.id.tvGender
        }, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lvUser.setAdapter(adapter);
        lvUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor = (Cursor) adapter.getItem(i);
                @SuppressLint("Range") int _id = cursor.getInt(cursor.getColumnIndex(DBHelper.ID));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DBHelper.NAME));
                @SuppressLint("Range") String gender = cursor.getString(cursor.getColumnIndex(DBHelper.GENDER));
                @SuppressLint("Range") String des = cursor.getString(cursor.getColumnIndex(DBHelper.DES));

                Intent intent = new Intent(ListUserAct.this,UpdateAct.class);
                intent.putExtra(DBHelper.ID,_id);
                intent.putExtra(DBHelper.NAME,name);
                intent.putExtra(DBHelper.GENDER,gender);
                intent.putExtra(DBHelper.DES,des);
                startActivity(intent);
            }
        });
    }

    protected void onStart(){
        super.onStart();
        c = db.getAllUser();
        adapter.changeCursor(c);
        adapter.notifyDataSetChanged();
        db.close();
    }
}