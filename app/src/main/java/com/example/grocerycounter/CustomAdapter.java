package com.example.grocerycounter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CustomAdapter extends BaseAdapter {
    Context mcontext;
    ArrayList<ShopObject> shopObjects;
    LayoutInflater inflater;
    SqlHelper sqlHelper ;

    public CustomAdapter(Context mcontext, ArrayList<ShopObject> shopObjects) {
        this.mcontext = mcontext;
        this.shopObjects = shopObjects;
        inflater = (LayoutInflater)mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sqlHelper = new SqlHelper(mcontext);
    }

    @Override
    public int getCount() {
        return shopObjects.size();
    }

    @Override
    public Object getItem(int i) {
        return shopObjects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        try {
            getDateInfo(i);
            Info.setId(i);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        view = inflater.inflate(R.layout.customadapter,null);
        TextView tv_name = (TextView)view.findViewById(R.id.pro_name);
        TextView tv_amount  = (TextView)view.findViewById(R.id.pro_amount);
        ImageButton btn_delete = (ImageButton)view.findViewById(R.id.btn_delete);
        ImageButton btn_edit = (ImageButton)view.findViewById(R.id.btn_edit);

        tv_amount.setText(Integer.toString(shopObjects.get(i).getAmount()));
        tv_name.setText(shopObjects.get(i).getName());

        //buttons/////////////////////

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("mytag", "onClick: "+shopObjects.get(i).getId());
                if(sqlHelper.delete(shopObjects.get(i).getId())){
                    Toast.makeText(mcontext,"Deleted Successful" , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mcontext,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mcontext.startActivity(intent);
                }else{
                    Toast.makeText(mcontext,"Deleted Failed" , Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext,EditActivity.class);
                intent.putExtra("id",shopObjects.get(i).getId());
                intent.putExtra("name",shopObjects.get(i).getName());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent);

            }
        });


        return view;
    }
    private void getDateInfo(int i) throws ParseException {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String format = simpleDateFormat.format(date);

        String date1 = shopObjects.get(i).getDate();
        if(TextUtils.equals(format, date1)){
            Toast.makeText(mcontext,"Already date updated",Toast.LENGTH_SHORT).show();
        }else{
            Date after = simpleDateFormat.parse(format);
            long after_mil = after.getTime();
            Date before = simpleDateFormat.parse(shopObjects.get(i).getDate());
            long before_mil = before.getTime();

            long difference = after_mil - before_mil;
            int days = (int) ((difference)/(1000*60*60*24));
            int amount  =  shopObjects.get(i).getAmount();
            if(days>0) {
                amount -= (shopObjects.get(i).getRate() * days);
                if (sqlHelper.updateDate(shopObjects.get(i).getId(), amount, format)) {
                    Log.i("date", "Updated date");
                } else {
                    Log.i("date","Failed to update date");
                }
            }
            else{
                Toast.makeText(mcontext,"date not updated",Toast.LENGTH_SHORT).show();
            }
        }


    }
}
