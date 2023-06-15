package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView timetxt;
    FloatingActionButton p,s,r;
    Boolean running=false;
    Boolean wasrunning=false;
    int second=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timetxt=findViewById(R.id.timetext);
        p=findViewById(R.id.play);
        s=findViewById(R.id.stoptime);
        r=findViewById(R.id.resert);

        if(savedInstanceState!=null){
            second=savedInstanceState.getByte("second");

            running=savedInstanceState.getBoolean("running");

            wasrunning=savedInstanceState.getBoolean("wasrunning");
        }

        rendertime();
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onstart();
                v.performHapticFeedback(HapticFeedbackConstants.CONFIRM);
            }
        });
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onstop();
            }
        });
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onreset();
            }
        });
    }

    private void rendertime() {
        Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hour=second/3600;
                int min= (second%3600)/60;
                int sec=second%60;



                String timeformat=String.format(Locale.getDefault(),"%d:%02d:%02d",hour,min,sec);
                timetxt.setText(timeformat);
                if(running){
                    second++;
                }
                handler.postDelayed(this,1000);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasrunning=running;
        running=false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(wasrunning){
            running=true;
        }
    }

    public void onstart(){
        running=true;
    }
    public void onstop(){
        running=false;
    }
    public void onreset(){
        running=false;

        second=0;
    }
}