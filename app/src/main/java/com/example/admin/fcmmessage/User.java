package com.example.admin.fcmmessage;

import android.content.SharedPreferences;

/**
 * Created by admin on 1/31/2018.
 */

class User {
    private static final User user=new User();
    public static User getInstance() {
        return user;
    }
    public String name="jhon";
    public  String deviceId="";
    public  String deviceToken="";

    public static  final String  apppreference="FCMmessage";
    public static final String Key="key";
    public  static final String Name="nameKey";
    public  static final String DeviceId="deviceIdKey";
    public  static final String DeviceToken="deviceTokenKey";

    public SharedPreferences sharedprefernces;




    private User(){}




    public boolean login(Firebaseusermodel firebaseuserModel) {
        Object deviceId=firebaseuserModel.getDeviceId();

        return false;
    }

    public void savefirebaseKey(String key) {
    }
}
