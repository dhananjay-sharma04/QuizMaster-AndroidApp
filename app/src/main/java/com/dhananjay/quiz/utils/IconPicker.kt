package com.dhananjay.quiz.utils

import com.dhananjay.quiz.R

object IconPicker {
    val icons = arrayOf(
        R.drawable.black,
        R.drawable.calender,
        R.drawable.laptop,
        R.drawable.notes,
        R.drawable.pen,
        R.drawable.science,
        R.drawable.telescope,
        R.drawable.tool,
        R.drawable.trophy,
        R.drawable.tube
    )

    var currentIcon = 0

    fun getIcon(): Int {
        currentIcon = (currentIcon + 1) % icons.size
        return icons[currentIcon]
    }
}