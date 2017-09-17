package minesweeper.mokr.slava.minesweeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        Button bnGame = (Button) findViewById(R.id.bn_game);
        Button bnAbout = (Button) findViewById(R.id.bn_about);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bnClicked(v);
            }
        };
        bnGame.setOnClickListener(onClickListener);
        bnAbout.setOnClickListener(onClickListener);
    }


    private void bnClicked(View button) {
        switch (button.getId()) {
            case R.id.bn_game:
                Intent a = new Intent(this, NewGameActivity.class);
                startActivity(a);
                break;
            case R.id.bn_about:
                Intent about = new Intent(this, AboutActivity.class);
                startActivity(about);
                break;
            default:
                break;
        }
    }
}
