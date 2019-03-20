package com.example.grocerycounter;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    SqlHelper sqlHelper;
    ListView listView;
    ArrayList<ShopObject> shopObjectArrayList;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init_views();
        init_variables();
        init_functions();
        init_listeners();



    }



    private void init_listeners() {

    }

    private void init_functions() {
        Cursor cursor = sqlHelper.show();
        cursor.moveToFirst();
        for(int i = 0;i<cursor.getCount();i++){


            int id = cursor.getInt(0);
            Log.i("detail",""+cursor.getInt(0) + cursor.getString(1)+cursor.getInt(2)+cursor.getFloat(3)+cursor.getString(4));
            String name = cursor.getString(1);
            int amount = cursor.getInt(2);
            float rate = cursor.getFloat(3);
            String date = cursor.getString(4);




            cursor.moveToNext();

            shopObjectArrayList.add(new ShopObject(id,name,amount,rate,date));
        }
        customAdapter = new CustomAdapter(getApplicationContext(),shopObjectArrayList);
        customAdapter.notifyDataSetChanged();
        listView.setAdapter(customAdapter);




    }

    private void init_variables() {
        sqlHelper = new SqlHelper(getApplicationContext());
        shopObjectArrayList = new ArrayList<>();

    }

    private void init_views() {
        listView = (ListView)findViewById(R.id.list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.add:
                Intent intent = new Intent(MainActivity.this,AddData.class);
                startActivity(intent);
                break;

            case R.id.detail:
                Intent intent1 = new Intent(MainActivity.this,Details.class);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
