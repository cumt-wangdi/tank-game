package com.cumt.game.business

import com.cumt.game.Enums.Direction

/**
 *
 * 接口，可移动的能力
 */
interface Moveable:View {
    //移动物体的速度
    val speed:Int
    val currDirection:Direction
    /**
     * 判断阻塞物和移动物是否碰撞，如果没有碰撞return null
     */
    fun willCollision(block:Blockable):Direction?{
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

    /**
     * 通知发生碰撞
     */
    fun notifyCollision(direction: Direction?,block: Blockable?)

}