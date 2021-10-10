package com.retroblade.hirasawaprod.feature.content.ui.animation

import com.retroblade.hirasawaprod.common.ui.AnimationPreset

/**
 * The extension file contains some content animation presets used in animation manager
 */

internal val HideContent = AnimationPreset { view ->
    view.animate().setStartDelay(0L).alpha(1.0F).setDuration(500L)
}

internal val ShowContent = AnimationPreset { view ->
    view.animate().setStartDelay(0L).alpha(0.0F).setDuration(500L)
}

internal val HideProgressBar = AnimationPreset { view ->
    view.animate().setStartDelay(1500L).setDuration(500L).alpha(0.0F)
}

internal val ShowProgressBar = AnimationPreset { view ->
    view.animate().setStartDelay(0L).setDuration(500L).alpha(1.0F)
}

internal val HideErrorMessage = AnimationPreset { view ->
    view.animate().setStartDelay(0L).setDuration(500L).alpha(0.0F)
}

internal val ShowErrorMessage = AnimationPreset { view ->
    view.animate().setStartDelay(200L).setDuration(500L).alpha(1.0F)
}

