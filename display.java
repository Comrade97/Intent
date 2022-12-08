package com.example.lab_4_intend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class display extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Bundle extras = getIntent().getExtras();

        String sName = extras.getString("Name");
        String sPhone = extras.getString("Phone");
        String sDOB = extras.getString("DOB");
        String sEmail = extras.getString("Email");
        String simg = extras.getString("img");
        loadImageFromStorage(simg);

        final TextView tName = (TextView) findViewById(R.id.result);
        tName.setText("\nName: "+sName+"\nPhone: "+sPhone+"\nDOB: "+sDOB+"\nEmail: "+sEmail);

        Toast.makeText(display.this,"Your Profile is ready",Toast.LENGTH_SHORT).show();
        Button btnHome = findViewById(R.id.btnBack);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(display.this, "Redirect to Home Menu", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(in);
            }
        });
    }
    private void loadImageFromStorage(String path) {
        try {
            File f = new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            ImageView img = (ImageView) findViewById(R.id.profile);
            img.setImageBitmap(b);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}