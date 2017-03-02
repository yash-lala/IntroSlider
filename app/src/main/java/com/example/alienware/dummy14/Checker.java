//class for intro slider and login(to check if usser has already logged in ,in a past session)
package com.example.alienware.dummy14;

/**
 * Created by Alienware on 29-05-2016.
 */

import android.content.Context;
import android.content.SharedPreferences;

//this class is to check if app is opened for the first time by using SharedPreferences
public class Checker {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    //shared preference mode
    private static final int mode =0;
    //shared preference file name
    private static final String name ="welcome";
    private static final String IsFirstTimeLaunch = "IsFirstTimeLaunch";

    public Checker(Context c){
        this.context = c;
        sharedPreferences = context.getSharedPreferences(name,mode);
        editor = sharedPreferences.edit();
    }

    public void setFirst(boolean isfirsttime){
        editor.putBoolean(IsFirstTimeLaunch,isfirsttime);
        editor.commit();
    }

    public boolean isFirst(){
        return sharedPreferences.getBoolean(IsFirstTimeLaunch,true);
    }
}
