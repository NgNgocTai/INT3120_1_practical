package com.example.artspace.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.artspace.R
data class Art(
    @DrawableRes val imageResourceId: Int,
    @StringRes val title: Int,
    @StringRes val artist: Int,
    val year: Int
)

object ArtRepository{
val arts = listOf(
    Art(R.drawable.starry_night, R.string.starry_night, R.string.van_gogh, 1889),
    Art(R.drawable.mona_lisa, R.string.mona_lisa, R.string.leonardo, 1503),
    Art(R.drawable.the_scream, R.string.the_scream, R.string.munch, 1893),
    Art(R.drawable.girl_with_pearl, R.string.girl_with_pearl, R.string.vermeer, 1665),
    Art(R.drawable.night_watch, R.string.night_watch, R.string.rembrandt, 1642),
)}
