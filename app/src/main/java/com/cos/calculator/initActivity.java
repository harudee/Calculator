package com.cos.calculator;

public interface initActivity {

    void init();
    void initListener();
    void initSetting();

    default void initViewModel(){}
    default void initAdapter(){}
    default void initData(){}

}
