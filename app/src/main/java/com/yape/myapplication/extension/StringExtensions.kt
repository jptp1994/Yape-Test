package com.yape.myapplication.extension

import com.yape.app.R

/**
 * Shorthand extension function to make view gone
 */
fun String.getImageDifficult(): Int {
    return when (this){
        "easy"-> R.drawable.ic_difficult_easy
            "medium"-> R.drawable.ic_difficult_medium
        else -> R.drawable.ic_difficult_hard
    }
}
