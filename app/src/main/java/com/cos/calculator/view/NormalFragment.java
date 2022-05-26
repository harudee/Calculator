package com.cos.calculator.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cos.calculator.R;

public class NormalFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "NormalFragment";
    private NormalFragment mContext = this;

    Button btn0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       
        View view =  inflater.inflate(R.layout.fragment_normal, container, false);

        btn0 = view.findViewById(R.id.num_0);

        init();
        initListener();
        initData();

        return view;


    }

    private void init(){


        
    }

    private void initListener(){
        


    }

    private void initData(){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.num_0:
                Log.d(TAG, "onClick: 0 클릭됨");
                break;
        }
        
    }
}