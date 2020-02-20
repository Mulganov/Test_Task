package com.mulganov.test_task.tools;

import android.content.Context;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mulganov.test_task.R;
import com.mulganov.test_task.sidebar.SidebarClass;
import com.mulganov.test_task.sidebar.SidebarElement;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JSONHelper {

    public static List<SidebarElement> getSidebar(String uurl, String file, TextView text, Context context) {
        InternetHelper.getFile(uurl, file, text);
        return importFromJSON(context, file);
    }

    private static List<SidebarElement> importFromJSON(Context context, String file) {

        InputStreamReader streamReader = null;
        FileInputStream fileInputStream = null;
        try{
            fileInputStream = new FileInputStream(file);
            streamReader = new InputStreamReader(fileInputStream);
            Gson gson = new Gson();
            SidebarClass elements = gson.fromJson(streamReader, SidebarClass.class);
            return  elements.getMenu();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        finally {
            if (streamReader != null) {
                try {
                    streamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}