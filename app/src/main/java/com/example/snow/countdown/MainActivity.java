package com.example.snow.countdown;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText inputTime;
    private TextView showTime;
    private Button getTime, startTime, stopTime;

    private int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        inputTime = (EditText) findViewById(R.id.et_inputTime);
        showTime = (TextView) findViewById(R.id.showTime);
        getTime = (Button) findViewById(R.id.getTime);
        startTime = (Button) findViewById(R.id.startTime);
        stopTime = (Button) findViewById(R.id.stopTime);

        getTime.setOnClickListener(this);
        startTime.setOnClickListener(this);
        stopTime.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.getTime:
                showTime.setText(inputTime.getText().toString().trim());
                i = Integer.parseInt(showTime.getText().toString());
                break;
            case R.id.startTime:
                //开始计时的方法;
                startTime();
                break;
            case R.id.stopTime:
                //停止计时的方法
                stopTime();
                break;
        }
    }

    private Timer timer;
    private TimerTask task;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            showTime.setText(String.valueOf(msg.arg1));
            startTime();

        }
    };
    private void startTime() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                //i--;
                Message msg = mHandler.obtainMessage();
                msg.arg1=--i;
                mHandler.sendMessage(msg);

            }

        };
        timer.schedule(task,1000);
        if (i <= 3) {
            showTime.setTextColor(Color.RED);
            if (i == 0) {
                timer.cancel();
                task.cancel();
            }
        }
    }

    private void stopTime() {
        task.cancel();
        timer.cancel();

    }
}
