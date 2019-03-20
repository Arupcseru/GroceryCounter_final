package com.example.grocerycounter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddData extends AppCompatActivity {

    EditText et_name,et_amount,et_rate;
    Button btn_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        init_variables();
        init_functions();
        init_listeners();
    }

    private void init_listeners() {
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SqlHelper sqlHelper = new SqlHelper(getApplicationContext());
                if((!TextUtils.isEmpty(et_name.getText().toString().trim()))&&
                        (!TextUtils.isEmpty(et_amount.getText().toString().trim()))&&
                        (!TextUtils.isEmpty(et_rate.getText().toString().trim()))
                    ){

                    Date date = Calendar.getInstance().getTime();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String currentdate = simpleDateFormat.format(date);


                    ShopObject shopObject = new ShopObject(1,et_name.getText().toString().trim(),Integer.parseInt(et_amount.getText().toString().trim()),
                            Float.parseFloat(et_rate.getText().toString().trim()),currentdate);
                   if( sqlHelper.insert(shopObject)){
                       Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                       Intent intent = new Intent(AddData.this,MainActivity.class);
                       startActivity(intent);
                   }else{
                       Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                   }
                }else{
                    Log.d("okey","NOT OKK");
                }


            }
        });
    }

    private void init_functions() {

    }

    private void init_variables() {
        et_name = (EditText)findViewById(R.id.product_name);
        et_amount = (EditText)findViewById(R.id.product_amount);
        et_rate = (EditText)findViewById(R.id.product_rate);
        btn_submit = (Button)findViewById(R.id.submit);
    }
}
