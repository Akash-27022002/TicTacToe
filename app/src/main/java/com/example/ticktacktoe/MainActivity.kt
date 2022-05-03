package com.example.ticktacktoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),View.OnClickListener{
   private var TURN_COUNT = 0
   private var _player = true
   private var boardStatus = Array(3){IntArray(3)}

   private lateinit var board :Array<Array<Button>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        board = arrayOf(
            arrayOf(button1,button2,button3),
            arrayOf(button4,button5,button6),
            arrayOf(button7,button8,button9)
        )
        for (i in board){
            for (button in i){
                button.setOnClickListener(this)
            }
        }

        intializeBoardStatus()
        reset.setOnClickListener{

            intializeBoardStatus()
        }



    }

    private fun intializeBoardStatus() {
        TURN_COUNT = 0
        _player = true
        updateDisplay("Player X turns")
        for (i in 0..2){
            for(j in 0 ..2){
               boardStatus[i][j] = -1
               board[i][j].isEnabled = true
               board[i][j].text = ""
            }
        }
    }


    override fun onClick(view: View) {
        when(view.id){
            R.id.button1 ->{
                updateValue(row = 0 , col = 0 , player = _player )
            }
            R.id.button2 ->{
                updateValue(row = 0 , col = 1 , player = _player )
            }
            R.id.button3 ->{
                updateValue(row = 0 , col = 2 , player = _player )
            }
            R.id.button4 ->{
                updateValue(row = 1 , col = 0 , player = _player )
            }
            R.id.button5 ->{
                updateValue(row = 1 , col = 1 , player = _player )
            }
            R.id.button6 ->{
                updateValue(row = 1 , col = 2 , player = _player )
            }
            R.id.button7 ->{
                updateValue(row = 2 , col = 0 , player = _player )
            }
            R.id.button8 ->{
                updateValue(row = 2 , col = 1 , player = _player )
            }
            R.id.button9 ->{
                updateValue(row = 2 , col = 2 , player =_player )
            }
        }
    }

    private fun updateValue(row: Int, col: Int, player: Boolean) {
        val text = if (player)"X" else "O"
        val value = if(player) 1 else 0
            board[row][col].apply {
                isEnabled = false
                setText(text)
                TURN_COUNT++
            }
        boardStatus[row][col] = value

        if(TURN_COUNT >= 5 && checkStatus()) {
            if(_player) updateDisplay("Player X Wins the Match")
            else updateDisplay("Player O Wins the Match")

            for (i in 0..2){
                for(j in 0 ..2){
                    if(board[i][j].isEnabled){
                        board[i][j].isEnabled = false
                    }
                }
            }
            setReset()
            return
        }
        _player  = !_player
        when {
            TURN_COUNT == 9 -> {
                updateDisplay("Game Draw")
                setReset()
            }
            _player -> updateDisplay("Player X turn")
            else -> updateDisplay("Player Y turn")
        }


    }

    private fun setReset() {
        reset.isEnabled = false
        Handler().postDelayed({
            intializeBoardStatus()
            reset.isEnabled = true
        },1500)
    }

    private fun updateDisplay(s: String) {
        displayTv.text = s
    }

    private fun checkStatus():Boolean{
        for (i in 0..2){
            if(boardStatus[i][0] == boardStatus[i][1] && boardStatus[i][0] == boardStatus[i][2] && boardStatus[i][0] != -1){
                return true
            }
        }
        for (i in 0..2){
            if(boardStatus[0][i] == boardStatus[1][i] && boardStatus[0][i] == boardStatus[2][i] && boardStatus[0][i] != -1){
                return true
            }
        }
        if((boardStatus[0][0] == boardStatus[1][1] && boardStatus[0][0] == boardStatus[2][2] && boardStatus[0][0] != -1)||
            boardStatus[0][2] == boardStatus[1][1] && boardStatus[0][2] == boardStatus[2][0] && boardStatus[0][2] != -1){
            return true
        }
        return false
    }
}