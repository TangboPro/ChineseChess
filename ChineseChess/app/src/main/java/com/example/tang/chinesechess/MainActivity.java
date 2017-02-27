package com.example.tang.chinesechess;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tang.chinesechess.ChessModel.Board;
import com.example.tang.chinesechess.ChessView.GameView;
import com.example.tang.chinesechess.control.GameController;

public class MainActivity extends AppCompatActivity {

    private final int UPDATA_VIEW=123;
    private final int SHOW_WIN=321;
    private Board board;

    private GameController controller;
    private GameView gameView;
    private ImageView playerImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        this.addContentView(gameView, params);
        //gameView.invalidate();
    }

    private void init() {
        controller = new GameController();
        board = controller.playChess();
        gameView = new GameView(this, controller, board);
        myThread th = new myThread();
        th.start();
    }

    /**消息队列*/
    Handler myHandler = new Handler() {
        // 接收到消息后处理
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATA_VIEW:
                    gameView.postInvalidate(); // 刷新界面
                    break;
                case SHOW_WIN:
                    gameView.showWin(msg.getData().getChar("win"));
                    break;
            }
            super.handleMessage(msg);
        }
    };

    class myThread extends Thread {
        @Override
        public void run() {
            super.run();

            while (controller.hasWin(board) == 'x') {
                updataView();
            /* User in. */
                while (board.player == 'r')
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                updataView();
                /* 界面更新缓冲*/
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (controller.hasWin(board) != 'x') {
                    showWin('r');
                }
                if (controller.hasWin(board) != 'x') {
                    interrupt();//中断线程
                }
            /* AI in. */
                controller.responseMoveChess(board, gameView);
                /* 更新界面*/
                updataView();
                /* 界面更新缓冲*/
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            /**黑棋赢*/
            showWin('b');
        }

        private void showWin(char player) {
            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putChar("win", player);
            msg.what = SHOW_WIN;
            msg.setData(bundle);
            myHandler.sendMessage(msg);
        }
        private void updataView() {
            Message message;
            message = new Message();
            message.what = UPDATA_VIEW;
            myHandler.sendMessage(message);
        }
    }


}

