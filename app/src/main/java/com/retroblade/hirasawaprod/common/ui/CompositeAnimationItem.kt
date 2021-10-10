package com.retroblade.hirasawaprod.common.ui

import android.widget.TextView
import java.lang.ref.WeakReference

/**
 * A class that represents a structure of "node" for animation manager
 * @see CompositeAnimationManager
 */
class CompositeAnimationItem(
    val view: WeakReference<TextView>,
    val text: String,
    val from: Long,
    val to: Long,
    val endAction: (() -> Unit)? = null
)