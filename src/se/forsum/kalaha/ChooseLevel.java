package se.forsum.kalaha;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class ChooseLevel extends Activity implements View.OnClickListener {

    private SeekBar seekBarLevel;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooselevel);

        seekBarLevel = (SeekBar) findViewById(R.id.seekBarLevel);
        seekBarLevel.setMax(100);
        Button buttonPlay;
        buttonPlay = (Button) findViewById(R.id.buttonPlay);

        buttonPlay.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int playLevel = seekBarLevel.getProgress();

        playLevel = Math.max(2,(int)(((playLevel / 100.0) * 7.0)+0.5));

        Intent intent = new Intent(getApplicationContext(), PlayKalaha.class);
        intent.putExtra("playLevel", playLevel);

        this.startActivity(intent);

    }
}