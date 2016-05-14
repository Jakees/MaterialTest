package com.example.titan.pojo;

/**
 * Created by TITAN on 2015/6/20.
 */
public class UserStatue {

    public static final int LOGINED = 1;
    public static final int UNLOGINED = -1;

    private static UserStatue instance;
    public static int USER_STATE = -1;

    private boolean isUserInfoSaved = false;

    public boolean isUserInfoSaved() {
        return isUserInfoSaved;
    }

    public void setUserInfoSaved(boolean isUserInfoSaved) {
        this.isUserInfoSaved = isUserInfoSaved;
    }

    private String userName;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private UserStatue(){}

    public static synchronized UserStatue getInstance() {
        if (instance == null) {
            instance = new UserStatue();
        }
        return instance;
    }

    public int getUSER_STATE() {
        return USER_STATE;
    }

    public boolean isUserLogined(){
        boolean state = false;

        switch (USER_STATE){
            case LOGINED:
                state = true;
                break;
            case UNLOGINED:
                state = false;
                break;
        }
        return state;
    }

    public void setUSER_STATE(int USER_STATE) {
        this.USER_STATE = USER_STATE;
    }

}