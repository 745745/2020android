import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args)
    {
        serverSocket serversocket=new serverSocket();
        serversocket.run();
    }


}

class serverSocket extends Thread
{
    private ServerSocket ss;
    private Socket socket;

    public serverSocket()
    {
        try
        {
            ss=new ServerSocket(12345);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true)
        {
            try {
                socket=ss.accept();
                System.out.println("已连接到");
                DataInputStream dis=new DataInputStream(socket.getInputStream());
                DataOutputStream dos=new DataOutputStream(socket.getOutputStream());
                while(true)
                {
                    String x=dis.readUTF();
                    String a=analysis.getResult(x);
                    dos.writeUTF(a);
                    int y=dis.read();
                    if(y==-1)
                    {
                        socket.close();
                        break;
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}