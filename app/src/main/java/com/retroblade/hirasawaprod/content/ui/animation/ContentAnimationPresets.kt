package com.retroblade.hirasawaprod.content.ui.animation

import com.retroblade.hirasawaprod.utils.ui.AnimationPreset

/**
 * @author m.a.kovalev
 */

internal val SwipeToRefresh = AnimationPreset { view ->
    view.animate().setStartDelay(0L).alpha(1.0F).setDuration(400L)
}

internal val ShowContent = AnimationPreset { view ->
    view.animate().setStartDelay(2000L).alpha(0.0F).setDuration(400L)
}

internal val HideProgressBar = AnimationPreset { view ->
    view.animate().setStartDelay(0L).setDuration(500L).alpha(0.0F)
}

internal val ShowProgressBar = AnimationPreset { view ->
    view.animate().setStartDelay(200L).setDuration(1000L).alpha(1.0F)
}

internal val HideErrorMessage = AnimationPreset { view ->
    view.animate().setStartDelay(0L).setDuration(500L).alpha(0.0F)
}

internal val ShowErrorMessage = AnimationPreset { view ->
    view.animate().setStartDelay(200L).setDuration(1000L).alpha(1.0F)
}

