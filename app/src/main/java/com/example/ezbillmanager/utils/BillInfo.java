package com.example.ezbillmanager.utils;

public class BillInfo implements Comparable<BillInfo>
{
    private int _usrID;
    public String _time;
    private String _type;
    private int _money;
    
    public BillInfo(int usrID, String time, String type, int money)
    {
        _money = money;
        _time = time;
        _type = type;
        _usrID = usrID;
    }
    public BillInfo()
    {

    }
    public int getMoney() {
        return _money;
    }

    public int getUsrID() {
        return _usrID;
    }

    public String getTime() {
        return _time;
    }

    public String getType() {
        return _type;
    }

    public void setMoney(int money) {
        this._money = money;
    }

    public void setTime(int year,int month,int day) {
        if(year<=0||month<=0||month>12||day<=0||day>31)
            return;
        this._time = String.valueOf(year)+"."+String.valueOf(month)+"."+String.valueOf(day);
    }
    public void setTime(String time)
    {
        this._time = time;
    }
    public void setType(String type) {
        this._type = type;
    }

    public void set_usrID(int _usrID) {
        this._usrID = _usrID;
    }

    public int compareTo(BillInfo o)
    {
        return _type.compareTo(o._type);
    }
}
