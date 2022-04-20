package com.example.devicemanagement.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.devicemanagement.R;

public class ChiTietSuDungActivity extends AppCompatActivity {

    ImageButton imvBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_sudung);
        setControl();
        setEvent();
    }

    private void setEvent() {
        imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setControl() {
        imvBack = findViewById(R.id.imvBack);
    }
}