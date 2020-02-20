package com.mulganov.test_task.sidebar;

import android.graphics.Bitmap;

public class SidebarElement {

    private String name;
    private String function;
    private String param;

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setFunction(String function){
        this.function = function;
    }
    public String getFunction(){
        return function;
    }

    public void setParam(String param){
        this.param = param;
    }
    public String getParam(){
        return param;
    }

    public String toString(){
        String text = "";

        text += "------------------" +  "\n";
        text += name +  "\n";
        text += function +  "\n";
        text += param +  "\n";

        return text;
    }
}