package com.mulganov.test_task.sidebar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.mulganov.test_task.MainActivity;
import com.mulganov.test_task.R;
import com.mulganov.test_task.tools.InternetHelper;

import java.util.ArrayList;

public class SidebarAdaptor extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<SidebarElement> objects;
    ImageView image;
    TextView text;
    WebView web;

    public SidebarAdaptor(Context context, ArrayList<SidebarElement> sidebarElements, TextView text, ImageView image, WebView web) {
        ctx = context;

        this.text = text;
        this.image = image;
        this.web = web;

        objects = sidebarElements;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.element_menu, parent, false);
        }

        SidebarElement p = getProduct(position);

        ((Button) view.findViewById(R.id.el_button)).setText(p.getName());

        ((Button) view.findViewById(R.id.el_button)).setOnTouchListener(this::onTouch);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private boolean onTouch(View view, MotionEvent event) {
        int action = event.getActionMasked();

        switch (action){
            case MotionEvent.ACTION_DOWN:
                String name = ((Button) view).getText() + "";

                SidebarElement el = getElement(name);

                MainActivity.number = getIndex(name);

                if (el == null) return false;

                String fun = el.getFunction();
                String param = el.getParam();

                switch (fun){
                    case "text":
                        web.post(new Runnable() {
                            @Override
                            public void run() {
                                web.setVisibility(View.INVISIBLE);
                            }
                        });

                        image.post(new Runnable() {
                            @Override
                            public void run() {
                                image.setVisibility(View.INVISIBLE);
                            }
                        });

                        text.post(new Runnable() {
                            @Override
                            public void run() {
                                text.setText(param);
                                text.setVisibility(View.VISIBLE);
                            }
                        });
                        break;
                    case "image":

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                InternetHelper.getFile(param, view.getContext().getCacheDir().getPath() + "/temp/temp.png", null);

                                Bitmap bitmap = BitmapFactory.decodeFile(view.getContext().getCacheDir().getPath() + "/temp/temp.png");

                                image.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        image.setImageBitmap(bitmap);
                                        image.setVisibility(View.VISIBLE);
                                    }
                                });
                            }
                        }).start();

                        text.post(new Runnable() {
                            @Override
                            public void run() {
                                text.setVisibility(View.INVISIBLE);
                            }
                        });

                        web.post(new Runnable() {
                            @Override
                            public void run() {
                                web.setVisibility(View.INVISIBLE);
                            }
                        });

                        break;

                    case "url":

                        text.post(new Runnable() {
                            @Override
                            public void run() {
                                text.setVisibility(View.INVISIBLE);
                            }
                        });

                        image.post(new Runnable() {
                            @Override
                            public void run() {
                                image.setVisibility(View.INVISIBLE);
                            }
                        });


                        web.post(new Runnable() {
                            @Override
                            public void run() {
                                web.loadUrl(param);
                                web.setVisibility(View.VISIBLE);
                            }
                        });

                        break;
                }
                break;
        }

        return false;
    }

    // товар по позиции
    SidebarElement getProduct(int position) {
        return ((SidebarElement) getItem(position));
    }

    private SidebarElement getElement(String name){

        for (SidebarElement el: objects){
            if (el.getName().equalsIgnoreCase(name)){
                return el;
            }
        }

        return null;
    }

    private int getIndex(String name){
        int i = 0;
        for (SidebarElement el: objects){
            if (el.getName().equalsIgnoreCase(name)){
                return i;
            }
            i++;
        }

        return 0;
    }

}