package minesweeper.mokr.slava.minesweeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import minesweeper.mokr.slava.minesweeper.gameLogic.Board;


public class NewGameActivity extends AppCompatActivity {

    private static Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_game);
        Button bnEasy = (Button) findViewById(R.id.bn_easy);
        Button bnMedium = (Button) findViewById(R.id.bn_medium);
        Button bnHard = (Button) findViewById(R.id.bn_hard);
        Button bnCustom = (Button) findViewById(R.id.bn_custom);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bnClicked(v);
            }
        };
        bnEasy.setOnClickListener(onClickListener);
        bnMedium.setOnClickListener(onClickListener);
        bnHard.setOnClickListener(onClickListener);
        bnCustom.setOnClickListener(onClickListener);

    }

    private void bnClicked(View button) {
        switch (button.getId()) {
            case R.id.bn_easy:
                board = new Board(5, 9);
                board.createGame(6);
                Intent easy = new Intent(this, GameActivity.class);
                startActivity(easy);
                break;
            case R.id.bn_medium:
                board = new Board(6, 11);
                board.createGame(15);
                Intent medium = new Intent(this, GameActivity.class);
                startActivity(medium);
                break;
            case R.id.bn_hard:
                board = new Board(10, 18);
                board.createGame(40);
                Intent hard = new Intent(this, GameActivity.class);
                startActivity(hard);
                break;
            case R.id.bn_custom:
                Intent custom = new Intent(this, CustomGameActivity.class);
                startActivity(custom);
                break;
            default:
                break;
        }
    }

    public static Board getBoard() {
        return board;
    }

    public static void setBoard(int columns, int rows, int bombs) {
        board = new Board(columns, rows);
        board.createGame(bombs);
    }
}
