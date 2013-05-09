package se.forsum.kalaha;

import android.os.Handler;
import android.os.Looper;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicBoolean;

class Game implements Serializable {
    private transient IGameStatus status;

    public Game() {
    }

    public void setIGameStatus(IGameStatus s){
        status = s;
    }

    public void play() {
        updateStatus(difficulty);
    }

    public void InitializeGameBoard() {
        pos.initializePosition();
            status.setStatusText("Game starts!");
    }


    private void endGame() {
        status.setStatusText("Final position");

        if(pos.opponentWins())
            status.setStatusText("Computer won!");
        else if(pos.playerWins())
            status.setStatusText("You won!");
        else if(pos.itIsADraw())
            status.setStatusText("it is a draw!");

        status.showPosition(pos);
    }

    private void updateStatus(int nDifficulty) {
        while(!pos.opponentWins()
                && !pos.playerWins()
                && !pos.itIsADraw())
        {
            status.showPosition(pos);

            if(pos.playersTurn)
            {
                status.setStatusText("Click on pit to distribute");
                return;
            }
            else
            {
                status.setStatusText("Computer desperately tries to figure something out");
                return;
            }

        }

        endGame();
    }

    public void doNextComputerMove() {
        computerMoving.set(true);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                updateStatus(difficulty);
            }
        });
        Move move;
        move = pos.findMove(difficulty);
        pos = move.perform(pos);

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                updateStatus(difficulty);
            }
        });
        computerMoving.set(false);
    }

    public boolean doNextPlayerMove(int pitNumber) {

        if(pitNumber-1 < 6
           && pitNumber-1 >= 0
           && pos.pits[pitNumber-1] != 0) {

            Move move;
            move = new Move();
            move.addPartMove(pitNumber - 1);
            pos = move.perform(pos);
            updateStatus(difficulty);
            return true;
        }


        return false;
    }

    public void setDifficulty(int n) {
        difficulty = n;
    }

    private int difficulty = 2;

    private Pos pos = new Pos();

    private AtomicBoolean computerMoving = new AtomicBoolean(false);

    public boolean getPlayersTurn() {
        if(!computerMoving.get())
            return pos.getPlayersTurn();

        return false;
    }
}

