package com.mulganov.test_task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mulganov.test_task.sidebar.SidebarAdaptor;
import com.mulganov.test_task.sidebar.SidebarElement;
import com.mulganov.test_task.tools.InternetHelper;
import com.mulganov.test_task.tools.JSONHelper;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    public static int number = 0;

    final static String nameVariableKey = "NAMBER_VARIABLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.activity_main_portrait);
            System.out.println("port");
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            setContentView(R.layout.activity_main_landscape);
            System.out.println("land");
        }

    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        outState.putString(nameVariableKey, number);
//        super.onSaveInstanceState(outState);
//    }
//    // получение ранее сохраненного состояния
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        number = savedInstanceState.getString(nameVariableKey);
//
//        if (number == null) number = "0";
//    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);

        final MainActivity main = this;

        new Thread(new Runnable() {
            @Override
            public void run() {
                String uurl = "https://www.dropbox.com/s/fk3d5kg6cptkpr6/menu.json?dl=1";
                File f = new File(getCacheDir().getPath() + "/temp/sidebar");
                f.mkdirs();
                ArrayList<SidebarElement> list = (ArrayList<SidebarElement>) JSONHelper.getSidebar(uurl, getCacheDir().getPath() + "/temp/sidebar/menu.json", (TextView)findViewById(R.id.status), main);

                TextView text = findViewById(R.id.text);
                ImageView image = findViewById(R.id.image);
                WebView web = findViewById(R.id.web);

                final SidebarAdaptor adaptor = new SidebarAdaptor(main, list, text, image, web);

                final ListView l = findViewById(R.id.sidebar);
                //l.setOnItemClickListener(TouchEvents::onTouchAdapter);

                l.post(new Runnable() {
                    @Override
                    public void run() {
                        l.setAdapter(adaptor);
                        ((TextView)findViewById(R.id.status)).setText("Адаптер успешно добавлен\nМеню успешно созданно");
                    }
                });

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        TextView text = ((TextView)findViewById(R.id.status));
                        for (;true;){
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (text.getAlpha() > 0)
                                text.setAlpha(text.getAlpha() - 0.01f);
                            else{
                                text.setVisibility(View.INVISIBLE);
                                break;
                            }
                        }
                    }
                }).start();

                SidebarElement el = list.get((number));

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
                                InternetHelper.getFile(param, getCacheDir().getPath() + "/temp/temp.png", null);

                                Bitmap bitmap = BitmapFactory.decodeFile(getCacheDir().getPath() + "/temp/temp.png");

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
            }
        }).start();

    }
}
