package com.cumt.game.model

import com.cumt.game.Enums.Direction
import com.cumt.game.business.AutoMoveable
import com.cumt.game.business.Blockable
import com.cumt.game.business.Moveable
import com.cumt.game.config.Config
import org.itheima.kotlin.game.core.Painter

class Enemy(override val x: Int, override val y: Int) :Moveable,AutoMoveable {

    override val currDirection: Direction =Direction.DOWN
    override val speed: Int=8
    override val width: Int= Config.block
    override val height: Int=Config.block

    override fun draw() {
        val imgPath = when (currDirection) {
            Direction.UP -> "/img/enemy_1_u.gif"
            Direction.DOWN -> "/img/enemy_1_d.gif"
            Direction.LEFT -> "/img/enemy_1_l.gif"
            Direction.RIGHT -> "/img/enemy_1_r.gif"
        }
        Painter.drawImage(imgPath,x,y)
    }


    override fun notifyCollision(direction: Direction?, block: Blockable?) {
    }
    override fun autoMove() {

    }



}