package com.retroblade.hirasawaprod.common.ui

import android.view.View
import java.lang.ref.WeakReference

/**
 * @author m.a.kovalev
 */
class CompositeAnimationItem(
    val view: WeakReference<View>,
    val text: String,
    val from: Long,
    val to: Long,
    val endAction: (() -> Unit)? = null
)