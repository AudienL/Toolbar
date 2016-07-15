package com.audienl.toolbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.audienl.library.Toolbar;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setOnToolbarClickedListener(new Toolbar.SimpleOnToolbarClickedListener() {
            @Override
            public void onReturnClick() {
                finish();
            }

            @Override
            public void onButton1Click() {
                super.onButton1Click();
                Toast.makeText(MainActivity.this, "未实现", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
