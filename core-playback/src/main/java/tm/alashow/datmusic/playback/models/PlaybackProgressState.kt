/*
 * Copyright (C) 2021, Alashov Berkeli
 * All rights reserved.
 */
package tm.alashow.datmusic.playback.models

import tm.alashow.base.util.millisToDuration

data class PlaybackProgressState(
    val total: Long = 0L,
    val position: Long = 0L,
    val elapsed: Long = 0L,
) {

    val progress get() = ((position.toFloat() + elapsed) / (total + 1).toFloat()).coerceIn(0f, 1f)

    val currentDuration get() = (position + elapsed).millisToDuration()
    val totalDuration get() = total.millisToDuration()
}