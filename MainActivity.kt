package com.example.citiies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.google.gson.Gson
import java.io.InputStreamReader
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    lateinit var cities : Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttStart:Button = findViewById(R.id.start)
        val spinner: Spinner = findViewById(R.id.spinner)

        var et_distance:EditText = findViewById(R.id.distance)
        val cities_stream = resources.openRawResource(R.raw.data)
        val gson = Gson()
        val cities_data = gson.fromJson(InputStreamReader(cities_stream), Cities::class.java)

        println("test coord")
        val xcity = cities_data.cities[0]


        fun getCities(data:Cities):MutableList<String>{
            var names:MutableList<String> = arrayListOf()
            for(i in 0..data.cities.size-1){
                val current = data.cities[i]
                names.add(current.name)
            }
            return names
        }

        fun inDistance(data:Cities,dist:Double,st_city:Int):String{
            var cities:String = ""
            val d = data.cities

            val st_lon:Double = d[st_city].coord["lon"]?.toDouble() ?: 0.0
            val st_lat: Double = d[st_city].coord["lat"]?.toDouble() ?: 0.0

            for(i in 0..d.size-1){
                if (i != st_city){
                    val current = d[i]
                    val cu_lon:Double = current.coord["lon"]?.toDouble() ?: 0.0
                    val cu_lat: Double = current.coord["lat"]?.toDouble() ?: 0.0
                    val result:Double = sqrt(((cu_lon - st_lon).pow(2.0) + (cu_lat - st_lat).pow(2.0)))
                    if(result <= dist){
                        cities+= current.name + System.getProperty("line.separator")
                    }
                }
            }
            return cities
        }

        val names = getCities(cities_data)
        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, names)
        spinner.adapter = adapter

        buttStart.setOnClickListener {
            val intent = Intent(this@MainActivity, ResultActivity::class.java)
            val distance:Double= et_distance.text.toString().toDouble()
            val startCity:Int= spinner.getSelectedItemPosition()
            val foundCities = inDistance(cities_data,distance,startCity)
            intent.putExtra("cities",foundCities)
            startActivity(intent)
        }
    }
}