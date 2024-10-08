package cn.cimoc.mys.ext

import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon

fun Modifier.hand(): Modifier {
    return this.pointerHoverIcon(PointerIcon.Hand)
}

