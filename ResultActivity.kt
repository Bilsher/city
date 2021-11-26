package com.example.citiies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {

    private lateinit var cities: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val backButt: Button = findViewById(R.id.back)
        cities = findViewById(R.id.result_cities)
        var gotRes= intent.extras?.getString("cities")
        cities.setText(gotRes)

        backButt.setOnClickListener {
            val intent = Intent(this@ResultActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}