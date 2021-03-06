package com.cumt.game.model

import com.cumt.game.business.Destroyable
import com.cumt.game.business.View
import com.cumt.game.config.Config
import org.itheima.kotlin.game.core.Painter


class Blast(override val x: Int, override val y: Int) : Destroyable {

    override val width: Int= Config.block
    override val height: Int = Config.block
    private val imgPaths = arrayListOf<String>()
    private var index:Int=0
    init {
        (1..32).forEach {
            imgPaths.add("/img/blast_$it.png")
        }
    }
    override fun draw() {
        var i = index % imgPaths.size
        Painter.drawImage(imgPaths[i],x,y)
        index++
    }
    override fun isDestroyed(): Boolean {
        return index>=32
    }

}