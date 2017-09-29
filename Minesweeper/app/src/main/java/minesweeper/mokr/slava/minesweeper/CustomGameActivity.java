package minesweeper.mokr.slava.minesweeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import minesweeper.mokr.slava.minesweeper.gameLogic.Board;


public class CustomGameActivity extends AppCompatActivity  {

    private final static String GAME_KEY = "GAME_KEY";

    private int numColumns;
    private int numRows;
    private int numBombs;
    private SeekBar bombs;
    private TextView textColumns;
    private TextView textRows;
    private TextView textBombs;
    private final static int maxBoardSize = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_game);

        SeekBar columns = (SeekBar) findViewById(R.id.bar_columns);
        columns.setMax(maxBoardSize);
        columns.setOnSeekBarChangeListener(seekBarChangeListener);
        textColumns = (TextView)findViewById(R.id.num_columns);
        textColumns.setText(String.valueOf(columns.getProgress()));

        SeekBar rows = (SeekBar) findViewById(R.id.bar_rows);
        rows.setMax(maxBoardSize);
        rows.setOnSeekBarChangeListener(seekBarChangeListener);
        textRows = (TextView)findViewById(R.id.num_rows);
        textRows.setText(String.valueOf(rows.getProgress()));


        bombs = (SeekBar)findViewById(R.id.bar_bombs);
        bombs.setMax(columns.getProgress() * rows.getProgress());
        bombs.setOnSeekBarChangeListener(seekBarChangeListener);
        textBombs = (TextView)findViewById(R.id.num_bombs);
        textBombs.setText(String.valueOf(bombs.getProgress()));

        Button startGame = (Button) findViewById(R.id.start_custom);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {bnClicked(v);
            }
        };
        startGame.setOnClickListener(onClickListener);

    }

    private void bnClicked(View button) {
        switch (button.getId()) {
            case R.id.start_custom:
                if (numColumns == 0 || numRows == 0 || numBombs == 0 || numColumns*numRows == numBombs) Toast.makeText(CustomGameActivity.this, R.string.wrong_settings, Toast.LENGTH_SHORT).show();
                else {
                    Intent startGame = new Intent(this, GameActivity.class);
                    Board board = new Board(numColumns, numRows);
                    board.createGame(numBombs);
                    startGame.putExtra(GAME_KEY, board);
                    startActivity(startGame);
                }
                break;
            default:
                break;
        }
    }

    private final SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()) {
                case R.id.bar_columns:
                    numColumns = seekBar.getProgress();
                    textColumns.setText(String.valueOf(numColumns));
                    bombs.setMax(numColumns * numRows);
                    break;
                case R.id.bar_rows:
                    numRows = seekBar.getProgress();
                    textRows.setText(String.valueOf(numRows));
                    bombs.setMax(numColumns * numRows);
                    break;
                case R.id.bar_bombs:
                    numBombs = seekBar.getProgress();
                    textBombs.setText(String.valueOf(numBombs));
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

}

