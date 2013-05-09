package se.forsum.kalaha;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Vector;
import android.view.View.OnClickListener;

public class PlayKalaha extends Activity implements IGameStatus,OnClickListener      {

    private Vector<Button> buttons = new Vector<Button>(14);
    private TextView statusText = null;
    private Game g = new Game();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        statusText = (TextView) this.findViewById(R.id.textViewStatus);

        buttons.add((Button) this.findViewById(R.id.pit0));
        buttons.add((Button) this.findViewById(R.id.pit1));
        buttons.add((Button) this.findViewById(R.id.pit2));
        buttons.add((Button) this.findViewById(R.id.pit3));
        buttons.add((Button) this.findViewById(R.id.pit4));
        buttons.add((Button) this.findViewById(R.id.pit5));
        buttons.add((Button) this.findViewById(R.id.pit6));
        buttons.add((Button) this.findViewById(R.id.pit7));
        buttons.add((Button) this.findViewById(R.id.pit8));
        buttons.add((Button) this.findViewById(R.id.pit9));
        buttons.add((Button) this.findViewById(R.id.pit10));
        buttons.add((Button) this.findViewById(R.id.pit11));
        buttons.add((Button) this.findViewById(R.id.pit12));
        buttons.add((Button) this.findViewById(R.id.pit13));

        for(Button b : buttons)
            b.setOnClickListener(this);

        if(savedInstanceState != null) {
            g = (Game) savedInstanceState.getSerializable("game");
            g.setIGameStatus(this);
        } else {
            g.setIGameStatus(this);

            int difficulty = getIntent().getIntExtra("playLevel", 2);
            g.setDifficulty(difficulty);
            g.InitializeGameBoard();
        }

        play();

    }

    @Override
    public void onClick(View view) {
        if(!g.getPlayersTurn()) {
            return;
        }

        int i = 0;
        for(;i<=13;i++) {
            if(buttons.get(i) == view)
                break;
        }

        g.doNextPlayerMove(i + 1);
        if(!g.getPlayersTurn()) {

        new Thread(new Runnable() {
            public void run() {
                g.doNextComputerMove();
            }
        }).start();
        }

    }

    void play() {
        g.play();
    }

// IGameStatus
    public void setStatusText(String text) {
        statusText.setText(text);
    }

    public void showPosition(Pos pos) {
        for(int i=0; i<=13 ;i++) {
            String numberOfBalls = "" + pos.noOfBallsInPit(i);
            buttons.get(i).setText(numberOfBalls);
        }
    }

    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        Serializable game = g;
        outState.putSerializable("game", g);
    }

    protected void onRestoreInstanceState (Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
    }
}
