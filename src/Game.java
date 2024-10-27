import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;

public class Game
{
    private Stage stage;
    private Scene scene;
    private GridPane root;

    //2D array to represent buttons in the grid
    private Button[][] board;
    private boolean gameWon;
    private boolean playerOne;
    private boolean isDraw;

    public boolean isPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(boolean playerOne) {
        this.playerOne = playerOne;
    }

    public Game()
    {

    }

    public void initialize()
    {
        gameWon = false;
        playerOne = true;;
        isDraw = false;

        board = new Button[3][3];

        root = new GridPane();
        root.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        root.setPrefSize(600, 700);

        initBoard();

        scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("TicTacToe");
        stage.show();
    }

    public void update()
    {
        if(gameWon)
        {
            System.out.println("Game Over");
        }

        String move = computerMakesMove();

        processMessage(move);

        gameWon = checkForWin();

        if(gameWon && playerOne)
        {
            System.out.println("Player One won the game!");
            resetGame();
        }
        else if(gameWon && !playerOne)
        {
            System.out.println("Player Two won the game!");
            resetGame();
        }

        isDraw = checkForDraw();

        if(isDraw)
        {
            System.out.println("The game is a draw!");
            resetGame();
        }
    }

    public void render()
    {

    }

    public void processMessage(String message)
    {
        if(playerOne)
        {
            switch(message)
            {
                case "A0":
                    board[0][0].setText("X");
                    break;
                case "A1":
                    board[0][1].setText("X");
                    break;
                case "A2":
                    board[0][2].setText("X");
                    break;
                case "B0":
                    board[1][0].setText("X");
                    break;
                case "B1":
                    board[1][1].setText("X");
                    break;
                case "B2":
                    board[1][2].setText("X");
                    break;
                case "C0":
                    board[2][0].setText("X");
                    break;
                case "C1":
                    board[2][1].setText("X");
                    break;
                case "C2":
                    board[2][2].setText("X");
                    break;
            }
        }
        else
        {
            switch(message)
            {
                case "A0":
                    board[0][0].setText("O");
                    break;
                case "A1":
                    board[0][1].setText("O");
                    break;
                case "A2":
                    board[0][2].setText("O");
                    break;
                case "B0":
                    board[1][0].setText("O");
                    break;
                case "B1":
                    board[1][1].setText("O");
                    break;
                case "B2":
                    board[1][2].setText("O");
                    break;
                case "C0":
                    board[2][0].setText("O");
                    break;
                case "C1":
                    board[2][1].setText("O");
                    break;
                case "C2":
                    board[2][2].setText("O");
                    break;
            }
        }

        playerOne = !playerOne;
    }

    public void resetGame()
    {
        gameWon = false;
        isDraw = false;
        playerOne = true;

        for(int row = 0; row < 3; row++)
        {
            for(int col = 0; col < 3; col++)
            {
                board[row][col].setText("");
            }
        }
    }

    public boolean checkForDraw()
    {
        for(int row = 0; row < 3; row++)
        {
            for(int col = 0; col < 3; col++)
            {
                if(board[row][col].getText().equals(""))
                {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean checkForWin()
    {
        boolean hasWon = false;

        if(playerOne)
        {
            for(int i = 0; i < 3; i++)
            {
                if(board[i][0].getText().equals("X") && board[i][1].getText().equals("X") && board[i][2].getText().equals("X")
                || board[0][i].getText().equals("X") && board[1][i].getText().equals("X") && board[2][i].getText().equals("X"))
                {
                    hasWon = true;
                }
            }

            if(board[0][0].getText().equals("X") && board[1][1].getText().equals("X") && board[2][2].getText().equals("X"))
            {
                hasWon = true;
            }
            if(board[0][2].getText().equals("X") && board[1][1].getText().equals("X") && board[2][0].getText().equals("X"))
            {
                hasWon = true;
            }
        }
        else
        {
            for(int i = 0; i < 3; i++)
            {
                if(board[i][0].getText().equals("O") && board[i][1].getText().equals("O") && board[i][2].getText().equals("O")
                        || board[0][i].getText().equals("O") && board[1][i].getText().equals("O") && board[2][i].getText().equals("O"))
                {
                    hasWon = true;
                }
            }

            if(board[0][0].getText().equals("O") && board[1][1].getText().equals("O") && board[2][2].getText().equals("O"))
            {
                hasWon = true;
            }
            if(board[0][2].getText().equals("O") && board[1][1].getText().equals("O") && board[2][0].getText().equals("O"))
            {
                hasWon = true;
            }
        }

        return hasWon;
    }

    public String computerMakesMove()
    {
        String move = "";
        boolean hasFoundMove = false;
        Random random = new Random();
        int row = -1;
        int col = -1;

        do
        {
            row = random.nextInt(3);
            col = random.nextInt(3);

            if(board[row][col].getText().isEmpty())
            {
                if(playerOne)
                {
                    board[row][col].setText("X");
                }
                else
                {
                    board[row][col].setText("O");
                }

                hasFoundMove = true;
            }

        }while(!hasFoundMove);

        switch(row)
        {
            case 0:
                move = move + "A";
                break;
            case 1:
                move = move + "B";
                break;
            case 2:
                move = move + "C";
                break;
        }

        move = move + col;

        return move;
    }

    public String[][] getBoardState()
    {
        String[][] boardState = new String[3][3];

        for(int row = 0; row < 3; row++)
        {
            for(int col = 0; col < 3; col++)
            {
                boardState[row][col] = board[row][col].getText();
            }
        }

        return boardState;
    }

    public Stage getStage()
    {
        return stage;
    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    public Scene getScene()
    {
        return scene;
    }

    public void setScene(Scene scene)
    {
        this.scene = scene;
    }

    private void initBoard()
    {
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 3; col++) {
                Button button = new Button("");
                button.setMinSize(100, 100); // Set button size
                button.setStyle("-fx-font-size: 24px;");

                final int r = row;
                final int c = col;

                // Add action for each button press (for testing)
                button.setOnAction(event -> handleMove(r, c));

                // Add button to grid and store in array
                root.add(button, col, row);
                board[row][col] = button;
            }
        }
    }

    private void handleMove(int row, int col)
    {
        board[row][col].setText("X");
    }
}
