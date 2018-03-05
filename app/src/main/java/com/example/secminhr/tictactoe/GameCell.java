package com.example.secminhr.tictactoe;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class GameCell extends android.support.v7.widget.AppCompatTextView implements View.OnClickListener {

    private float width;
    private int inside;
    GameBoard board;

    public GameCell(Context context, GameBoard board) {
        super(context);
        this.board = board;
        setOnClickListener(this);
    }

    public GameCell(Context context, @Nullable AttributeSet attrs, GameBoard board) {
        super(context, attrs);
        this.board = board;
        setOnClickListener(this);
    }

    public void setWidth(float width) {
        this.width = width;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int)width, (int)width);
    }

    @Override
    public void onClick(View view) {
        setInside(board.currentState);
        board.checkGameFinish();
    }

    public void setInside(int inside) {
        if(this.inside != 1 && this.inside != -1) {
            this.inside = inside;
            if(inside == 1) {
                setBackgroundResource(R.drawable.circle_drawable);
            } else if(inside == -1) {
                setBackgroundResource(R.drawable.ic_close_black_24dp);
            }
            board.currentState *= -1;
        }
    }

    public int getInside() {
        return this.inside;
    }
}
