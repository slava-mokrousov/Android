package minesweeper.mokr.slava.minesweeper;

import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


import minesweeper.mokr.slava.minesweeper.gameLogic.Board;

public class GameActivity extends AppCompatActivity implements View.OnTouchListener {


    private final Board board = NewGameActivity.getBoard();
    private final static int shift = 2;
    private final static int strokeWidth = 2;
    private final static int clickTime = 30;
    private final static int flagTime = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DrawView view = new DrawView(this);
        view.setOnTouchListener(this);
        setContentView(view);
    }

    class DrawView extends View {

        final Paint p;

        public DrawView(Context context) {
            super(context);
            p = new Paint();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.WHITE);
            int width = board.getWidth();
            int height = board.getHeight();
            int cellSize;
            int canvasHeight = canvas.getHeight();
            int canvasWidth = canvas.getWidth();
            if (canvasHeight / height > canvasWidth / width) {
                cellSize = canvasWidth / width;
            } else {
                cellSize = canvasHeight / height;
            }
            p.setColor(Color.BLACK);
            p.setStrokeWidth(strokeWidth);
            int center = cellSize / 5;
            for (int x = 0; x <= width * cellSize; x += cellSize) {
                canvas.drawLine(x, 0, x, height * cellSize, p);
            }
            for (int y = 0; y <= height * cellSize; y += cellSize) {
                canvas.drawLine(0, y, width * cellSize, y, p);
            }
            for (int column = 0; column < board.getWidth(); column++) {
                for (int row = 0; row < board.getHeight(); row++) {
                    p.setTextSize(cellSize);
                    if (board.getCell(column, row).isClicked()) {
                        if (board.getCell(column, row).isBomb()) {
                            Drawable mine = getDrawable(R.drawable.mine);
                            if (mine != null) {
                                mine.setBounds(cellSize * column, row * cellSize, (column + 1) * cellSize, (row + 1) * cellSize);
                                mine.draw(canvas);
                            }
                        } else {
                            int number = board.getValue(column, row);
                            if (number > 0) {
                                p.setColor(Color.BLUE);
                                canvas.drawText("" + number, column * cellSize + center, (row + 1) * cellSize - center, p);
                            } else {
                                p.setColor(Color.GRAY);
                                canvas.drawRect(column * cellSize + shift, row * cellSize + shift, (column + 1) * cellSize - shift, (row + 1) * cellSize - shift, p);
                            }
                        }
                    }
                    if (board.getCell(column, row).isFlagged()) {
                        Drawable flag = getDrawable(R.drawable.flag);
                        if (flag != null) {
                            flag.setBounds(cellSize * column, row * cellSize, (column + 1) * cellSize, (row + 1) * cellSize);
                            flag.draw(canvas);
                        }
                    }
                }
            }
            if (board.checkEnd() == 1) Toast.makeText(GameActivity.this, R.string.win, Toast.LENGTH_LONG).show();
            if (board.checkEnd() == -1) Toast.makeText(GameActivity.this, R.string.lose, Toast.LENGTH_LONG).show();

        }
    }




    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (board.checkEnd() == 0) {
            float x, y;
            int width = board.getWidth();
            int height = board.getHeight();
            int cellSize;
            int canvasHeight = v.getHeight();
            int canvasWidth = v.getWidth();
            if (canvasHeight / height > canvasWidth / width) {
                cellSize = canvasWidth / width;
            } else {
                cellSize = canvasHeight / height;
            }
            long start = event.getEventTime();
            long end = event.getDownTime();
            long eventTime = start - end;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                x = event.getX();
                y = event.getY();

                int column = (int) (x / cellSize);
                int row = (int) (y / cellSize);

                if (column > (board.getWidth() - 1) || row > (board.getHeight() - 1)) {
                    Toast.makeText(GameActivity.this, R.string.miss_click, Toast.LENGTH_SHORT).show();
                } else {
                    if (eventTime > flagTime) {
                        if (!board.getCell(column, row).isClicked()) {
                            board.getCell(column, row).cellFlagged();
                            v.invalidate();
                        }
                    }
                    if (eventTime > clickTime && eventTime <= flagTime) {
                        if (!board.getCell(column, row).isFlagged()) {
                            board.getCell(column, row).cellClicked();
                            board.findZeroes(column, row);
                            if (board.getCell(column, row).isBomb()) board.showBombs();
                            v.invalidate();
                        }
                    }
                }
            }
        }
        return true;
    }
}
