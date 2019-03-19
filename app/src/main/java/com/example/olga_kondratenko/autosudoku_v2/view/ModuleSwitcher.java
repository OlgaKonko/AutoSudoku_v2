package com.example.olga_kondratenko.autosudoku_v2.view;


import android.content.Context;
import android.content.Intent;

public class ModuleSwitcher {

   private Context packageContext;
   public FileManager fileManager;

    public ModuleSwitcher(Context packageContext) {
        this.packageContext = packageContext;
        fileManager = new FileManager(packageContext);
    }

    public void showMenu(){
        Intent intent = new Intent(packageContext, MenuActivity.class);
        packageContext.startActivity(intent);

    }

    public void showGame() {
        Intent intent = new Intent(packageContext, MainFieldActivity.class);
        packageContext.startActivity(intent);
    }

    public void showSpinner(){
       MainFieldActivity.instance.showSpinner();
    }

    public void hideSpinner(){
        MainFieldActivity.instance.hideSpinner();
    }
}
