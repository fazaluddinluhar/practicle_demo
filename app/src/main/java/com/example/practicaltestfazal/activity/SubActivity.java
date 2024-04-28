package com.example.practicaltestfazal.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.practicaltestfazal.R;


public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        TextView tvID = findViewById(R.id.tvID);
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvBody = findViewById(R.id.tvBody);

        if (getIntent().getExtras() != null) {
            tvID.setText(String.valueOf(getIntent().getExtras().getInt("ID")));
            tvTitle.setText(getIntent().getExtras().getString("Title"));
            tvBody.setText(getIntent().getExtras().getString("Body"));
        }
    }
}
