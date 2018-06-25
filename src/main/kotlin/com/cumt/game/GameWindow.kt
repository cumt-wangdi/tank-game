package com.cumt.game

import com.cumt.game.Enums.Direction
import com.cumt.game.business.*
import com.cumt.game.config.Config
import com.cumt.game.model.*
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.itheima.kotlin.game.core.Window
import java.io.File
import java.util.concurrent.CopyOnWriteArrayList

class GameWindow : Window(title = "坦克大战1.0",
        icon = "icon/tank.jpg",
        width = Config.gameWidth,
        height = Config.gameHeight) {

    //管理元素的集合，线程安全的
    private val views = CopyOnWriteArrayList<View>()

    lateinit var tank: Tank
    override fun onCreate() {
        //通过文件创建地图
        val file = File(javaClass.getResource("/map/1.map").path)
        val lines = file.readLines()
        var lineNum = 0
        lines.forEach { line ->
            var columnNum = 0
            line.toCharArray().forEach { column ->
                when (column) {
                    '砖' -> views.add(Wall(columnNum * Config.block, lineNum * Config.block))
                    '草' -> views.add(Grass(columnNum * Config.block, lineNum * Config.block))
                    '水' -> views.add(Water(columnNum * Config.block, lineNum * Config.block))
                    '钢' -> views.add(Steel(columnNum * Config.block, lineNum * Config.block))
                }
                columnNum++
            }
            lineNum++
        }
        tank = Tank(Config.block * 12, Config.block * 14)
        views.add(tank)
    }

    override fun onDisplay() {
        //绘制集合中的元素
        views.forEach {
            it.draw()
        }
    }

    override fun onKeyPressed(event: KeyEvent) {
        //控制坦克的方向
        when (event.code) {
            KeyCode.W, KeyCode.UP -> tank.move(Direction.UP)
            KeyCode.S, KeyCode.DOWN -> tank.move(Direction.DOWN)
            KeyCode.A, KeyCode.LEFT -> tank.move(Direction.LEFT)
            KeyCode.D, KeyCode.RIGHT -> tank.move(Direction.RIGHT)
            KeyCode.ENTER -> {
                val bullet = tank.shot()
                views.add(bullet)
            }
            else -> tank.move(Direction.UP)
        }


    }

    override fun onRefresh() {
        //判断运动物体和阻塞物体是否发生碰撞
        //1.过滤出运动物体
        views.filter{it is Moveable}.forEach { move ->
            move as Moveable
            //2.过滤出阻塞物体
            var badDirection:Direction?=null
            var blockable:Blockable?=null
            views.filter { it is Blockable }.forEach blockTag@{block ->
                block as Blockable
                val direction = move.willCollision(block)
                direction?.let {
                    badDirection = direction
                    blockable = block
                    return@blockTag
                }
            }
            move.notifyCollision(badDirection,blockable)
        }

        //过滤出自动移动的物体
        views.filter { it is AutoMoveable }.forEach {
            it as AutoMoveable
            it.autoMove()
        }
        //过滤出可以销毁的物体
        views.filter { it is Destroyable }.forEach {
            if ((it as Destroyable).isDestroyed()){
                views.remove(it)
            }
        }

        //过滤出攻击和被攻击的物体
        views.filter { it is Attackable }.forEach { attack ->
            attack as Attackable
            views.filter { it is Sufferable }.forEach { suffer ->
                suffer as Sufferable
                if (attack.isCollision(suffer)){
                    val blastView = suffer.notifySuffer(attack)
                    blastView?.let {
                        views.addAll(blastView)
                    }
                    attack.notifyAttack(suffer)
                }
            }
        }
    }
}