public class analysis {
    public static String getResult(String request)
    {
        boolean a=false;
        String x="0";
        String []para=request.split(" ");
        MySQLConnect mySQLConnect=new MySQLConnect();
        switch (para[0])
        {
            //增
            case("1"):
            {
                if(para[1].equals("1"))
                {
                    if(para.length<4)
                        return x;
                    a=mySQLConnect.regist(Integer.parseInt(para[2]),para[3]);
                    if(a==false)
                        return x;
                }
                else
                {
                    if(para.length<6)
                        return x;
                    billinfo bi=new billinfo();
                    bi.userid=Integer.parseInt(para[2]);
                    bi.time=para[3];
                    bi.type=para[4];
                    bi.money=Integer.parseInt(para[5]);
                    a=mySQLConnect.addBill(bi);
                    if(a==false)
                        return x;
                }
                x="1";
                break;
            }
            //删
            case("2"):
            {
                if(para[1].equals("1"))
                {
                    if(para.length<4)
                        return x;
                    a=mySQLConnect.deleteUser(Integer.parseInt(para[2]),para[3]);
                    if(a==false)
                        return x;
                }
                else
                {
                    if(para.length<6)
                        return x;
                    billinfo bi=new billinfo();
                    bi.userid=Integer.parseInt(para[2]);
                    bi.time=para[3];
                    bi.type=para[4];
                    bi.money=Integer.parseInt(para[5]);
                    a=mySQLConnect.deleteBill(bi);
                    if(a==false)
                        return x;
                }
                x="1";
                break;
            }
            //改
            case("3"):
            {
                if(para[1].equals("1"))
                {
                    if(para.length<5)
                        return x;
                    a=mySQLConnect.changePassword(Integer.parseInt(para[2]),para[3],para[4]);
                    if(a==false)
                        return x;
                }
                else
                {
                    if(para.length<10)
                        return x;
                    billinfo bi=new billinfo();
                    billinfo bc =new billinfo();
                    bi.userid=Integer.parseInt(para[2]);
                    bi.time=para[3];
                    bi.type=para[4];
                    bi.money=Integer.parseInt(para[5]);
                    bc.userid=Integer.parseInt(para[6]);
                    bc.time=para[7];
                    bc.type=para[8];
                    bc.money=Integer.parseInt(para[9]);
                    a=mySQLConnect.changeBill(bi,bc);
                    if(a==false)
                        return x;
                }
                x="1";
                break;
            }

            //查
            case("4"):
            {
                if(para[1].equals("1"))
                {
                    if(para.length<4)
                        return x;
                    if (mySQLConnect.User(Integer.parseInt(para[2]), para[3]))
                        x="1";
                }
                else
                {
                    billinfo[] bill=null;
                    if(para.length==3)
                    {   //该id下的所有账单
                        bill=mySQLConnect.SelectBill(Integer.parseInt(para[2]));
                        if(bill==null)
                            return x;
                    }
                    else if(para.length==4)
                    {   //该id下某时间的所有账单
                        bill=mySQLConnect.SelectBill(Integer.parseInt(para[2]),para[3]);
                        if(bill==null)
                            return x;
                    }
                    else if(para.length==5)
                    {
                        //查找某时间段的账单
                        bill=mySQLConnect.SelectBill(Integer.parseInt(para[2]),para[3],para[4]);
                        if(bill==null)
                            return x;
                    }
                    else return x;
                    x="1 ";
                    for(int i=0;i<bill.length;i++)
                    {
                        x+=bill[i].userid+" ";
                        x+=bill[i].time+" ";
                        x+=bill[i].type+" ";
                        x+=bill[i].money+" ";
                    }
                }
                break;
            }
        }
    return x;
    }
}
