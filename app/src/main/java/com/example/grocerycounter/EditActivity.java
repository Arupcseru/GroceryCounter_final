package com.example.grocerycounter;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    TextView tv_title;
    Button btn_update;
    EditText et_update;
    SqlHelper sqlHelper;
    int amount  = 0;
    int id;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        id = intent.getIntExtra("id",-1);
        name = intent.getStringExtra("name");
        Log.i("name is",name);
        Log.i("id",String.valueOf(id));

        init_view();
        init_variables();
        init_function();
        init_listeners();
    }




    private void init_view() {
        tv_title = (TextView) findViewById(R.id.title);
        et_update = (EditText) findViewById(R.id.et_update);
        btn_update = (Button)findViewById(R.id.btn_update);

    }

    private void init_variables() {
        sqlHelper = new SqlHelper(getApplicationContext());
        Cursor singleItem = sqlHelper.getSingleItem(id);
        if (singleItem.getCount() > 0) {
            singleItem.moveToFirst();
            amount = singleItem.getInt(2);
        }
        else
            Toast.makeText(getApplicationContext(),"Data Not Found",Toast.LENGTH_SHORT).show();



    }

    private void init_function() {
        tv_title.setText(name);
    }

    private void init_listeners() {

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int add = Integer.parseInt(et_update.getText().toString().trim());
                amount+=add;

                boolean update = sqlHelper.update(amount,id);
                Log.i("amount",String.valueOf(id));

                if(update){
                    Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditActivity.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Failed to update",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
