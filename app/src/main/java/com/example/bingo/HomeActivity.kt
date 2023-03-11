package com.example.bingo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.hide()
        val startButton = findViewById<Button>(R.id.startbtn)
        startButton.setOnClickListener {
            val start = Intent(this,StartActivity::class.java)
            startActivity(start)
        }

        val button = findViewById<Button>(R.id.button2)
        button.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("20210144-Maleesha Kawshan")
            builder.setMessage("I confirm that I understand what plagiarism is and have read and" +
                    "     understood the section on Assessment Offences in the Essential" +
                    " Information for Students.  The work that I have submitted is" +
                    " entirely my own. Any work from other authors is duly referenced" +
                    " and acknowledged.")
            builder.setPositiveButton("OK", null)
            val dialog = builder.create()
            dialog.show()
        }


    }
}