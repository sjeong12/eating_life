package com.example.eating_life

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url = "kakaomap://search?q=맛집&p=37.537229,127.005515";

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}