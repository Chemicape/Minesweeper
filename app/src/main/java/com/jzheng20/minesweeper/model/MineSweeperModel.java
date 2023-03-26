package com.jzheng20.minesweeper.model;

public class MineSweeperModel {
    Cell[][] board = new Cell[10][10];
    public MineSweeperModel(int bombs){
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = new Cell(i,j);
            }
        }
        int[]bomblist = getRand(bombs);
        for (int i = 0; i < bomblist.length; i++) {
            board[bomblist[i]/10][bomblist[i]%10].setAsBomb();
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                countBombsAround(i,j);
            }
        }
    }
    public int processor(int x,int y){
        switch (board[x][y].bombsAround){
            case 0:{
                revealAround(x,y);
                return 0;
            } case -1:{
                return -1;
            }
        }
        return board[x][y].bombsAround;
    }
    public boolean win(){
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(board[i][j].isBomb==false){
                    if(board[i][j].isRevealed==false){
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public void revealAround(int x,int y){
        int bombs = 0;
        if(x-1>=0&&board[x-1][y].bombsAround==0){
            board[x-1][y].isRevealed = true;
            revealAround(x-1,y);
        }
        if(y-1>=0&&board[x][y-1].isBomb){
            board[x][y-1].isRevealed = true;
            revealAround(x,y-1);
        }
        if(x+1<=9&&board[x+1][y].isBomb){
            board[x+1][y].isRevealed = true;
            revealAround(x+1,y);
        }
        if(y+1<=9&&board[x][y+1].isBomb){
            board[x][y+1].isRevealed = true;
            revealAround(x,y+1);
        }
        if(x-1>=0&&y-1>=0&&board[x-1][y-1].isBomb){
            board[x-1][y-1].isRevealed = true;
            revealAround(x-1,y-1);
        }
        if(x-1>=0&&y+1<=9&&board[x-1][y+1].isBomb){
            board[x-1][y+1].isRevealed = true;
            revealAround(x-1,y+1);
        }
        if(x+1<=9&&y-1>=0&&board[x+1][y-1].isBomb){
            board[x-1][y].isRevealed = true;
            revealAround(x+1,y-1);
        }
        if(x+1<=9&&y+1<=9&&board[x+1][y+1].isBomb){
            board[x-1][y].isRevealed = true;
            revealAround(x+1,y+1);
        }
        if(!board[x][y].isBomb){
            board[x][y].updateBombsAround(bombs);
        }
    }
    public void countBombsAround(int x,int y){
        int bombs = 0;
        if(x-1>=0&&board[x-1][y].isBomb){
            bombs++;
        }
        if(y-1>=0&&board[x][y-1].isBomb){
            bombs++;
        }
        if(x+1<=9&&board[x+1][y].isBomb){
            bombs++;
        }
        if(y+1<=9&&board[x][y+1].isBomb){
            bombs++;
        }
        if(x-1>=0&&y-1>=0&&board[x-1][y-1].isBomb){
            bombs++;
        }
        if(x-1>=0&&y+1<=9&&board[x-1][y+1].isBomb){
            bombs++;
        }
        if(x+1<=9&&y-1>=0&&board[x+1][y-1].isBomb){
            bombs++;
        }
        if(x+1<=9&&y+1<=9&&board[x+1][y+1].isBomb){
            bombs++;
        }
        if(!board[x][y].isBomb){
            board[x][y].updateBombsAround(bombs);
        }}
    public int[] getRand(int num){
        int[] a = new int[num];
        for (int i = 0; i < a.length; i++) {
            if (i == 0) {
                a[i] = (int) (Math.random() * 100);
            } else {
                int b = (int) (Math.random() * 100);
                for (int j = 0; j < a.length; j++) {
                    if (a[j] == b) {
                        b = (int) (Math.random() * 100);
                        j = 0;
                    }
                }
                a[i] = b;
            }
        }
        return a;
    }
}
class Cell{
    int x;
    int y;
    int bombsAround;
    boolean isBomb;
    boolean isRevealed;
    public Cell(int x,int y){
        this.x = x;
        this.y = y;
        bombsAround = -1;
        isBomb = false;
        isRevealed = false;
    }
    public Boolean isBomb(){
        return isBomb;
    }
    public void updateBombsAround(int bombs){
        bombsAround = bombs;
    }
    public void setAsBomb(){
        isBomb = true;
    }
}