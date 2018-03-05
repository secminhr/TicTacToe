package com.example.secminhr.tictactoe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements GameBoard.OnFinishedListener {

    GameBoard board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        board = new GameBoard(this);
        setContentView(board);
        board.setOnFinishedListener(this);
    }

    @Override
    public void finished(int winner) {
        if(winner == 1) {
            getSupportActionBar().setTitle(getString(R.string.circle_win));
        } else if(winner == -1) {
            getSupportActionBar().setTitle(getString(R.string.cross_win));
        } else if(winner == 0) {
            getSupportActionBar().setTitle(getString(R.string.draw));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.restart_action:
                board = new GameBoard(this);
                board.setOnFinishedListener(this);
                setContentView(board);
                getSupportActionBar().setTitle(getString(R.string.app_name));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
