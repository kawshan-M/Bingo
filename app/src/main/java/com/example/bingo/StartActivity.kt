package com.example.bingo

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class StartActivity : AppCompatActivity() {

    //create global variables
    private var winningPoints = 101
    private var playerDice = arrayOf(0, 0, 0, 0, 0)
    private var comDice = arrayOf(0, 0, 0, 0, 0)
    private var tieN = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        supportActionBar?.hide()

        var textView = findViewById<TextView>(R.id.playerScoreview)
        var textView2 = findViewById<TextView>(R.id.comScoreview)
        var count = 0
        var count2 = 0
        var playerScore = 0
        var comScore = 0
        var change =0
        var roll = 0
        var c = 0
        var rerolls =0

        //getting pass mark through Settings page.kt just to set the winning score

        intent?.let {
            val value = it.getIntExtra("MY_INT_VALUE", winningPoints)
            winningPoints = value
        }

        findViewById<Button>(R.id.throwBtn).setOnClickListener{
            //set tie strategy
            if (tieN){
                var x =0
                var y =0
                while (x<5){
                    var randomNo =(1..6).random()
                    playerDice[x]=randomNo
                    x++
                    diceRoll(x,count)
                }
                while (y<5){
                    var randomNo =(1..6).random()
                    comDice[y] = randomNo
                    y++
                    comStrategy(randomNo, y)
                }
                tieN = false
            }
            //set player dice
            else if (count<5){
                while (count<5){
                    var randomNo =(1..6).random()
                    playerDice[count]=randomNo
                    count++
                    diceRoll(randomNo,count)
                    roll = cal(count,roll)
                }
                while (count2<5){
                    var randomNo =(1..6).random()
                    comDice[count2] = randomNo
                    count2++
                    comStrategy(randomNo, count2)
                }

            }
            //reroll strategy for player
            else if (rerolls==1){
                var randomNo =(1..6).random()
                check(false)
                c = 0
                for (k in 0..4){
                    if (playerDice[k] == 0){
                        c = k
                        break
                    }
                }
                playerDice[c]=randomNo
                c+=1
                diceRoll(randomNo,c)
                rerolls = set(playerDice,rerolls)
                if (roll == 3 && rerolls ==0){
                    for (i in playerDice){
                        playerScore += i
                    }
                    textView.text = playerScore.toString() //add players score
                    comDice = computer(comDice)
                    for (i in comDice){
                        comScore += i
                    }
                    textView2.text = comScore.toString()
                    roll = 0
                    count = 0
                    count2 = 0
                }
            }
            //search winner
            if (playerScore >= winningPoints || comScore >= winningPoints){
                //tie strategy
                if (playerScore==comScore){
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("YOU and COMPUTER achieved the same score")
                    builder.setMessage("“You and the computer have one last throw ”")
                    builder.setPositiveButton("OK", null)
                    val dialog = builder.create()
                    dialog.show()
                    findViewById<Button>(R.id.rerollBtn).isEnabled = false
                    tieN = true
                }
                else{
                    win(playerScore,winningPoints)
                }
            }
        }

        findViewById<Button>(R.id.rerollBtn).setOnClickListener{
            if (roll == 0 ){
                Toast.makeText(this,"First throw your dices",Toast.LENGTH_SHORT).show()
            }else if (roll < 3){
                check(true)
                Toast.makeText(this,"Select the dice want to roll",Toast.LENGTH_SHORT).show()
                reroll(playerDice)
                rerolls = 1
                roll+=1
            }else{
                Toast.makeText(this,"you performs the maximum of 3 rolls for that turn",Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.scoreBtn).setOnClickListener{
            if (change ==0){

                //random strategy and add score
                comDice = computer(comDice)
                for (i in comDice){
                    comScore += i
                }
                textView2.text = comScore.toString()
                roll = 0
                count = 0
                count2 = 0
                for (i in playerDice) {
                    playerScore += i
                }
                textView.text = playerScore.toString() //add players score
            }
            //winner check
            if (playerScore >= winningPoints || comScore >= winningPoints){
                if (playerScore==comScore){
                    //tie strategy
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("YOU and COMPUTER achieved the same score")
                    builder.setMessage("“You and the computer have one last throw ”")
                    builder.setPositiveButton("OK", null)
                    val dialog = builder.create()
                    dialog.show()
                    findViewById<Button>(R.id.rerollBtn).isEnabled = false
                    tieN = true
                }
                else{
                    win(playerScore,winningPoints)
                }
            }
        }
    }

    //computer random strategy
    fun computer(array: Array<Int>): Array<Int> {
        var noOfRolls =(1..3).random()
        var x = 0
        if (noOfRolls>1){
            Toast.makeText(this,"Computer use reroll",Toast.LENGTH_SHORT).show()
            while (noOfRolls==1){
                while (x>5){
                    var rand =(0..1).random()
                    var randno =(1..6).random()
                    if (rand==0){
                        array[x]=randno
                        comStrategy(randno,x)
                    }
                    x+=1
                }
                noOfRolls-=1
            }

        }
        Toast.makeText(this,"Computer is not use reroll",Toast.LENGTH_SHORT).show()
        return array
    }

    fun set(array: Array<Int>,rerolls:Int): Int {
        var x =0
        var s =rerolls
        for (i in 0..4){
            if(array[i]!=0){
                x+=1
            }
        }
        if (x==5){
            s =0
        }
        return s
    }

    fun cal(count:Int, roll:Int): Int {
        var y = roll
        if (count == 4){
            y+=1
        }
        return y
    }

    //dice roll function
    private fun diceRoll(randomNo:Int, y:Int){
        var imageDice :Int
        when (randomNo) {
            1 -> {
                imageDice=R.drawable.dice_1
            }
            2 -> {
                imageDice=R.drawable.dice_2
            }
            3 -> {
                imageDice=R.drawable.dice_3
            }
            4 -> {
                imageDice=R.drawable.dice_4
            }
            5 -> {
                imageDice=R.drawable.dice_5
            }
            else -> {
                imageDice=R.drawable.dice_6
            }
        }
        when (y) {
            1 -> {
                findViewById<ImageView>(R.id.userDice1).setImageResource(imageDice)
            }
            2 -> {
                findViewById<ImageView>(R.id.userDice2).setImageResource(imageDice)
            }
            3 -> {
                findViewById<ImageView>(R.id.userDice3).setImageResource(imageDice)
            }
            4 -> {
                findViewById<ImageView>(R.id.userDice4).setImageResource(imageDice)
            }
            5 -> {
                findViewById<ImageView>(R.id.userDice5).setImageResource(imageDice)
            }
        }
    }

    fun comStrategy(randomNo:Int, y:Int){
        var imageDice :Int
        when (randomNo) {
            1 -> {
                imageDice=R.drawable.dice_1
            }
            2 -> {
                imageDice=R.drawable.dice_2
            }
            3 -> {
                imageDice=R.drawable.dice_3
            }
            4 -> {
                imageDice=R.drawable.dice_4
            }
            5 -> {
                imageDice=R.drawable.dice_5
            }
            else -> {
                imageDice=R.drawable.dice_6
            }
        }
        when (y) {
            1 -> {
                findViewById<ImageView>(R.id.comDice1).setImageResource(imageDice)
            }
            2 -> {
                findViewById<ImageView>(R.id.comDice2).setImageResource(imageDice)
            }
            3 -> {
                findViewById<ImageView>(R.id.comDice3).setImageResource(imageDice)
            }
            4 -> {
                findViewById<ImageView>(R.id.comDice4).setImageResource(imageDice)
            }
            5 -> {
                findViewById<ImageView>(R.id.comDice5).setImageResource(imageDice)
            }
        }
    }

    //set dice images clickable
    fun check(value:Boolean){
        findViewById<ImageView>(R.id.userDice1).isClickable = value
        findViewById<ImageView>(R.id.userDice2).isClickable = value
        findViewById<ImageView>(R.id.userDice3).isClickable = value
        findViewById<ImageView>(R.id.userDice4).isClickable = value
        findViewById<ImageView>(R.id.userDice5).isClickable = value
    }

    fun reroll(dice:Array<Int>): Array<Int> {
        var img :Int = R.drawable.design1
        findViewById<ImageView>(R.id.userDice1).setOnClickListener{
            findViewById<ImageView>(R.id.userDice1).setImageResource(img)
            dice[0]=0
        }
        findViewById<ImageView>(R.id.userDice2).setOnClickListener{
            findViewById<ImageView>(R.id.userDice2).setImageResource(img)
            dice[1]=0
        }
        findViewById<ImageView>(R.id.userDice3).setOnClickListener{
            findViewById<ImageView>(R.id.userDice3).setImageResource(img)
            dice[2]=0
        }
        findViewById<ImageView>(R.id.userDice4).setOnClickListener{
            findViewById<ImageView>(R.id.userDice4).setImageResource(img)
            dice[3]=0
        }
        findViewById<ImageView>(R.id.userDice5).setOnClickListener{
            findViewById<ImageView>(R.id.userDice5).setImageResource(img)
            dice[4]=0
        }
        return dice
    }

    //winner popup window
    fun win(score:Int,win:Int){
        var playerScore = score
        var winningPoints = win
        if (playerScore >= winningPoints) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("20210144-Maleesha Kawshan")
            builder.setMessage("“You win!”")
            builder.setPositiveButton("OK", null)
            val dialog = builder.create()
            dialog.show()
        }else{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("20210144-Maleesha Kawshan")
            builder.setMessage("“You lose”")
            builder.setPositiveButton("OK", null)
            val dialog = builder.create()
            dialog.show()
        }
        //crate game is not playable
        findViewById<Button>(R.id.throwBtn).isEnabled = false
        findViewById<Button>(R.id.rerollBtn).isEnabled = false
        findViewById<Button>(R.id.scoreBtn).isEnabled = false
    }

}