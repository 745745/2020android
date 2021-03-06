import java.sql.*;

public class MySQLConnect {

    static String JDBC_DRIVER="com.mysql.jdbc.Driver";
    static String DB_URL = "jdbc:mysql://localhost:3306/mysql";

    static String USER="root";
    static String Password="247284";

    private Connection cn;
    public ResultSet rs;
    //查找用户信息
    public boolean User(int id,String password)
    {
        String x="";
        try {
            PreparedStatement pps=cn.prepareStatement("select password from userinfo where id=?");
            pps.setInt(1,id);

            rs=pps.executeQuery();

            while(rs.next())
            {
                x = rs.getString("password");
            }
            rs.close();
            pps.close();
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        return password.equals(x);
    }
    //按时间和id查找账单
    public billinfo[] SelectBill(String time,int userid)
    {
        billinfo[] bill=null;
        try {
            PreparedStatement pps=cn.prepareStatement("select * from billinfo where time=? and userid=?");
            pps.setString(1,time);
            pps.setInt(2,userid);
            rs=pps.executeQuery();

            int i=0;
            while(rs.next())
            {
                i++;
            }
            rs.beforeFirst();
            bill=new billinfo[i];
            int j=0;
            while (rs.next())
            {
                bill[j]=new billinfo();
                bill[j].id=rs.getInt("id");
                bill[j].userid=rs.getInt("userid");
                bill[j].time=rs.getString("time");
                bill[j].type=rs.getString("type");
                bill[j].money=rs.getInt("money");
                j++;
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        return bill;
    }
    //查找该id下的所有账单
    public billinfo[] SelectBill(int userid)
    {
        billinfo[] bill=null;
        try {
            PreparedStatement pps=cn.prepareStatement("select * from billinfo where userid=?");
            pps.setInt(1,userid);
            rs=pps.executeQuery();

            int i=0;
            while(rs.next())
            {
                i++;
            }
            rs.beforeFirst();
            bill=new billinfo[i];
            int j=0;
            while (rs.next())
            {
                bill[j]=new billinfo();
                bill[j].id=rs.getInt("id");
                bill[j].userid=rs.getInt("userid");
                bill[j].time=rs.getString("time");
                bill[j].type=rs.getString("type");
                bill[j].money=rs.getInt("money");
                j++;
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        return bill;
    }
    //构造函数，先将数据库连接好
    public MySQLConnect()
    {
        try {

            Class.forName(JDBC_DRIVER);
            cn= DriverManager.getConnection(DB_URL,USER,Password);
        }
        catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    //用户注册
    public void regist(int id,String password)
    {
        try {
            PreparedStatement pps=cn.prepareStatement("insert into userinfo (id,password) values (?,?)");
            pps.setInt(1,id);
            pps.setString(2,password);
            pps.executeUpdate();
        }

        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    //添加账单信息
    public void addBill(billinfo bi)
    {
        try {
            PreparedStatement pps=cn.prepareStatement("insert into billinfo (userid,time,type,money) values (?,?,?,?)");
            pps.setInt(1,bi.userid);
            pps.setString(2,bi.time);
            pps.setString(3,bi.type);
            pps.setInt(4,bi.money);
            pps.executeUpdate();
        }

        catch (SQLException throwables) {
            throwables.printStackTrace();
        }



    }

    public void deleteBill(billinfo bill)
    {
        billinfo[] bi=SelectBill(bill.time, bill.userid);
        try {
            PreparedStatement pps=cn.prepareStatement("delete from billinfo where id=? and userid=? and time=? and type=? and money=?");
            pps.setInt(1,bi[0].id);
            pps.setInt(2,bi[0].userid);
            pps.setString(3,bi[0].time);
            pps.setString(4,bi[0].type);
            pps.setInt(5,bi[0].money);
            pps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


              //测试用例
    public static void main(String[] args)
    {
        MySQLConnect mySQLConnect=new MySQLConnect();

        billinfo bi=new billinfo();

        bi.userid=1;
        bi.time="2020.7.7";
        bi.type="吃饭";
        bi.money=1000;

        //mySQLConnect.addBill(bi);
        //mySQLConnect.deleteBill(bi);
        //mySQLConnect.regist(233,"247284");
        //billinfo[] bill=mySQLConnect.SelectBill(1);
        //for(int i=0;i<bill.length;i++)
        //    System.out.println(bill[i].id+" "+bill[i].type+" "+bill[i].time+" "+bill[i].money);

        //mySQLConnect.regist(1,"24728");
        //System.out.println(mySQLConnect.User(1,"247284"));


    }

}


class billinfo
{
    public int id;
    public int userid;
    public String time;
    public String type;
    public int money;

    public billinfo()
    {
        id=0;
        userid=0;
        time=null;
        type=null;
        money=0;
    }
}
