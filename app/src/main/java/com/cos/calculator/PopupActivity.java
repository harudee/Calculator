package com.cos.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cos.calculator.dao.HistoryDAO;
import com.cos.calculator.model.History;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class PopupActivity extends AppCompatActivity implements initActivity {

    private static final String TAG = "PopupActivity";
    private PopupActivity mContext = this;

    private Button btnClose, btnHistoryClear;
    private AlertDialog.Builder builder;
    private AlertDialog mRecodeDialog;

    private RecyclerView historyView;
    private List<History> historyList;
    private HistoryAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup);

        init();
        initListener();
        initData();

    }

    //외부 클릭시 창닫힘 방지
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }


    public void init(){

        btnClose = findViewById(R.id.btn_close);
        btnHistoryClear = findViewById(R.id.btn_history_clear);
        historyView = findViewById(R.id.history_view);
        historyView.setLayoutManager(new LinearLayoutManager(mContext));

    }

    @Override
    public void initListener() {

        btnClose.setOnClickListener(v -> {
            finish();
        });

        btnHistoryClear.setOnClickListener(v -> {

            new Thread(){
                @Override
                public void run() {

                    deleteAll();

                }
            }.start();

            finish();

        });

    }

    @Override
    public void initSetting() {

    }


    public void initData(){
        //파일 읽기
        /*String str = null;
        try {
            BufferedInputStream bis =
                    new BufferedInputStream(new FileInputStream(new File(getFilesDir() + "/recode.txt")));

            byte[] buff = new byte[9999]; //버퍼 배열

            int nRLen = bis.read(buff); //파일 크기

            str = new String(buff, 0, nRLen); //byte -> string

            bis.close();

        } catch (IOException e) {
            Log.d(TAG, "readRecode: 오류 발생 "+e);
            e.printStackTrace();
        }*/


        historyAdapter = new HistoryAdapter(historyList);
        historyView.setAdapter(historyAdapter);


        new Thread(){

            @Override
            public void run() {

                loadAll();

            }
        }.start();


    }

    private void loadAll(){

        HistoryDB historyDB = HistoryDB.getInstance(mContext);

        historyList = historyDB.historyDAO().getAll();
        historyAdapter.setHistoryList(historyList);

    }

    private void deleteAll(){

        HistoryDB historyDB = HistoryDB.getInstance(mContext);
        historyDB.historyDAO().deleteAll();

        //historyAdapter.notifyDataSetChanged();

    }


}