package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    enum class playerTurn
    {
        CROSS,
        CIRCLE
    }

    var firstTurn = playerTurn.CROSS
    var currentTurn = playerTurn.CROSS
    var xWins = 0
    var oWins = 0
    var boardOfBtns = mutableListOf<Button>()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBoard()
    }

    private fun initBoard()
    {
        boardOfBtns.add(binding.btn1A)
        boardOfBtns.add(binding.btn2A)
        boardOfBtns.add(binding.btn3A)
        boardOfBtns.add(binding.btn1B)
        boardOfBtns.add(binding.btn2B)
        boardOfBtns.add(binding.btn3B)
        boardOfBtns.add(binding.btn1C)
        boardOfBtns.add(binding.btn2C)
        boardOfBtns.add(binding.btn3C)
    }

    //Click detection
    fun btnClicked(view: View)
    {
        if(view !is Button)
            return

        addToBoard(view)

        //Win output + score keeping
        if(winCheck(CROSS))
        {
            xWins++
            winner("X has won, O sucks")
        }
        if(winCheck(CIRCLE))
        {
            oWins++
            winner("O has won, X sucks")
        }
        if(fullBoard())
        {
            winner("Tie, you both suck")
        }
    }

    //Win detection
    private fun winCheck(s: String): Boolean
    {
        //Horizontal wins
        if(match(binding.btn1A, s) && match(binding.btn2A, s) && match(binding.btn3A, s))
            return true
        if(match(binding.btn1B, s) && match(binding.btn2B, s) && match(binding.btn3B, s))
            return true
        if(match(binding.btn1C, s) && match(binding.btn2C, s) && match(binding.btn3C, s))
            return true

        //Vertical wins
        if(match(binding.btn1A, s) && match(binding.btn1B, s) && match(binding.btn1C, s))
            return true
        if(match(binding.btn2A, s) && match(binding.btn2B, s) && match(binding.btn2C, s))
            return true
        if(match(binding.btn3A, s) && match(binding.btn3B, s) && match(binding.btn3C, s))
            return true

        //Diagonal wins
        if(match(binding.btn1A, s) && match(binding.btn2B, s) && match(binding.btn3C, s))
            return true
        if(match(binding.btn3A, s) && match(binding.btn2B, s) && match(binding.btn1C, s))
            return true

        return false
    }

    private fun match(button: Button, symbol : String) = button.text == symbol

    //Winner dialog
    private fun winner(title: String)
    {
        val message = "\nX's Score: $xWins\n\nO's Score: $oWins"

        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Reset")
            { _,_ ->
                resetBoard()
            }
            .setCancelable(false)
            .show()
    }

    //Auto board reset
    private fun resetBoard()
    {
        for(button in boardOfBtns)
        {
            button.text = ""
        }
        //First turn switches after a game
        if(firstTurn == playerTurn.CROSS)
            firstTurn = playerTurn.CIRCLE
        else if (firstTurn == playerTurn.CIRCLE)
            firstTurn = playerTurn.CROSS

        currentTurn = firstTurn
        setTurnTxtBox()
    }

    //Full board detection
    private fun fullBoard(): Boolean
    {
        for(button in boardOfBtns)
        {
            if(button.text == "")
                return false
        }

        return true
    }

    companion object
    {
        const val CROSS = "X"
        const val CIRCLE = "O"
    }

    //Indicates a players box selection
    private fun addToBoard(button: Button)
    {
        if(button.text != "")
            return
        if(currentTurn == playerTurn.CIRCLE)
        {
            button.text = CIRCLE
            currentTurn = playerTurn.CROSS
        }
        else if(currentTurn == playerTurn.CROSS)
        {
            button.text = CROSS
            currentTurn = playerTurn.CIRCLE
        }

        setTurnTxtBox()
    }

    //Displays the current turn
    private fun setTurnTxtBox()
    {
        var turn = ""

        if(currentTurn == playerTurn.CROSS)
            turn = "Current Turn: $CROSS"
        else if(currentTurn == playerTurn.CIRCLE)
            turn = "Current Turn: $CIRCLE"

        binding.txtPlayer.text = turn
    }

}