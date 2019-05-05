package com.example.flashlight;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_CAMERA = 1;
    private Button button;
    private boolean on;
    private Camera camera;
    private ImageButton tombol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        on = false;

        ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA }, REQUEST_CODE_CAMERA);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Click", Toast.LENGTH_SHORT).show();
                if (on) {
                    turnOff();
                } else {
                    turnOn();
                }
                on = !on;
                updateButton();
            }
        });

        tombol = findViewById(R.id.tombol);
        tombol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Halo", Toast.LENGTH_SHORT).show();
                if (on) {
                    turnOff();
                } else {
                    turnOn();
                }
                on = !on;
                updateButton();
            }
        });

        updateButton();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                finish();
            }
        }
    }

    private void updateButton() {
        if (on) {
            button.setText("Matikan");
            tombol.setImageDrawable(getResources().getDrawable(R.drawable.on));
        }
        else{
            button.setText("Menyala");
            tombol.setImageDrawable(getResources().getDrawable(R.drawable.off));
        }
    }

    private void turnOff() {
        camera.stopPreview();
        camera.release();
    }

    private void turnOn() {
        camera = Camera.open();
        Camera.Parameters params = camera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(params);
        camera.startPreview();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        turnOff();
    }
}
