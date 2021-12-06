package com.example.my;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.ApplicationExitInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    String ToMsg;
    String senMsg;
    TcpService Ts; // 서비스 객체
    boolean isService = false; // 서비스 중인 확인용

    ServiceConnection conn = new ServiceConnection() {
        public void onServiceConnected(ComponentName name,
                                       IBinder service) {
// 서비스와 연결되었을 때 호출되는 메서드
// 서비스 객체를 전역변수로 저장
            TcpService.MyBinder mb = (TcpService.MyBinder) service;
            Ts = mb.getService(); // 서비스가 제공하는 메소드 호출하여
// 서비스쪽 객체를 전달받을수 있슴
            isService = true;
        }
        public void onServiceDisconnected(ComponentName name) {
// 서비스와 연결이 끊겼을 때 호출되는 메서드
            isService = false;
        }
    };

    private DrawerLayout drawerLayout;
    private View drawerView;
    private long backKeyPressedTime = 0;   // 마지막으로 뒤로 가기 버튼을 눌렀던 시간 저장
    private Toast toast;  // 첫 번째 뒤로 가기 버튼을 누를 때 표시

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageButton memo = findViewById(R.id.btnmemo);
        memo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MemoMainActivity.class);
                startActivity(intent);
            }
        });


//        ImageButton calender = findViewById(R.id.btncalen);
//        calender.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,CalenderActivity.class);
//                startActivity(intent);
//            }
//        });

        ImageButton cctv = findViewById(R.id.btncctv);
        cctv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CctvActivity.class);
                startActivity(intent);
            }
        });

        ImageButton tembtn = findViewById(R.id.temactivity);
        tembtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tem = new Intent(MainActivity.this, TemActivity.class);
                startActivity(tem);
            }
        });

        TextView temreceive = findViewById(R.id.Temreceive);

        Intent intent_T = new Intent(
                MainActivity.this, // 현재 화면
                TcpService.class); // 다음넘어갈 컴퍼넌트

        bindService(intent_T, // intent 객체
                conn, // 서비스와 연결에 대한 정의
                Context.BIND_AUTO_CREATE);

        Intent intent = getIntent();
        String tem = intent.getStringExtra("tem");
        String moi = intent.getStringExtra("moi");
        String rec = intent.getStringExtra("rec");

        if (tem == null && moi == null) {
            temreceive.setText("설정 온도 :\n설정 습도 :");
        } else {
            temreceive.setText("설정 온도  :   " + tem + "℃" + "\n" + "설정 습도  :   " + moi + "%");

        }

        drawerLayout = findViewById(R.id.mainactivi);
        drawerView = findViewById(R.id.drawer);

        TextView reMsg = findViewById(R.id.reMsg);
        reMsg.post(new Runnable() {
            @Override
            public void run() {
            }
        });
        reMsg.setText(Ts.returnMsg());

        ImageButton mnuopen = findViewById(R.id.mnuopen);
        mnuopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }
        });


        ImageButton asdf = findViewById(R.id.asdf);
        asdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }



    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        // 기존 뒤로 가기 버튼의 기능을 막기 위해 주석 처리 또는 삭제

        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 2.5초를 더해 현재 시간과 비교 후
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 2.5초가 지났으면 Toast 출력
        // 2500 milliseconds = 2.5 seconds
        if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 2.5초를 더해 현재 시간과 비교 후
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 2.5초가 지나지 않았으면 종료
        if (System.currentTimeMillis() <= backKeyPressedTime + 2500) {
            moveTaskToBack(true);
            finishAndRemoveTask();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}