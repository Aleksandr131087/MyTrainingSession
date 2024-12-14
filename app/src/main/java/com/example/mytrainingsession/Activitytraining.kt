package com.example.mytrainingsession

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.system.measureTimeMillis

class Activitytraining : AppCompatActivity() {

    val exercises = ExerciseDataBase.exercises

private var exerciseIndex = 0
private lateinit var currentExercise: Exercise
private lateinit var timer: CountDownTimer

    private lateinit var toolbarTraining: Toolbar
    private lateinit var titleTV: TextView
    private lateinit var startButton:Button
    private lateinit var excerciseTV:TextView
private lateinit var descriptionTV: TextView
private lateinit var nextButton: Button
    private lateinit var imageViewIV:ImageView
    private lateinit var timerTV: TextView



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activitytraining)
        toolbarTraining = findViewById(R.id.toolbarTraining)
        setSupportActionBar(toolbarTraining)
        titleTV = findViewById(R.id.titleTV)
        startButton = findViewById(R.id.startButton)
        excerciseTV = findViewById(R.id.excerciseTV)
        descriptionTV = findViewById(R.id.descriptionTV)
        nextButton = findViewById(R.id.nextButton)
        imageViewIV = findViewById(R.id.imageViewIV)
        timerTV = findViewById(R.id.timerTV)

        startButton.setOnClickListener {
            startWorkout()
        }
        nextButton.setOnClickListener {
            nextExcercise()
        }




    }

    private fun nextExcercise() {
        timer.cancel()
        nextButton.isEnabled = false
        startNextExcercise()
    }

    private fun startWorkout() {
        exerciseIndex=0
        titleTV.text = "Начало тренировки"
        startButton.isEnabled = false
        startButton.text = "Процесс тренировки"
        startNextExcercise()

    }

    private fun startNextExcercise() {
        if (exerciseIndex<exercises.size){
            currentExercise = exercises[exerciseIndex]
            excerciseTV.text = currentExercise.name
            descriptionTV.text = currentExercise.description
            imageViewIV.setImageResource(exercises[exerciseIndex].gifImage)
            timerTV.text = formatTime(currentExercise.durationInSeconds)
            timer = object: CountDownTimer(
                currentExercise.durationInSeconds*1000L,
                1000){
                override fun onTick(p0: Long) {
                    timerTV.text= formatTime((p0/1000).toInt())
                }

                override fun onFinish() {
                    timerTV.text = "Упражнение завершено"
                    imageViewIV.visibility = View.VISIBLE
                    nextButton.isEnabled = true
                    imageViewIV.setImageResource(0)
                }
            }.start()
            exerciseIndex++
        } else{
            titleTV.text = "Тренировка закончена"
            descriptionTV.text=""
            timerTV.text = ""
            nextButton.isEnabled = false
            startButton.isEnabled = true
            startButton.text="Начать снова"
        }

    }

    private fun formatTime(seconds: Int): String {
val minutes = seconds/60
        val remainingSeconds = seconds%60
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_exit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_exit -> finishAffinity()
        }
        return super.onOptionsItemSelected(item)
    }
}