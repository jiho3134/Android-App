package com.example.my;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TcpService extends Service {

    MainActivity mainActivity = new MainActivity();
    int a;
    String msg = "비었음";
    String getmsg ; //"2323";
    String recmsg;
    String receiveMsg;
    Intent IT_S;
    Socket socket;
    PrintWriter out;
    BufferedReader in;
    String GetMsg;
    int server_conn = 0;
    String ip = "192.168.2.44";
    int port = 9000;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IT_S = intent;
        return super.onStartCommand(intent, flags, startId);
    }

    public TcpService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Intent intent2 = new Intent(
                getApplicationContext()
                , NotiService.class);
        startService(intent2);

        Thread work = new Thread() {
            public void run() {
                while (true) {
                    if (server_conn == 0) {
                        try {
                            socket = new Socket(ip, port);
                            out = new PrintWriter(socket.getOutputStream(), true);                                                                                                                   //전송한다.
                            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                            server_conn = 1;


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (server_conn == 1) {
                        if(getmsg == null){
                            out.write("3");
                            out.flush();
                        }else {
                            out.write(getmsg);
                            out.flush();
                        }
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        work.start();
    }
    IBinder mBinder = new MyBinder();

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    class MyBinder extends Binder {
        TcpService getService() { // 서비스 객체를 리턴
            return TcpService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // 액티비티에서 bindService() 를 실행하면 호출됨
        // 리턴한 IBinder 객체는 서비스와 클라이언트 사이의 인터페이스 정의한다
        a++;
        return mBinder;
    }

    String returnMsg() {
        return receiveMsg;
    }

    void SendMsg(String str) {
        getmsg = str;
    }

}