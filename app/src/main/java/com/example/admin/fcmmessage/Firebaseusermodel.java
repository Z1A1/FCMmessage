package com.example.admin.fcmmessage;

/**
 * Created by admin on 2/1/2018.
 */

class Firebaseusermodel {
    String DeviceId;
    String deviceToken;
    private String userName;

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }
}
