package com.example.ezbillmanager.utils;

import java.net.*;
import java.io.*;

/**
 * 这个类基于单例,请用getInstance方法获取实例
 */
public class NetManager
{
    private volatile static NetManager _netManager;
    private final String _serverName = "118.24.79.108";
    private final String ACTION_ERROR = "ERROR";
    private final String ACTION_SUCCESS = "1";
    private final String ACTION_FAIL = "0";
    private final int _port = 14500;

    private NetManager() {};

    public static NetManager getInstance()
    {
        if(_netManager == null)
        {
            synchronized (NetManager.class)
            {
                if(_netManager == null)
                {
                    _netManager = new NetManager();
                }
            }
        }
        return _netManager;
    }
    private String _sendSocket(String param)
    {
        try
        {
            Socket client = new Socket(_serverName,_port);
            while(!client.isConnected())
            {

            }
            OutputStream outputStream = client.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeUTF(param);

            InputStream inputStream = client.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            String res = dataInputStream.readUTF();
            client.close();
            return res;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return ACTION_ERROR;
        }
    }

    public int usrLogin(int usrID, String passwd)
    {
        String param = "4 1 "+String.valueOf(usrID)+" "+passwd;
        String result = _sendSocket(param);
        if(result.equals(ACTION_SUCCESS))
        {
            return 1;
        }
        else if(result.equals(ACTION_FAIL))
        {
            return 0;
        }
        else if(result.equals(ACTION_ERROR))
        {
            return -1;
        }
        else
        {
            return -2;
        }
    }

    public int usrRegister(int usrID, String passwd)
    {
        String param = "1 1 "+String.valueOf(usrID)+" "+passwd;
        String result = _sendSocket(param);
        if(result.equals(ACTION_SUCCESS))
        {
            return 1;
        }
        else if(result.equals(ACTION_FAIL))
        {
            return 0;
        }
        else if(result.equals(ACTION_ERROR))
        {
            return -1;
        }
        else
        {
            return -2;
        }
    }

    public int usrDelete(int usrID, String passwd)
    {
        String param = "2 1 "+String.valueOf(usrID)+" "+passwd;
        String result = _sendSocket(param);
        if(result.equals(ACTION_SUCCESS))
        {
            return 1;
        }
        else if(result.equals(ACTION_FAIL))
        {
            return 0;
        }
        else if(result.equals(ACTION_ERROR))
        {
            return -1;
        }
        else
        {
            return -2;
        }
    }

    public int usrChangePasswd(int usrID, String oldPasswd, String newPasswd)
    {
        String param = "3 1 "+String.valueOf(usrID)+" "+oldPasswd+" "+newPasswd;
        String result = _sendSocket(param);
        if(result.equals(ACTION_SUCCESS))
        {
            return 1;
        }
        else if(result.equals(ACTION_FAIL))
        {
            return 0;
        }
        else if(result.equals(ACTION_ERROR))
        {
            return -1;
        }
        else
        {
            return -2;
        }
    }

    /**
     * 增加一笔账单
     * @param time 格式 年.月.日 比如2020.7.8
     */
    public int addBill(int usrID, String time, String type, int money)
    {
        String param = "1 2 "+String.valueOf(usrID)+" "+time+" "+type+" "+String.valueOf(money);
        String result = _sendSocket(param);
        if(result.equals(ACTION_SUCCESS))
        {
            return 1;
        }
        else if(result.equals(ACTION_FAIL))
        {
            return 0;
        }
        else if(result.equals(ACTION_ERROR))
        {
            return -1;
        }
        else
        {
            return -2;
        }
    }

    /**
     * 删除一笔账单
     * @param time 格式 年.月.日 比如2020.7.8
     */
    public int deleteBill(int usrID, String time, String type, int money)
    {
        String param = "2 2 "+String.valueOf(usrID)+" "+time+" "+type+" "+String.valueOf(money);
        String result = _sendSocket(param);
        if(result.equals(ACTION_SUCCESS))
        {
            return 1;
        }
        else if(result.equals(ACTION_FAIL))
        {
            return 0;
        }
        else if(result.equals(ACTION_ERROR))
        {
            return -1;
        }
        else
        {
            return -2;
        }
    }

    /**
     *
     * @param usrID 旧的等待更改用户ID
     * @param time 格式 年.月.日 比如2020.7.8
     * @param usrID1 新的用户ID
     * @param time1 格式 年.月.日 比如2020.7.8
     * @return
     */
    public int modifyBill(int usrID, String time, String type, int money,
                            int usrID1, String time1, String type1, int money1)
    {
        String param = "3 2 "+String.valueOf(usrID)+" "+time+" "+type+" "+String.valueOf(money)
                +" "+String.valueOf(usrID1)+" "+time1+" "+type1+" "+String.valueOf(money1);
        String result = _sendSocket(param);
        if(result.equals(ACTION_SUCCESS))
        {
            return 1;
        }
        else if(result.equals(ACTION_FAIL))
        {
            return 0;
        }
        else if(result.equals(ACTION_ERROR))
        {
            return -1;
        }
        else
        {
            return -2;
        }
    }

    /** 查找一笔账单
     *
     * @param time 格式 年.月.日 比如2020.7.8
     * @return
     */
    public BillInfo findBill(int usrID, String time, String type, int money)
    {
        String param = "4 2 "+usrID+" "+time+" "+type+" "+money;
        String result = _sendSocket(param);
        String[] strArr = result.split(" ");
        if(strArr.length!=4)
        {
            //log一下出错
            return null;
        }
        else
        {
            BillInfo billInfo = new BillInfo(Integer.parseInt(strArr[0]),strArr[1],
                    strArr[2],Integer.parseInt(strArr[3]));
            return billInfo;
        }
    }

    /** 通过用户ID查找
     *
     * @param usrID 用户ID
     */
    public BillInfo[] findBillByUsrID(int usrID)
    {
        String param = "4 2 "+usrID;
        String result = _sendSocket(param);
        if(result.charAt(0)=='0')return null;
        String[] strArr = result.split(" ");
        System.out.println(result);
        System.out.println(strArr.length);
        if((strArr.length-1)%4!=0)
        {
            //log出错
            return null;
        }
        else
        {
            int length = (strArr.length-1)/4;
            System.out.println(strArr[0]);
            BillInfo[] billArr = new BillInfo[length];
            for(int i=0;i<length;++i)
            {
                billArr[i] = new BillInfo();
                billArr[i].set_usrID(Integer.parseInt(strArr[4*i+1]));
                billArr[i].setTime(strArr[4*i+2]);
                billArr[i].setType(strArr[4*i+3]);
                billArr[i].setMoney(Integer.parseInt(strArr[4*i+4]));
            }
            return billArr;
        }
    }

    /** 查找某一日的所有账单
     *
     * @param usrID 用户ID
     * @param time 格式 年.月.日 比如2020.7.8
     */
    public BillInfo[] findBillByTime(int usrID, String time)
    {
        String param = "4 2 "+usrID+" "+time;
        String result = _sendSocket(param);
        String[] strArr = result.split(" ");
        System.out.println(result);
        System.out.println(strArr.length);
        if((strArr.length-1)%4!=0)
        {
            //log出错
            return null;
        }
        else
        {
            int length = (strArr.length-1)/4;
            System.out.println(strArr[1]);
            BillInfo[] billArr = new BillInfo[length];
            for(int i=0;i<length;++i)
            {
                billArr[i] = new BillInfo();
                billArr[i].set_usrID(Integer.parseInt(strArr[4*i+1]));
                billArr[i].setTime(strArr[4*i+2]);
                billArr[i].setType(strArr[4*i+3]);
                billArr[i].setMoney(Integer.parseInt(strArr[4*i+4]));
            }
            return billArr;
        }
    }

    /** 查找两天之间的账单
     *
     * @param usrID 用户ID
     * @param startTime 格式 年.月.日 比如2020.7.8
     * @param endTime 同上
     */
    public BillInfo[] findBillByTimeval(int usrID, String startTime, String endTime)
    {
        String param = "4 2 "+usrID+" "+startTime+" "+endTime;

        String result = _sendSocket(param);
        String[] strArr = result.split(" ");
        if((strArr.length-1)%4!=0)
        {
            //log出错
            return null;
        }
        else
        {
            int length = (strArr.length-1)/4;
            BillInfo[] billArr = new BillInfo[length];
            for(int i=0;i<length;++i)
            {
                billArr[i] = new BillInfo();
                billArr[i].set_usrID(Integer.parseInt(strArr[4*i+1]));
                billArr[i].setTime(strArr[4*i+2]);
                billArr[i].setType(strArr[4*i+3]);
                billArr[i].setMoney(Integer.parseInt(strArr[4*i+4]));
            }
            return billArr;
        }
    }


}
