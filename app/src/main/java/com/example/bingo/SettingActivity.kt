package com.example.bingo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        //creating text field to fix winning score by human player

        val editText = findViewById<EditText>(R.id.entermax)
        val button = findViewById<Button>(R.id.setBtn)

        //passing the pass mark to game face activity

        button.setOnClickListener(){
            val value = editText.text.toString().toInt()
            val intent = Intent(this@SettingActivity, StartActivity::class.java)
            intent.putExtra("MY_INT_VALUE", value)
            startActivity(intent)

        }



    }

}