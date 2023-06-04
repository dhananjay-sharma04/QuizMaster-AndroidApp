package com.dhananjay.quiz.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dhananjay.quiz.R
import com.dhananjay.quiz.adapters.OptionAdapter
import com.dhananjay.quiz.databinding.ActivityQuestionBinding
import com.dhananjay.quiz.models.Question
import com.dhananjay.quiz.models.Quiz
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class QuestionActivity : AppCompatActivity() {

    var quizzes : MutableList<Quiz>? = null
    var questions : MutableMap<String, Question>? = null
    var index = 1
    lateinit var binding: ActivityQuestionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            setUpFirestore()
            setUpEventListener()
        } else{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setUpEventListener() {
        binding.btnPrevious.setOnClickListener {
            index --
            bindViews()
        }
        binding.btnNext.setOnClickListener {
            index ++
            bindViews()
        }
        binding.btnSubmit.setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java)
            val json = Gson().toJson(quizzes!![0])
            intent.putExtra("QUIZ", json)
            startActivity(intent)
        }
    }

    private fun setUpFirestore() {
        val firestore = FirebaseFirestore.getInstance()
        var date = intent.getStringExtra("DATE")
        if (date != null){
            firestore.collection("quizzes").whereEqualTo("title", date)
                .get()
                .addOnSuccessListener {
                    if (it != null && !it.isEmpty){
                        quizzes = it.toObjects(Quiz::class.java)
                        questions = quizzes!![0].Questions
                        bindViews()
                    }
                }
        }
    }

    private fun bindViews() {

        binding.btnPrevious.visibility = View.GONE
        binding.btnNext.visibility = View.GONE
        binding.btnSubmit.visibility = View.GONE

        if (index == 1){
            binding.btnNext.visibility = View.VISIBLE
        } else if (index == questions!!.size){
            binding.btnPrevious.visibility = View.VISIBLE
            binding.btnSubmit.visibility = View.VISIBLE
        } else{
            binding.btnPrevious.visibility = View.VISIBLE
            binding.btnNext.visibility = View.VISIBLE
        }

        val question = questions!!["question$index"]

        question?.let {
            binding.description.text = it.description
            val optionAdapter = OptionAdapter(this, it)
            binding.optionList.layoutManager = LinearLayoutManager(this)
            binding.optionList.adapter = optionAdapter
            binding.optionList.setHasFixedSize(true)
        }

    }
}