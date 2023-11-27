package com.example.weatherforecast

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.weatherforecast.databinding.ActivityMainBinding

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var  permissionResult:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val timer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                checkAndShowAlertDialog()
            }
        }

        timer.start()
    }

    private fun checkAndShowAlertDialog() {
        if (UserPermission.getPermission(this@SplashScreen) == null) {
            showListAlertDialog()

        } else {
            Log.d("CONTROL2","${UserPermission.getPermission(this@SplashScreen)}")
            moveToNextActivity()
        }
    }

    private fun showListAlertDialog() {
        val items = arrayOf("Evet", "Hayır")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Uygulamanın konumunuza erişmesini istiyor musunuz?")
            .setItems(items) { dialog, which ->
                val selectedOption = items[which]
                UserPermission.savePermission(this, selectedOption)
                dialog.dismiss()

                // Dialog kutusu işlemleri tamamlandıktan sonra diğer aktiviteye geçiş yap
                Log.d("CONTROL1","${UserPermission.getPermission(this@SplashScreen)}")
                moveToNextActivity()
            }

        builder.show()
    }

    private fun moveToNextActivity() {
        val intent = Intent(this@SplashScreen, WeatherForecast::class.java)
        permissionResult=UserPermission.getPermission(this@SplashScreen).toString()
        Log.d("CONTROL3",permissionResult)
        intent.putExtra("PERMISSION", permissionResult)
        startActivity(intent)
        finish()
    }
}
