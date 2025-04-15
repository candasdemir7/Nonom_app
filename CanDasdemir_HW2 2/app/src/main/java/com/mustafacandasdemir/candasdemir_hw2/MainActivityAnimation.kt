package com.mustafacandasdemir.candasdemir_hw2

import android.media.MediaPlayer
import com.airbnb.lottie.LottieAnimationView
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.mustafacandasdemir.candasdemir_hw2.databinding.ActivityMainAnimationBinding
import com.mustafacandasdemir.candasdemir_hw2.databinding.ActivityMainBinding

class MainActivityAnimation : AppCompatActivity() {
    private lateinit var mediaPlayer: MediaPlayer


    lateinit var bindingMain: ActivityMainAnimationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMain = ActivityMainAnimationBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.nam)

        mediaPlayer.start()

        mediaPlayer.setOnCompletionListener {
            it.release()
        }

        // Binding ile Lottie animasyonu erişiyoruz
        val lottieAnimationView = bindingMain.lottieAnimationView
        lottieAnimationView.setAnimation("splash_animation.json") // Animasyon dosyasını assets klasöründen alır
        lottieAnimationView.playAnimation()

        // Animasyon bitiminde yeni ekrana geçiş yapıyoruz
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000) // Animasyonun süresine göre 3 saniye bekliyoruz
    }
}
