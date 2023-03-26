package com.jzheng20.minesweeper.model

data class Grid(
    val x:Int,
    val y:Int,
    val bombsAround:Int,
    val isBomb:Boolean = false,
    val revealed:Boolean = false
){
    fun getX(){x}
    fun getY(){y}
    fun getBombsAround(){bombsAround}
    fun isBomb(){isBomb}
    fun isRevealed(){revealed}
}
