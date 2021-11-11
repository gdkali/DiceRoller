package com.gd.diceRoller

import android.animation.ValueAnimator
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

private const val TOTAL_DICE_COUNT = ""
private const val DICE_RESTORE_ONE = ""
private const val DICE_RESTORE_TWO = ""

private var firstDice: Int = 0
private var secondDice: Int = 0

class MainActivity : AppCompatActivity() {

    private lateinit var totalDiceCountText: TextView
    private lateinit var rollButton: Button

    private lateinit var diceImage: ImageView
    private lateinit var diceImageTwo: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Button
        rollButton = findViewById(R.id.rollButton)
        totalDiceCountText = findViewById(R.id.totalDiceValue)

        diceImage = findViewById(R.id.diceImage)
        diceImageTwo = findViewById(R.id.diceImageTwo)

        //  val totalDiceCountText: TextView = findViewById(R.id.totalDiceValue)

        // Make button interactive and run rollDice function
        rollButton.setOnClickListener { rollDice() }
    }

    // Class will accept dice side and generate random number
    class Dices(private val numSide: Int) {
        fun roll(): Int {
            return (1..numSide).random()
        }
    }


    private fun rollDice() {
        // Set specific number of dice sides
        val dice = Dices(6)

        // Get result from dice.roll
        firstDice = dice.roll()
        secondDice = dice.roll()

        // Initialize ImageView
//        val diceImage: ImageView = findViewById(R.id.diceImage);
//        val diceImageTwo: ImageView = findViewById(R.id.diceImageTwo)

        animateDice(diceImage, diceImageTwo)
        soundEffect()


        // As per rollResult value specific drawable will be loaded
        val drawableResource = when (firstDice) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }

        val drawableResourceSecond = when (secondDice) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }

        // Update Image to Screen
        diceImage.setImageResource(drawableResource)
        diceImageTwo.setImageResource(drawableResourceSecond)

        totalDiceCount(firstDice, secondDice)
    }

    private fun totalDiceCount(firstDice: Int, secondDice: Int) {
        val diceOnePlusDiceTwo = firstDice.plus(secondDice)
        Log.d("DCheckInt", firstDice.toString())
        totalDiceCountText.text = diceOnePlusDiceTwo.toString()
    }


    private fun soundEffect() {
        // Make Dice Sound when user click on Roll Button
        val diceAudioEffect = MediaPlayer.create(this, R.raw.roll)
        diceAudioEffect.start()
    }

    private fun animateDice(diceImage: ImageView, diceImageTwo: ImageView) {

        // Animate(Rotate) Both the Dice
        val valueAnimator = ValueAnimator.ofFloat(3600f)

        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float

            diceImage.rotation = value
            diceImageTwo.rotation = value
        }

        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.duration = 7_00
        valueAnimator.start()


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Total Dice Count
        outState.putString(TOTAL_DICE_COUNT, totalDiceCountText.text.toString())
        Log.d("TEXTS_CONSTANT", outState.getString(TOTAL_DICE_COUNT, ""))

        // Dice 1 Save Image Resource
        putInt()
        Log.d("CheckInt", firstDice.toString())
        // Dice 2 Save Image Resource

        putInt()
        Log.d("CheckIntSecond", secondDice.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        // Total Dice Count Restore
        totalDiceCountText.text = savedInstanceState.getString(TOTAL_DICE_COUNT, "")

        // Dice One Restore
        diceImage.setImageResource(
            savedInstanceState.getInt(
                DICE_RESTORE_ONE, when (firstDice) {
                    1 -> R.drawable.dice_1
                    2 -> R.drawable.dice_2
                    3 -> R.drawable.dice_3
                    4 -> R.drawable.dice_4
                    5 -> R.drawable.dice_5
                    else -> R.drawable.dice_6
                }
            )
        )

        Log.d(TOTAL_DICE_COUNT, savedInstanceState.getString(TOTAL_DICE_COUNT, ""))
        Log.d(DICE_RESTORE_ONE, savedInstanceState.getString(DICE_RESTORE_ONE, ""))

        // Dice Two Restore
        diceImageTwo.setImageResource(
            savedInstanceState.getInt(
                DICE_RESTORE_TWO, when (secondDice) {
                    1 -> R.drawable.dice_1
                    2 -> R.drawable.dice_2
                    3 -> R.drawable.dice_3
                    4 -> R.drawable.dice_4
                    5 -> R.drawable.dice_5
                    else -> R.drawable.dice_6
                }
            )
        )
        Log.d(DICE_RESTORE_TWO, savedInstanceState.getString(DICE_RESTORE_TWO, ""))
    }
}


private fun putInt() {

}


