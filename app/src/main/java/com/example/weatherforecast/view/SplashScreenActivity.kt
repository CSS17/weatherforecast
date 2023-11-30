package com.example.weatherforecast.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.weatherforecast.sharedpreferences.UserPermission
import com.example.weatherforecast.databinding.ActivityMainBinding
import com.google.android.gms.location.LocationServices

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var permissionResult = false
    private var coordinateList = HashMap<String, Double>()
    private val MY_PERMISSION_REQUEST_CODE = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        coordinateList["Latitude"] = 0.0
        coordinateList["Longitude"] = 0.0

        val timer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                //Checking and navigating the situation
                checkSituation()

            }
        }

        timer.start()
    }

    private fun moveToNextActivity() {
        val intent = Intent(this@SplashScreenActivity, WeatherForecastActivity::class.java)
        intent.putExtra("callerActivity", "SearchScreen")
        intent.putExtra("LATITUDE", coordinateList["Latitude"])
        intent.putExtra("LONGITUDE", coordinateList["Longitude"])
        startActivity(intent)
        finish()
    }

    private fun checkSituation() {
        // Getting Permission For first time
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && UserPermission.getFlagcontext(
                this@SplashScreenActivity
            ) ==null) {
            UserPermission.saveFlagcontext(this@SplashScreenActivity, false)
            // İzin verilmemişse izin talep et
            getPermission()
        }

        //Permission Already Allowed
        else if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && UserPermission.getFlagcontext(
                this@SplashScreenActivity
            ) !=null){
            // İzin zaten varsa izin durumunu kaydet ve diğer aktiviteye geç
            getLocation()
        }

        //Permission Already Denied
        else if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && UserPermission.getFlagcontext(
                this@SplashScreenActivity
            ) !=null){
            moveToNextActivity()
        }

    }

    private fun getPermission(){
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            MY_PERMISSION_REQUEST_CODE
        )
    }

    private fun getLocation(){
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                val latitude = location.latitude
                val longitude = location.longitude

                // İzin verildiyse latitude ve longitude değerlerini diğer aktiviteye geç
                coordinateList["Latitude"]=latitude
                coordinateList["Longitude"]=longitude
                moveToNextActivity()
            }
        }
    }


    // Function that processes permission request results
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //Permission allowed
        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // İzin verildiyse izin durumunu kaydet ve diğer aktiviteye geç
                permissionResult = true
                UserPermission.savePermission(this, permissionResult)
                getLocation()
            }
            // Permission Denied
            else{
                permissionResult = false
                UserPermission.savePermission(this, permissionResult)
                moveToNextActivity()
            }

        }


    }
}
