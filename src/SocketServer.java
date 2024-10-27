import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketServer
{
    ServerSocket serverSocket;
    List<Socket> clients = new ArrayList<>();
    private PrintWriter out;
    private BufferedReader in;

    public void initialize(int port) throws IOException
    {
        try
        {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started, waiting for connection");

            Socket clientSocket = serverSocket.accept();
            clients.add(clientSocket);
            System.out.println("Client connected");

            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (serverSocket != null && !serverSocket.isClosed())
            {
                serverSocket.close();
            }
        }

    }

    public void sendMessageToClient(String message) throws IOException
    {
        if(out != null)
        {
            out.println(message);
            System.out.println("Sent to client: " + message);
        }
        else
        {
            System.out.println("Output stream is not init");
        }

    }

    public String processMessage() throws IOException
    {
        String messageFromClient = null;

        if(in != null)
        {
            try
            {
                messageFromClient = in.readLine();
                if(messageFromClient != null)
                {
                    System.out.println("Server received message from client: " + messageFromClient);
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return messageFromClient;
    }
}
