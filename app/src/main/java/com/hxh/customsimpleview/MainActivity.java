package com.hxh.customsimpleview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hxh.simpleview_lib.SearchEditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SearchEditText set =  findViewById(R.id.set);
        set.setSearchListener(new SearchEditText.OnSearchListener() {
            @Override
            public void onSearch(String str) {
                Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void submit(View view) {

    }
}
