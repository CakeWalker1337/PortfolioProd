package com.retroblade.hirasawaprod.common.ui

import android.widget.TextView
import java.lang.ref.WeakReference

/**
 * @author m.a.kovalev
 */
class CompositeAnimationItem(
    val view: WeakReference<TextView>,
    val text: String,
    val from: Long,
    val to: Long,
    val endAction: (() -> Unit)? = null
)