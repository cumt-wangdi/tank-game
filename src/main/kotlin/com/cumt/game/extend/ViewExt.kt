package com.cumt.game.extend

import com.cumt.game.business.View

fun View.checkCollision(view: View):Boolean{
    return checkCollision(x,y,width,height,view.x,view.y,view.width,view.height)
}