public class analysis {
    public static String getResult(String request)
    {
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
                    mySQLConnect.regist(Integer.parseInt(para[2]),para[3]);
                }
                else
                {
                    if(para.length<6)
                        return x;
                    billinfo bi=new billinfo();
                    bi.id=Integer.parseInt(para[2]);
                    bi.time=para[3];
                    bi.type=para[4];
                    bi.money=Integer.parseInt(para[5]);
                    mySQLConnect.addBill(bi);

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
                    mySQLConnect.deleteUser(Integer.parseInt(para[2]),para[3]);
                }
                else
                {
                    if(para.length<6)
                        return x;
                    billinfo bi=new billinfo();
                    bi.id=Integer.parseInt(para[2]);
                    bi.time=para[3];
                    bi.type=para[4];
                    bi.money=Integer.parseInt(para[5]);
                    mySQLConnect.deleteBill(bi);
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
                    mySQLConnect.changePassword(Integer.parseInt(para[2]),para[3],para[4]);
                }
                else
                {
                    if(para.length<6)
                        return x;
                    billinfo bi=new billinfo();
                    bi.id=Integer.parseInt(para[2]);
                    bi.time=para[3];
                    bi.type=para[4];
                    bi.money=Integer.parseInt(para[5]);
                    mySQLConnect.changeBill(bi);
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
