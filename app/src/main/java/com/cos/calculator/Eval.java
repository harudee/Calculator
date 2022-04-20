package com.cos.calculator;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Eval {

    public static String cal(String result){
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        try {
            return engine.eval(result).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
