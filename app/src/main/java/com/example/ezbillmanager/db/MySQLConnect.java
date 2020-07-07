import java.sql.*;

public class MySQLConnect {

    static String JDBC_DRIVER="com.mysql.jdbc.Driver";
    static String DB_URL = "jdbc:mysql://localhost:3306/mysql";

    static String USER="root";
    static String Password="247284";

    private Connection cn;
    public ResultSet rs;

    public String SelectUser(int id)
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

        return x;
    }

    public billinfo SelectBill(int id)
    {
        billinfo bill=new billinfo();
        try {
            PreparedStatement pps=cn.prepareStatement("select * from billinfo where id=?");
            pps.setInt(1,id);

            rs=pps.executeQuery();

            while (rs.next())
            {
                bill.id=id;
                bill.time=rs.getString("time");
                bill.type=rs.getString("type");
                bill.money=rs.getInt("money");
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

    public void addBill(billinfo bi)
    {
        try {
            PreparedStatement pps=cn.prepareStatement("insert into billinfo (id,time,type,money) values (?,?,?,?)");
            pps.setInt(1,bi.id);
            pps.setString(2,bi.time);
            pps.setString(3,bi.type);
            pps.setInt(4,bi.money);
            pps.executeUpdate();
        }

        catch (SQLException throwables) {
            throwables.printStackTrace();
        }



    }

//              测试用例
//    public static void main(String[] args)
//    {
//        MySQLConnect mySQLConnect=new MySQLConnect();
//
//        billinfo bi=new billinfo();
//
//        bi.id=10;
//        bi.time="2020.7.5";
//        bi.type="吃饭啊";
//        bi.money=1000;
//
//        mySQLConnect.addBill(bi);
//        mySQLConnect.regist(233,"247284");
//        billinfo bill=mySQLConnect.SelectBill(1);
//        System.out.println(bill.id+" "+bill.type+" "+bill.time+" "+bill.money);
//        System.out.println(mySQLConnect.SelectUser(233));
//    }

}


class billinfo
{
    public int id;
    public String time;
    public String type;
    public int money;
}
