import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient
{

    private PrintWriter out;
    private BufferedReader in;

    public void initialize(String host, int port)
    {
        System.out.println("Client started, connecting...");

        try
        {
            Socket socket = new Socket(host, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Connected to Server!");
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void sendMessageToServer(String message) throws IOException
    {
        if(out != null)
        {
            out.println(message);
            System.out.println("Sent to server: " + message);
        }
        else
        {
            System.out.println("Output stream is not init");
        }
    }

    public String processMessage() throws IOException
    {
        String messageFromServer = null;

       if(in != null)
       {
           try
           {
               messageFromServer = in.readLine();

               if(messageFromServer != null)
               {
                   System.out.println("Client received message from server: " + messageFromServer);
               }
           }
           catch (IOException e)
           {
               System.out.println("Client error: " + e);
           }
       }

        return messageFromServer;
    }
}
