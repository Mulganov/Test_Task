package com.mulganov.test_task.tools;

import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;

import com.mulganov.test_task.sidebar.SidebarAdaptor;
import com.mulganov.test_task.sidebar.SidebarElement;

public class TouchEvents {


    public static void onTouchAdapter(AdapterView<?> adapterView, View view, int i, long l) {
        System.out.println("event");
//        Adapter a = (Adapter) adapterView;
//        SidebarAdaptor adaptor = (SidebarAdaptor) a;
//
//        System.out.println(adaptor.getItem(i).toString());
    }
}
