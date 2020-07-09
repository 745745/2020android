package com.example.ezbillmanager.utils;

public class userInfo
{
    public int userid;
    private String password;
    private static userInfo instance=new userInfo();
    private userInfo()
    {
        userid=0;
        password=null;
    }

    public void setId(int userid)
    {
        this.userid=userid;
    }

    public void setPassword(String password)
    {
        this.password=new String(password);
    }

    public static userInfo getInstance()
    {
        if(instance==null)
            instance=new userInfo();
        return instance;
    }

    public  boolean isNew()
    {
        if(userid==0&&password==null)
        {
            return true;
        }
        else return false;
    }
}
