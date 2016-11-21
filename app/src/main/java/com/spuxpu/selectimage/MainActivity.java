package com.spuxpu.selectimage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.qiao.activity.DemoActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClick(View view){
        Intent intent =  new Intent(this, DemoActivity.class);

        ArrayList<String> selectList = new ArrayList<String>();
        intent.putExtra("datalist", selectList);
        startActivity(intent);
    }
}
