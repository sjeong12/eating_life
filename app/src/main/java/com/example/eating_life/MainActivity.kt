package com.example.eating_life

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer
import kotlin.Array

class MainActivity : AppCompatActivity() {
    var dbHelper : DBHelper? = null
    var menu_list = arrayListOf<menu_item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbHelper = DBHelper(this)
        menu_list = dbHelper!!.selectAll()
        if (menu_list.size == 0) {
            val menu_array = arrayOf("치킨", "피자", "파스타", "자장면", "짬뽕", "뼈해장국", "순대국밥", "회덮밥")
            for (i in 0..(menu_array.size - 1)) {
                dbHelper!!.insertRecord(menu_array[i])
            }
            menu_list = dbHelper!!.selectAll()
        }
        val slide_in_anim = AnimationUtils.loadAnimation(this, R.anim.slide_in)
        val slide_out_anim = AnimationUtils.loadAnimation(this, R.anim.slide_out)
        shuffle(menu_list)
        addMenu(menu_list)
        button.setOnClickListener {
            button.setEnabled(false)
            slotMachine.setInAnimation(slide_in_anim)
            slotMachine.setOutAnimation(slide_out_anim)
            var speed = 30
            slotMachine.setFlipInterval(speed)
            slotMachine.startFlipping()
            timer(period = 100)
            {
                speed += 10 //* (speed / 100 + 1)
                slotMachine.setFlipInterval(speed)
                if (speed >= 470)
                {
                    cancel()
                    slotMachine.stopFlipping()
                }
            }
            Handler().postDelayed({
                button.setEnabled(true)
                setupLottie()
            }, 4400)
        }

        floatingActionButton.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        /*
        val url = "kakaomap://search?q=맛집&p=37.537229,127.005515"

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent);
        */
    }

    fun shuffle(menu : ArrayList<menu_item>)
    {
        val random = Random()
        var tmp: String
        var rn: Int
        for (i in 0..(menu.size-1))
        {
            rn = random.nextInt(menu.size - i) + i
            tmp = menu[i].name
            menu[i].name = menu[rn].name
            menu[rn].name = tmp
        }
    }

    fun addMenu(menu : ArrayList<menu_item>) {
        for (i in 0..(menu.size-1)) {
            val flip_text = TextView(this)
            flip_text.setGravity(Gravity.CENTER)
            flip_text.setTextSize(48f)
            flip_text.setTypeface(Typeface.DEFAULT_BOLD)
            flip_text.setTextColor(Color.DKGRAY)
            flip_text.setBackgroundColor(Color.WHITE)
            flip_text.setText(menu.get(i).name)
            slotMachine.addView(flip_text)
        }
    }

    fun setupLottie(){
            lottie_confetti.apply {
            setAnimation("confetti.json")
            playAnimation()
        }
    }
}