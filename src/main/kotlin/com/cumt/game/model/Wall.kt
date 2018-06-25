package com.cumt.game.model

import com.cumt.game.business.*
import com.cumt.game.config.Config
import org.itheima.kotlin.game.core.Painter

class Wall(override val x: Int, override val y: Int) : Blockable,Sufferable,Destroyable {


    override var blood: Int=3
    override val width: Int=Config.block
    override val height: Int = Config.block

    override fun draw() {
        Painter.drawImage("img/wall.gif",x,y)
    }
    override fun notifySuffer(attackable: Attackable):Array<View>? {
       blood -= attackable.attackPower
        return arrayOf(Blast(x,y))
    }

    override fun isDestroyed(): Boolean = blood <=0

}