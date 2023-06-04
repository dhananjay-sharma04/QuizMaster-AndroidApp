package com.dhananjay.quiz.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import com.dhananjay.quiz.databinding.ActivityResultBinding
import com.dhananjay.quiz.models.Quiz
import com.google.gson.Gson
import java.lang.StringBuilder

class ResultActivity : AppCompatActivity() {

    lateinit var binding: ActivityResultBinding

    lateinit var quiz: Quiz

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViews()
    }

    private fun setUpViews() {
        val quizData = intent.getStringExtra("QUIZ")
        quiz = Gson().fromJson<Quiz>(quizData, Quiz::class.java)

        calculateScore()
        setAnswerView()
    }

    private fun setAnswerView() {
        val builder = StringBuilder("")
        for (entry in quiz.Questions.entries){
            val question = entry.value
            builder.append("<font color'#18206F'><b>Question: ${question.description}</b></font><br/><br/>")
            builder.append("<font color='#009688'>Answer: ${question.answer}</font><br/><br/>")
        }
        binding.tvTextAnswer.text = Html.fromHtml(builder.toString(), Html.FROM_HTML_MODE_COMPACT)
    }

    private fun calculateScore() {
        var score = 0

        for (entry in quiz.Questions.entries){
            val question = entry.value
            if (question.answer == question.userAnswer){
                score += 10
            }
        }

        binding.tvScore.text = "Your Score : $score"
    }
}