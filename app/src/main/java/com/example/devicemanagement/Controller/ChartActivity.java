package com.example.devicemanagement.Controller;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.devicemanagement.R;

public class ChartActivity extends AppCompatActivity {
    ImageButton imbBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_report);
    }
    private void setEvent() {


        imbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void setControl() {

        imbBack = findViewById(R.id.imbBack);
    }
}
