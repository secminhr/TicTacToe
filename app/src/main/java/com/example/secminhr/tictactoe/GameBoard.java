package com.example.secminhr.tictactoe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;

public class GameBoard extends GridLayout implements View.OnClickListener {

    final private int length = 3;
    private float cellWidth = 0f;
    private Paint paint = new Paint();
    private OnFinishedListener listener;
    //In thi project, we'll use 1 represent O, and -1 represent X
    int currentPlayer = 1;


    private GameCell[][] cells = {{new GameCell(getContext()), new GameCell(getContext()), new GameCell(getContext())},
            {new GameCell(getContext()), new GameCell(getContext()), new GameCell(getContext())},
            {new GameCell(getContext()), new GameCell(getContext()), new GameCell(getContext())}};

    private int winner;

    interface OnFinishedListener {
        void finished(int winner);
    }

    public GameBoard(Context context) {
        super(context);
        setWillNotDraw(false);
        for(GameCell[] arr: cells) {
            for(GameCell cell: arr) {
                cell.setOnClickListener(this);
            }
        }
    }

    public GameBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        for(GameCell[] arr: cells) {
            for(GameCell cell: arr) {
                cell.setOnClickListener(this);
            }
        }
    }

    public GameBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        for(GameCell[] arr: cells) {
            for(GameCell cell: arr) {
                cell.setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View view) {
        GameCell cell = (GameCell)view;
        //the boolean of this determine whether we should change player
        if (cell.setInside(currentPlayer)) {
            currentPlayer *= -1;
            checkGameFinish();
        }
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        WindowManager manager = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        setMeasuredDimension(size.x, size.x);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cellWidth = getWidth()/length;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                GameCell cell = cells[i][j];
                cell.setWidth(this.cellWidth);
                Spec rowSpec = GridLayout.spec(i);
                Spec columnSpec = GridLayout.spec(j);
                LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
                params.height = (int)cellWidth;
                params.width = (int)cellWidth;
                addView(cell, params);
                measureChild(cell, MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        cellWidth = getWidth()/length;
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        float spaceToTop = 0;
        for (int i = 0; i <= 4; i++) {
            canvas.drawLine(0f, spaceToTop, getWidth(), spaceToTop, paint);
            spaceToTop += cellWidth;
        }
        float spaceToLeft = 0;
        for (int i = 0; i<= 4; i++) {
            canvas.drawLine(spaceToLeft, 0f, spaceToLeft, getWidth(), paint);
            spaceToLeft += cellWidth;
        }
    }

    public void checkGameFinish() {
        int sum = 0;
        if (checkColumn() || checkRow() || checkDiagonal() || checkFull()) {
            finishGame();
        }
    }

    private boolean checkColumn() {
        int sum = 0;
        for(int i = 0; i < 3; i++) {
            sum = cells[i][0].add(cells[i][1], cells[i][2]);
            if(sum == 3 || sum == -3) {
                winner = cells[i][0].getInside();
                return true;
            }
        }
        return false;
    }

    private boolean checkRow() {
        int sum = 0;
        for(int i = 0; i < 3; i++) {
            sum = cells[0][i].add(cells[1][i], cells[2][i]);
            if(sum == 3 || sum == -3) {
                winner = cells[0][i].getInside();
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonal() {
        int sum = 0;
        sum = cells[0][0].add(cells[1][1], cells[2][2]);
        if (sum == 3 || sum == -3) {
            winner = cells[0][0].getInside();
            return true;
        }
        sum = cells[0][2].add(cells[1][1], cells[2][0]);
        if(sum == 3 || sum == -3) {
            winner = cells[0][2].getInside();
            return true;
        }

        return false;
    }

    private boolean checkFull() {
        for(GameCell[] arr:cells) {
            for(GameCell cell: arr) {
                if(cell.getInside() != 1 && cell.getInside() != -1) {
                    return false;
                }
            }
        }
        return true;
    }

    private void finishGame() {
        this.listener.finished(winner);
        for(GameCell[] arr: cells) {
            for(GameCell cell: arr) {
                cell.setOnClickListener(null);
            }
        }
    }

    public void setOnFinishedListener(OnFinishedListener listener) {
        this.listener = listener;
    }
}
