import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class GameClient extends Application
{
    private Game game;
    private SocketClient socketClient;
    private static final long MOVE_DELAY = 1_000_000_000; //1 second in nanoseconds
    private long lastMoveTime;
    private String messageToServer;

    private boolean myTurn = false;

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
        game.setPlayerOne(false);

        socketClient = new SocketClient();
        socketClient.initialize("localhost", 6667);

        lastMoveTime = 0;

        messageToServer = "";

        AnimationTimer gameLoop = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                String messageFromServer;

                if(now - lastMoveTime > MOVE_DELAY)
                {
                    if(!myTurn)
                    {
                        try
                        {
                            messageFromServer = socketClient.processMessage();
                            game.processMessage(messageFromServer);

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
                        messageToServer = game.computerMakesMove();

                        try
                        {
                            socketClient.sendMessageToServer(messageToServer);
                            game.processMessage(messageToServer);

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
