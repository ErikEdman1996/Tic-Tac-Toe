import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class GameServer extends Application
{
    private Game game;
    private String[][] localBoard;
    private static final long MOVE_DELAY = 1_000_000_000; //1 second in nanoseconds
    private long lastMoveTime;
    private SocketServer socketServer;
    private String messageFromClient;
    private String messageToClient;

    private boolean myTurn = true;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        game = new Game();
        game.setStage(primaryStage);
        game.initialize();

        localBoard = new String[3][3];
        localBoard = game.getBoardState();

        lastMoveTime = 0;

        socketServer = new SocketServer();
        socketServer.initialize(6667);

        AnimationTimer gameLoop = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                if(now - lastMoveTime > MOVE_DELAY)
                {
                    String messageFromClient = "";

                    if(!myTurn)
                    {
                        try
                        {
                            messageFromClient = socketServer.processMessage();
                            game.processMessage(messageFromClient);

                            if(game.checkForWin())
                            {
                                System.out.println("Server Wins!");
                                game.resetGame();
                            }
                            else if(game.checkForDraw())
                            {
                                System.out.println("It's a draw!");
                                game.resetGame();
                            }
                            else
                            {
                                myTurn = true;
                            }
                        }
                        catch (IOException e)
                        {
                            throw new RuntimeException(e);
                        }
                    }
                    else
                    {
                        messageToClient = game.computerMakesMove();

                        try
                        {
                            socketServer.sendMessageToClient(messageToClient);
                            game.processMessage(messageToClient);

                            if(game.checkForWin())
                            {
                                System.out.println("Client Wins!");
                                game.resetGame();
                            }
                            else if(game.checkForDraw())
                            {
                                System.out.println("It's a draw!");
                                game.resetGame();
                            }
                            else
                            {
                                myTurn = false;
                            }
                        }
                        catch (IOException e)
                        {
                            throw new RuntimeException(e);
                        }

                    }
                    lastMoveTime = now;
                }
            }
        };

        gameLoop.start();
    }
}
