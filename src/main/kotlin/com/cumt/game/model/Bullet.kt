package com.cumt.game.model

import com.cumt.game.Enums.Direction
import com.cumt.game.business.*
import com.cumt.game.config.Config
import com.cumt.game.extend.checkCollision
import org.itheima.kotlin.game.core.Painter

class Bullet(val direction: Direction,
             create: (Int, Int) -> Pair<Int, Int>)
    : AutoMoveable,Destroyable,Attackable {

    override val attackPower: Int =1
    override val speed: Int = 10
    override var x: Int = 0
    override var y: Int = 0
    override val width: Int
    override val height: Int
    private var destroyed:Boolean = false
    private val imgPath: String = when (direction) {
        Direction.UP -> "/img/shot_top.gif"
        Direction.DOWN -> "/img/shot_bottom.gif"
        Direction.LEFT -> "/img/shot_left.gif"
        Direction.RIGHT -> "/img/shot_right.gif"
    }

    init {
        val size = Painter.size(imgPath)
        width = size[0]
        height = size[1]
        val pair = create.invoke(width, height)
        x = pair.first
        y = pair.second
    }


    override fun draw() {
        Painter.drawImage(imgPath, x, y)
    }

    override fun autoMove() {
        when (direction) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }
    }

    override fun isDestroyed(): Boolean {
        if (destroyed) return true
       return when{
            x<-width ->true
            x> Config.gameWidth ->true
            y<-height ->true
            y>Config.gameHeight ->true
           else ->false
        }
    }
    override fun isCollision(sufferable: Sufferable): Boolean {
        return checkCollision(sufferable)
    }

    override fun notifyAttack(sufferable: Sufferable) {
        destroyed = true
    }
}