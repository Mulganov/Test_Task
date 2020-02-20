package com.mulganov.test_task.tools;

import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

public class InternetHelper {

    public static File getFile(String uurl, String file, TextView text){
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            //text.setText("Получаем ссылку...");
            setStatus(text, "Получаем ссылку...");
            URL url = new URL(uurl);
            //text.setText("Получили ссылку\n(открывание потока для чтение файла)...");
            setStatus(text, "Получили ссылку\n(открывание потока для чтение файла)...");
            bis = new BufferedInputStream(url.openStream());
            //text.setText("Открыт поток для чтения...");
            setStatus(text, "Открыт поток для чтения...");
            bos = new BufferedOutputStream(new FileOutputStream(new File(file)));
            //text.setText("Открыт поток для записи...");
            setStatus(text, "Открыт поток для записи...");

            int c;
            //text.setText("Началось чтение файла...");
            setStatus(text, "Началось чтение файла...");
            while ((c = bis.read()) != -1) {
                bos.write(c);
            }

            //text.setText("Чтение завершенно...");
            setStatus(text, "Чтение завершенно...");
            System.out.println(uurl + " OK");

            bos.close();
            bis.close();

            return new File(file);

        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println(uurl + " NOT");
        }

        return null;
    }

    private static void setStatus(final TextView textView, final String text){
        if (textView == null) return;
        textView.post(new Runnable() {
            @Override
            public void run() {
                textView.setText(text);
            }
        });
    }
}
