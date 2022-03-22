package com.example.mymemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editText=findViewById(R.id.editMemo);

        findViewById(R.id.btnDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = editText.getText().toString();

                if(str.length()>0){
                    Date date = new Date();
                    SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");

                    String timestr = sdf.format(date);

                    Toast.makeText(AddActivity.this, str+","+timestr, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent();
                    intent.putExtra("main", str);
                    intent.putExtra("time", timestr);
                    setResult(0,intent);
                    finish();
                }
            }
        });


        findViewById(R.id.btnCancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
}
}