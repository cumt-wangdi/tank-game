package com.cumt.game.business


interface View {
    //位置
    val x:Int
    val y:Int
    //宽高
    val width:Int
    val height:Int
    //显示
    fun draw()

    fun checkCollision(x1:Int,y1:Int,w1:Int,h1:Int,x2:Int,y2:Int,w2:Int,h2:Int):Boolean{
        return when {
        //阻塞物在运动物的上方，不碰撞
            y2 + h2 <= y1 -> false
        //阻塞物在运动物的下方，不碰撞
            y1 + h1 <= y2 -> false
        //阻塞物在运动物的左方，不碰撞
            x2+ w2 <= x1 -> false
        //阻塞物在运动物的右方，不碰撞
            x1 + w1 <= x2 -> false
        //其他情况发生碰撞
            else -> true
        }
    }
}