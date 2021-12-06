package com.example.my;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MemoAddActivity extends AppCompatActivity {

    EditText edtmemo,edtcontents;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {         // Edittext 입력 중 다른 곳 터치하면 타자기 내리기
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_add);

        edtmemo = findViewById(R.id.edtmemo);
        edtcontents = findViewById(R.id.edtcontents);

        findViewById(R.id.btndone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edtmemo.getText().toString();
                String contents = edtcontents.getText().toString();

                if(title.length() >0){
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    String substr = sdf.format(date);

//                    Toast.makeText(MemoAddActivity.this,title+","+substr,Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent();
                    intent.putExtra("title",title);
                    intent.putExtra("content",contents);
                    intent.putExtra("sub",substr);
                    setResult(0,intent);

                    finish();
                }
            }
        });

        findViewById(R.id.btncancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemoAddActivity.this,MemoMainActivity.class);
                startActivity(intent);
            }
        });
    }
}