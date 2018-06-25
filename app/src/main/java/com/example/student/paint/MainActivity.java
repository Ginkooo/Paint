package com.example.student.paint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Surface surface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        surface = new Surface(this, null);
    }

    public void onClicked(View v) {
        synchronized (Surface.block) {
            switch (v.getId()) {
                case R.id.BlueButton:
                    Params.Color = R.color.colorBlue;
                    break;
                case R.id.RedButton:
                    Params.Color = R.color.colorRed;
                    break;
                case R.id.YellowButton:
                    Params.Color = R.color.colorYellow;
                    break;
                case R.id.GreenButton:
                    Params.Color = R.color.colorGreen;
                    break;
                case R.id.ClearButton:
                    Params.ClearCanvas = true;
                    break;

            }
        }
    }
}
