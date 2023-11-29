package com.example.weatherforecast.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.weatherforecast.R
import com.example.weatherforecast.databinding.FragmentPermissionDeniedBinding
import com.example.weatherforecast.sharedpreferences.UserPermission
import com.example.weatherforecast.view.WeatherFragment.Companion.newInstance
import com.google.android.gms.location.LocationServices

class PermissionDeniedFragment : Fragment() {
    private var coordinateList= doubleArrayOf(0.0,0.0)
    private lateinit var binding: FragmentPermissionDeniedBinding
    companion object {
        const val MY_PERMISSION_REQUEST_CODE = 123 // Kendi istediğiniz bir sayı olabilir
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPermissionDeniedBinding.inflate(inflater, container, false)
        binding.btnOpenLocation.setOnClickListener {
            getPermission()
        }
        return binding.root
    }

    private fun getPermission(){
        val context=requireContext()
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && UserPermission.getFlagcontext(
                context
            ) ==null) {
            UserPermission.saveFlagcontext(context, false)
            // İzin verilmemişse izin talep et
            getPermission()
        }
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            MY_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //Permission allowed
        val context = requireContext()
        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // İzin verildiyse izin durumunu kaydet ve diğer aktiviteye geç
                UserPermission.savePermission(context, true)
                getLocation() // İzin verildiğinde getLocation() fonksiyonunu çağır
            }
        }
    }


    private fun getLocation(){
        val context = requireContext()
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
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
                coordinateList[0]=latitude
                coordinateList[1]=longitude
                changeFragment()
            }
        }


    }

    private fun changeFragment() {
        val weatherFragment=newInstance(coordinateList,"fragment")
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container, weatherFragment, "WeatherFragment")
            ?.addToBackStack(null)
            ?.commit()
    }

}