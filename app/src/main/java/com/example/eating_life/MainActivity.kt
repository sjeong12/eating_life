package com.example.eating_life

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val menu_array = arrayOf("치킨", "피자", "파스타", "자장면", "짬뽕", "뼈해장국", "순대국밥", "회덮밥")
        val random = Random()

        button.setOnClickListener {
            val num = random.nextInt(menu_array.size)
            val menu = menu_array.get(num)
            textView.setText(menu)
        }
        /*
        val url = "kakaomap://search?q=맛집&p=37.537229,127.005515"

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent);
        */
    }
}