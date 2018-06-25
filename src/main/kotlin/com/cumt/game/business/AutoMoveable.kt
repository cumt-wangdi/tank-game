package com.cumt.game.business

import com.cumt.game.Enums.Direction

interface AutoMoveable:View {
    //速度
    val speed :Int
    //自己移动的方法
    fun autoMove()


}