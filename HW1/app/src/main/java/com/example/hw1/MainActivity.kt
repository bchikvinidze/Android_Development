package com.example.hw1

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    var scoreComputerNumber = 0
    var scorePlayerNumber = 0
    var scoreComputer : TextView? = null
    var scorePlayer : TextView? = null

    // rogori praqtikaa rom aqve yofiliyo es findviewbyid-ebi globalur cvladebad?
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var buttonRock = findViewById<Button>(R.id.rock)
        var buttonPaper = findViewById<Button>(R.id.paper)
        var buttonScissors = findViewById<Button>(R.id.scissor)

        var imgPlayer : ImageButton? = null //findViewById<ImageButton>(R.id.imagePlayer)
        var imgComputer : ImageButton? = null //findViewById<ImageButton>(R.id.imageComputer)
        var buttonRockGame : Button? = null //findViewById<ImageButton>(R.id.imagePlayer)
        var buttonPaperGame : Button? = null //findViewById<ImageButton>(R.id.imageComputer)
        var buttonScissorsGame : Button? = null //findViewById<TextView>(R.id.scoreComputerGame)

        var gameStarted = false
        var rockImg: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.rock, null)
        var paperImg: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.paper, null)
        var scissorImg: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.scissors, null)

        buttonRock.setOnClickListener {
            setContentView(R.layout.gameplay)
            imgPlayer = findViewById<ImageButton>(R.id.imagePlayer) //eseni rogor gavitano calke funqciashi??
            imgComputer = findViewById<ImageButton>(R.id.imageComputer)
            scoreComputer = findViewById<TextView>(R.id.scoreComputerGame)
            scorePlayer = findViewById<TextView>(R.id.scorePlayerGame)
            buttonRockGame = findViewById<Button>(R.id.rockGame)
            buttonPaperGame = findViewById<Button>(R.id.paperGame)
            buttonScissorsGame = findViewById<Button>(R.id.scissorGame)

            assignListeners(buttonRockGame,buttonPaperGame,buttonScissorsGame,imgPlayer,imgComputer,rockImg,paperImg,scissorImg)
            listenerActivity(imgPlayer, "rock", imgComputer, rockImg, paperImg, scissorImg)
        }

        buttonPaper.setOnClickListener {
            setContentView(R.layout.gameplay)
            imgPlayer = findViewById<ImageButton>(R.id.imagePlayer)
            imgComputer = findViewById<ImageButton>(R.id.imageComputer)
            scoreComputer = findViewById<TextView>(R.id.scoreComputerGame)
            scorePlayer = findViewById<TextView>(R.id.scorePlayerGame)
            buttonRockGame = findViewById<Button>(R.id.rockGame)
            buttonPaperGame = findViewById<Button>(R.id.paperGame)
            buttonScissorsGame = findViewById<Button>(R.id.scissorGame)

            assignListeners(buttonRockGame,buttonPaperGame,buttonScissorsGame,imgPlayer,imgComputer,rockImg,paperImg,scissorImg)
            listenerActivity(imgPlayer, "paper", imgComputer, rockImg, paperImg, scissorImg)
        }

        buttonScissors.setOnClickListener {
            setContentView(R.layout.gameplay)
            imgPlayer = findViewById<ImageButton>(R.id.imagePlayer)
            imgComputer = findViewById<ImageButton>(R.id.imageComputer)
            scoreComputer = findViewById<TextView>(R.id.scoreComputerGame)
            scorePlayer = findViewById<TextView>(R.id.scorePlayerGame)
            buttonRockGame = findViewById<Button>(R.id.rockGame)
            buttonPaperGame = findViewById<Button>(R.id.paperGame)
            buttonScissorsGame = findViewById<Button>(R.id.scissorGame)

            assignListeners(buttonRockGame,buttonPaperGame,buttonScissorsGame,imgPlayer,imgComputer,rockImg,paperImg,scissorImg)
            listenerActivity(imgPlayer, "scissor", imgComputer, rockImg, paperImg, scissorImg)
        }

    }

    fun listenerActivity(imgPlayer : ImageButton?, chosen : String, imgComputer : ImageButton?,
                         rockImg : Drawable?, paperImg : Drawable?, scissorImg : Drawable?){
        if(chosen == "rock") {
            imgPlayer?.setImageDrawable(rockImg)
        } else if(chosen == "paper"){
            imgPlayer?.setImageDrawable(paperImg)
        } else {
            imgPlayer?.setImageDrawable(scissorImg)
        }
        val rand = (0..2).random()
        if(rand == 0){
            imgComputer?.setImageDrawable(rockImg)
        } else if(rand == 1){
            imgComputer?.setImageDrawable(paperImg)
        } else {
            imgComputer?.setImageDrawable(scissorImg)
        }

        //deciding on scores logic
        if(rand == 0){
            if(chosen == "rock"){
                scoreComputer?.setTextColor(Color.parseColor("yellow"))
                scorePlayer?.setTextColor(Color.parseColor("yellow"))
            } else if(chosen == "paper"){
                scorePlayerNumber++;
                scorePlayer?.text = scorePlayerNumber.toString()
                scoreComputer?.setTextColor(Color.parseColor("black"))
                scorePlayer?.setTextColor(Color.parseColor("green"))
            } else {
                scoreComputerNumber++
                scoreComputer?.text = scoreComputerNumber.toString()
                scoreComputer?.setTextColor(Color.parseColor("green"))
                scorePlayer?.setTextColor(Color.parseColor("black"))
            }
        } else if(rand == 1){
            if(chosen == "rock"){
                scoreComputerNumber++
                scoreComputer?.text = scoreComputerNumber.toString()
                scoreComputer?.setTextColor(Color.parseColor("green"))
                scorePlayer?.setTextColor(Color.parseColor("black"))
            } else if(chosen == "paper"){
                scoreComputer?.setTextColor(Color.parseColor("#FFFF00"))
                scorePlayer?.setTextColor(Color.parseColor("#FFFF00"))
            } else {
                scorePlayerNumber++;
                scorePlayer?.text = scorePlayerNumber.toString()
                scoreComputer?.setTextColor(Color.parseColor("black"))
                scorePlayer?.setTextColor(Color.parseColor("green"))
            }
        } else {
            if(chosen == "rock"){
                scorePlayerNumber++;
                scorePlayer?.text = scorePlayerNumber.toString()
                scoreComputer?.setTextColor(Color.parseColor("black"))
                scorePlayer?.setTextColor(Color.parseColor("green"))
            } else if(chosen == "paper"){
                scoreComputerNumber++;
                scoreComputer?.text = scorePlayerNumber.toString()
                scoreComputer?.setTextColor(Color.parseColor("green"))
                scorePlayer?.setTextColor(Color.parseColor("black"))
            } else {
                scoreComputer?.setTextColor(Color.parseColor("#FFFF00"))
                scorePlayer?.setTextColor(Color.parseColor("#FFFF00"))
            }
        }
    }

    fun assignListeners(buttonRockGame : Button?, buttonPaperGame : Button?, buttonScissorsGame : Button?,
                        imgPlayer : ImageButton?, imgComputer: ImageButton?, rockImg: Drawable?, paperImg: Drawable?,
                        scissorImg: Drawable?){
        buttonRockGame?.setOnClickListener {
            listenerActivity(imgPlayer, "rock",imgComputer, rockImg, paperImg, scissorImg)
        }

        buttonPaperGame?.setOnClickListener {
            listenerActivity(imgPlayer, "paper", imgComputer, rockImg, paperImg, scissorImg)
        }

        buttonScissorsGame?.setOnClickListener {
            listenerActivity(imgPlayer, "scissor", imgComputer, rockImg, paperImg, scissorImg)
        }
    }

  /*  fun setup(imgPlayer : ImageButton?, imgComputer : ImageButton?, scoreComputer : TextView?, scorePlayer : TextView?,
                        buttonRockGame : Button?, buttonPaperGame : Button?, buttonScissorsGame : Button?,
                        rockImg : Drawable?, paperImg : Drawable?, scissorImg : Drawable?){
        setContentView(R.layout.gameplay)

        var imgPlayer2 = imgPlayer
        var imgComputer2 = imgComputer
        var scoreComputer2 = scoreComputer
        var scorePlayer2 = scorePlayer
        var buttonRockGame2 = buttonRockGame
        var buttonPaperGame2 = buttonPaperGame
        var buttonScissorsGame2 = buttonScissorsGame

        imgPlayer2 = findViewById<ImageButton>(R.id.imagePlayer)
        imgComputer2 = findViewById<ImageButton>(R.id.imageComputer)
        scoreComputer2 = findViewById<TextView>(R.id.scoreComputerGame)
        scorePlayer2 = findViewById<TextView>(R.id.scorePlayerGame)
        buttonRockGame2 = findViewById<Button>(R.id.rockGame)
        buttonPaperGame2 = findViewById<Button>(R.id.paperGame)
        buttonScissorsGame2 = findViewById<Button>(R.id.scissorGame)
    } */

}