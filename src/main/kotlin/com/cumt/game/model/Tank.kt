package com.cumt.game.model

import com.cumt.game.Enums.Direction
import com.cumt.game.business.Blockable
import com.cumt.game.business.Moveable
import com.cumt.game.business.View
import com.cumt.game.config.Config
import org.itheima.kotlin.game.core.Painter

class Tank(override var x: Int, override var y: Int) :View,Moveable {


    override val width: Int = Config.block
    override val height: Int = Config.block
    //移动的速度
    override val speed: Int=10
    override var currDirection: Direction =Direction.UP
    //不可以走的方向
    private var badDirection:Direction?=null

    override fun draw() {
        val path = when (currDirection) {
            Direction.UP -> "/img/tank_u.gif"
            Direction.DOWN -> "/img/tank_d.gif"
            Direction.LEFT -> "/img/tank_l.gif"
            Direction.RIGHT -> "/img/tank_r.gif"
        }
        Painter.drawImage(path, x, y)
    }

    fun move(direction: Direction) {
        //如果是不可以走的方向，就不往下执行
        if (direction == badDirection){
            return
        }

        //如果当前方向与移动方向不一致时只改变方向
        if (this.currDirection != direction) {
            this.currDirection = direction
            return
        }
        //根据方向控制移动
        when (this.currDirection) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }
        //判断越界
        if (x < 0) x = 0
        if (x > Config.gameWidth - width) x = Config.gameWidth - width
        if (y < 0) y = 0
        if (y > Config.gameHeight - height) y = Config.gameHeight - height
    }

    override fun willCollision(block: Blockable): Direction? {
        var x = this.x
        var y = this.y

        when (currDirection) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }

        val collision = when {
        //阻塞物在运动物的上方，不碰撞
            block.y + block.height <= y -> false
        //阻塞物在运动物的下方，不碰撞
            y + height <= block.y -> false
        //阻塞物在运动物的左方，不碰撞
            block.x + block.width <= x -> false
        //阻塞物在运动物的右方，不碰撞
            x + width <= block.x -> false
        //其他情况发生碰撞
            else -> true
        }

        return if (collision) currDirection else null
    }

    override fun notifyCollision(direction: Direction?, block: Blockable?) {
        badDirection=direction
    }

    fun shot():Bullet {
        return Bullet(currDirection,{bulletWidth,bulletHeight ->
            //获取坦克当前的位置
            val tankX = x
            val tankY = y
            //计算子弹的位置
            var bulletX:Int = 0
            var bulletY:Int = 0
            when(currDirection){
                Direction.UP ->{
                    bulletX = tankX + (width - bulletWidth)/2
                    bulletY = tankY - bulletHeight/2
                }
                Direction.DOWN ->{
                    bulletX = tankX + (width - bulletWidth)/2
                    bulletY = tankY+height- bulletHeight/2
                }
                Direction.LEFT ->{
                    bulletX = tankX- bulletWidth/2
                    bulletY = tankY +(height - bulletHeight)/2
                }
                Direction.RIGHT ->{
                    bulletX = tankX +width-bulletWidth/2
                    bulletY = tankY +(height - bulletHeight)/2
                }
            }
            Pair(bulletX,bulletY)
        })
    }
}