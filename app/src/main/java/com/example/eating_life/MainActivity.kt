package com.example.eating_life

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer
import kotlin.Array

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val menu_array = arrayOf("치킨", "피자", "파스타", "자장면", "짬뽕", "뼈해장국", "순대국밥", "회덮밥")
        val slide_in_anim = AnimationUtils.loadAnimation(this, R.anim.slide_in)
        val slide_out_anim = AnimationUtils.loadAnimation(this, R.anim.slide_out)
        shuffle(menu_array)
        addMenu(menu_array)
        button.setOnClickListener {
            slotMachine.setInAnimation(slide_in_anim)
            slotMachine.setOutAnimation(slide_out_anim)
            var speed = 50
            slotMachine.setFlipInterval(speed)
            slotMachine.startFlipping()
            timer(period = 100)
            {
                speed += 10 //* (speed / 100 + 1)
                slotMachine.setFlipInterval(speed)
                if (speed >= 500)
                {
                    cancel()
                    slotMachine.stopFlipping()
                }
            }
        }

        /*
        val url = "kakaomap://search?q=맛집&p=37.537229,127.005515"

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent);
        */
    }

    fun shuffle(menu : Array<String>)
    {
        val random = Random()
        var tmp: String
        var rn: Int
        for (i in 0..(menu.size-1))
        {
            rn = random.nextInt(menu.size - i) + i
            tmp = menu[i]
            menu[i] = menu[rn]
            menu[rn] = tmp
        }
    }

    fun addMenu(menu : Array<String>) {
        for (i in 0..(menu.size-1)) {
            val flip_text = TextView(this)
            flip_text.setGravity(Gravity.CENTER)
            flip_text.setTextSize(40F)
            flip_text.setText(menu.get(i))
            slotMachine.addView(flip_text)
        }
    }
}