package com.test.sy.shotlinkone;

import android.app.Activity;

/**
 * Created by jaemoon on 2016-11-25.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SQLSaved extends Activity {

    String filepath="";
    String url="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.details_activity);
        ptc_SetScreenSize();

        Intent intent = getIntent();
        filepath = intent.getStringExtra("filepath");
        url = intent.getStringExtra("url");





        SQLManager sqlm = new SQLManager(SQLSaved.this,"content.db",null,1);
        /*sqlm.onUpgrade(sqlm.getWritableDatabase(),1,2);*/
        sqlm.Insert(url,"",filepath);



        Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent1);





    }

    private void ptc_SetScreenSize() {
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        int width = (int) (display.getWidth() * 0.8); //Display 사이즈의 70%

        int height = (int) (display.getHeight() * 0.4);  //Display 사이즈의 90%

        getWindow().getAttributes().width = width;

        getWindow().getAttributes().height = height;
    }

}

