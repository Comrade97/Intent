package com.example.lab_4_intend;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.Menu;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    ImageView imageView;
    EditText varName,varPhone,varBirth,varEmail;
    String n,p,b,e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        varName = (EditText) findViewById(R.id.name);
        varPhone = (EditText) findViewById(R.id.editTextPhone);
        varBirth = (EditText) findViewById(R.id.editTextDate);
        varEmail = (EditText) findViewById(R.id.editTextTextEmailAddress);

        imageView = (ImageView) this.findViewById(R.id.profile);
        Button photoButton = (Button) this.findViewById(R.id.btnCapture);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,CAMERA_REQUEST);
            }
        });

        Button BtnClick = findViewById(R.id.btnClick);
        Button BtnClick1 = findViewById(R.id.btnClick1);
        BtnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageView pimg = findViewById(R.id.profile);
                pimg.buildDrawingCache();
                Bitmap bmap = pimg.getDrawingCache();
                String str = saveToInternalStorage(bmap);

                //Toast.makeText(MainActivity.this,"Explicit Intent Success",Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this,str,Toast.LENGTH_SHORT).show();

                n = varName.getText().toString();
                p=varPhone.getText().toString();
                b=varBirth.getText().toString();
                e=varEmail.getText().toString();

                Intent intent = new Intent(getApplicationContext(),display.class);

                intent.putExtra("Name",n);
                intent.putExtra("Phone",p);
                intent.putExtra("BOD",b);
                intent.putExtra("Email",e);
                intent.putExtra("img",str);
                startActivity(intent);
            }
        });
        BtnClick1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Implicit Intent Success",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://nirmauni.ac.in/"));
                startActivity(i);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());

        File directory = cw.getDir("imageDir",Context.MODE_PRIVATE);
        File mypath = new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG,100,fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }
}