package com.cumt.game.business

interface Attackable:View {
    //检测是否碰撞
    fun isCollision(sufferable: Sufferable):Boolean
    //通知碰撞
    fun notifyAttack(sufferable: Sufferable)
    //攻击力
    val attackPower:Int

}