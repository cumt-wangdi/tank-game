package com.cumt.game.model

import com.cumt.game.business.Blockable
import com.cumt.game.business.View
import com.cumt.game.config.Config
import org.itheima.kotlin.game.core.Painter

class Steel(override val x: Int, override val y: Int) : View,Blockable {
    override val width: Int = Config.block
    override val height: Int = Config.block

    override fun draw() {
        Painter.drawImage("/img/steel.gif",x,y)
    }
}