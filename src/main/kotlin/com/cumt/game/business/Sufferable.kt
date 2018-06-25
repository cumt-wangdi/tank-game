package com.cumt.game.business

interface Sufferable:View {
    //通知碰撞
    fun notifySuffer(attackable: Attackable):Array<View>?
    //生命值
    val blood:Int
}