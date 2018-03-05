package com.example.secminhr.tictactoe;

import android.content.Context;

public class GameCell extends android.support.v7.widget.AppCompatTextView {

    private float width;
    private int inside;

    public GameCell(Context context) {
        super(context);
    }

    public void setWidth(float width) {
        this.width = width;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int)width, (int)width);
    }

    public boolean setInside(int inside) {
        if(this.inside != 1 && this.inside != -1) {
            this.inside = inside;
            if(inside == 1) {
                setBackgroundResource(R.drawable.circle_drawable);
            } else {
                setBackgroundResource(R.drawable.ic_close_black_24dp);
            }
            return true;
        }
        return false;
    }

    public int add(GameCell... cells) {
        int sum = this.inside;
        for(GameCell cell: cells) {
            sum += cell.getInside();
        }
        return sum;
    }

    public int getInside() {
        return this.inside;
    }
}
