package com.mulganov.test_task.sidebar;

import java.util.ArrayList;

public class SidebarClass {

    private ArrayList<SidebarElement> menu;

    public void setMenu(ArrayList<SidebarElement> menu){
        this.menu = menu;
    }

    public ArrayList<SidebarElement> getMenu(){
        return menu;
    }
}
