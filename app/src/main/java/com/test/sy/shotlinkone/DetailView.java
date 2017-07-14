package com.test.sy.shotlinkone;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jaemoon on 2016-11-26.
 */
public class DetailView extends Activity {

    private ArrayList<SQLManager.content> dbData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);


        SQLManager sqlm = new SQLManager(DetailView.this,"content.db",null,1);
        dbData = sqlm.SelectAll(); // db의 값을 불러 온다.


        String posi = getIntent().getStringExtra("position");

        int position = Integer.parseInt(posi);




        Bitmap bitmap = ImageRotater.SafeDecodeBitmapFile(dbData.get(position).filepath);


        ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setImageBitmap(bitmap);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String posi = getIntent().getStringExtra("position");
                int position = Integer.parseInt(posi);
                String  Realurl = dbData.get(position).url;
                String temp = Realurl;
                String url = Realurl;
                if (temp.contains("http://") || temp.contains("https://")) {
                    url = temp.split("://")[1];
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + url));
                startActivity(intent);

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

    }
}
