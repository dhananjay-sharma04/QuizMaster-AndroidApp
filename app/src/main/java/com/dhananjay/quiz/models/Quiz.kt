package com.dhananjay.quiz.models

data class Quiz(
    var id : String = "",
    var title : String = "",
    var Questions: MutableMap<String, Question> = mutableMapOf()
)