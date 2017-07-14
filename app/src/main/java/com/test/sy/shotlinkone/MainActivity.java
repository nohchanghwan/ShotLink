package com.test.sy.shotlinkone;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends ActionBarActivity {

    final String serviceName = "com.test.sy.shotlinkone.FileService";
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private HashMap<String,ArrayList<SQLManager.content>> childList;
    private ArrayList<SQLManager.content> dbData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLManager sqlm = new SQLManager(MainActivity.this,"content.db",null,1);
        dbData = sqlm.SelectAll(); // db의 값을 불러 온다.


       final ArrayList<SQLManager.content> arr = dbData;
        gridView = (GridView) findViewById(R.id.gridView);

        gridAdapter = new GridViewAdapter(MainActivity.this,0,arr.toArray(new SQLManager.content[arr.size()]));
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

            String b = Integer.toString(position);


               //Create intent
                Intent intent = new Intent(MainActivity.this, DetailView.class);
                intent.putExtra("position", b);

                //Start details activity
                startActivity(intent);




                /*Intent intent = new Intent(MainActivity.this, DetailView.class);

                Bitmap bitmap = ImageRotater.SafeDecodeBitmapFile(dbData.get(position).filepath);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
                byte[] b = outputStream.toByteArray();
                intent.putExtra("b",b);
                intent.putExtra("url", dbData.get(position).url);
                startActivity(intent);*/


            }
        });

/*        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("url", arr.get(i).url);
                String temp = arr.get(i).url;
                String url=arr.get(i).url;
                if(temp.contains("http://")||temp.contains("https://"))
                {
                    url = temp.split("://")[1];
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + url));
                startActivity(intent);
            }
        });*/


        if(isServiceRunning(serviceName)) {
            Toast.makeText(this, "서비스 시작", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(MainActivity.this, FileService.class);
            intent.setFlags(Context.BIND_ADJUST_WITH_ACTIVITY);
            startService(intent);
            Toast.makeText(this, "서비스 시작", Toast.LENGTH_SHORT).show();
        }





    }

    @Override
    public void onBackPressed() {

            super.onBackPressed();
            finish();

    }

    @Override
    protected void onResume() {


        super.onResume();
    }


    public Boolean isServiceRunning(String serviceName) {
        ActivityManager activityManager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo runningServiceInfo :activityManager.getRunningServices(Integer.MAX_VALUE)) {

            if (serviceName.equals(runningServiceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}