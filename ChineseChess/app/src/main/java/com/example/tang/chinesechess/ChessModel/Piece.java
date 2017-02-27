package com.example.tang.chinesechess.ChessModel;

/**
 * Created by Tang on 2017/2/21.
 *
 */

public class Piece {

    public String key;/**棋子key*/
    public char color;/**颜色*/
    public char character;/**标识*/
    public int[] position = new int[2];/**坐标*/

    public Piece(String name,int[] position){
        this.key = name;
        this.color = name.charAt(0);
        this.character = name.charAt(1);
        this.position = position;
    }
}
