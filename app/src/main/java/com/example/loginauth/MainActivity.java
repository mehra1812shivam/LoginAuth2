package com.example.loginauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_main);
        view=findViewById(R.id.view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String format = "https://drive.google.com/viewerng/viewer?embedded=true&url=%s";
                String fullPath = String.format(Locale.ENGLISH, format, "https://www.india.gov.in/sites/upload_files/npi/files/coi_part_full.pdf");
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fullPath));
                startActivity(browserIntent);
            }
        });
    }
}
