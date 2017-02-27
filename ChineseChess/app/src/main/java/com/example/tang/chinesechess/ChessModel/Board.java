package com.example.tang.chinesechess.ChessModel;

import java.util.Map;

/**
 * Created by Tang on 2017/2/21.
 */

public class Board {

    public final int BOARD_WIDTH = 9, BOARD_HEIGHT = 10;
    public Map<String,Piece> pieces;/**棋子集合*/
    public char player='r';/**红色玩家*/
    private Piece[][] cells = new Piece[BOARD_HEIGHT][BOARD_WIDTH];

    public boolean isInside(int[] position) {
        return isInside(position[0], position[1]);
    }

    public boolean isInside(int x, int y) {
        return !(x < 0 || x >= BOARD_HEIGHT
                || y < 0 || y >= BOARD_WIDTH);
    }

    public boolean isEmpty(int[] position) {
        return isEmpty(position[0], position[1]);
    }

    public boolean isEmpty(int x, int y) {
        /**越界或不存在*/
        return isInside(x, y) && cells[x][y] == null;
    }

    public Piece getPiece(int[] pos) {
        return getPiece(pos[0], pos[1]);
    }

    public Piece getPiece(int x, int y) {
        return cells[x][y];
    }

    /**位置更新*/
    public boolean update(Piece piece) {
        int[] pos = piece.position;
        cells[pos[0]][pos[1]] = piece;
        return true;
    }

    public Piece updatePiece(String key, int[] newPos) {

        Piece orig = pieces.get(key);
        Piece inNewPos = getPiece(newPos);

        /* 如果移动的位置存在棋子则移除该棋子*/
        if (inNewPos != null)
            pieces.remove(inNewPos.key);

        /* 清除并更新*/
        int[] origPos = orig.position;
        cells[origPos[0]][origPos[1]] = null;
        cells[newPos[0]][newPos[1]] = orig;
        orig.position = newPos;
        player = (player == 'r') ? 'b' : 'r';/**玩家交替*/
        return inNewPos;
    }

    public boolean backPiece(String key) {
        int[] origPos = pieces.get(key).position;
        cells[origPos[0]][origPos[1]] = pieces.get(key);
        return true;
    }
}
