package com.example.weatherforecast.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecast.R
import com.example.weatherforecast.databinding.ActivitySearchScreenBinding
import com.example.weatherforecast.model.LatLong
import com.example.weatherforecast.sharedpreferences.UserPermission
import com.example.weatherforecast.sharedpreferences.UserSearchHistory
import com.example.weatherforecast.viewmodel.SearchHistoryAdapter

class SearchScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchScreenBinding
    private lateinit var searchHistoryList: MutableList<String>
    private lateinit var popupWindow: PopupWindow
    private var selectedCityName=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.editTextSearch.clearFocus()
        binding.editTextSearch.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.recyclerViewSearchHistory.visibility = View.VISIBLE
            } else {
                binding.recyclerViewSearchHistory.visibility = View.GONE
            }
        }

        searchHistoryList = UserSearchHistory.getSearchHistory(this)





        binding.btnSearch.setOnClickListener {
            val searchedItem = binding.editTextSearch.text.toString()
            if(searchedItem.length>2){
                showPopup()
            }
            UserSearchHistory.addSearchTerm(this, searchedItem)
            addToList(searchedItem)


        }


    }

    fun addToList(searchedItem:String){
        val searchHistoryAdapter = SearchHistoryAdapter(searchHistoryList) { selectedItem ->
            binding.editTextSearch.setText(selectedItem)
        }
        searchHistoryList.add(0, searchedItem)
        binding.recyclerViewSearchHistory.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewSearchHistory.adapter = searchHistoryAdapter

        if (searchHistoryList.size > 7) {
            searchHistoryList.removeAt(7)
        }

        searchHistoryAdapter.notifyDataSetChanged()

        binding.editTextSearch.text.clear()
    }

    private fun showPopup() {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.popup_layout, null)

        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT

        popupWindow = PopupWindow(popupView, width, height, true)


        popupWindow.showAsDropDown(binding.recyclerViewSearchHistory, 0, 0)
        popupWindow.inputMethodMode = PopupWindow.INPUT_METHOD_NEEDED

        val data = arrayOf("ANKARA", "İSTANBUL", "İZMİR", "BURSA",
            "ADANA","GAZİANTEP","KAYSERİ","KONYA","ANTALYA","MERSİN",
            "DİYARBAKIR","ESKİŞEHİR","DENİZLİ","SAMSUN","ŞANLIURFA")

        val cityLatLong = listOf(
            LatLong("ANKARA",39.9334, 32.8597),
            LatLong("İSTANBUL", 41.0082, 28.9784),
            LatLong("İZMİR", 38.4192, 27.1287),
            LatLong("BURSA", 40.1824, 29.0671),
            LatLong("ADANA", 37.0000, 35.3213),
            LatLong("GAZİANTEP", 37.0662, 37.3833),
            LatLong("KAYSERİ", 38.7338, 35.4884),
            LatLong("KONYA", 37.8746, 32.4933),
            LatLong("ANTALYA", 36.8969, 30.7133),
            LatLong("MERSİN", 36.8011, 34.6178),
            LatLong("DİYARBAKIR", 37.9204, 40.2306),
            LatLong("ESKİŞEHİR", 39.7767, 30.5206),
            LatLong("DENİZLİ", 37.7749, 29.0875),
            LatLong("SAMSUN", 41.279703, 36.336067),
            LatLong("ŞANLIURFA", 37.1591, 38.7969)
        )

        val listView: ListView = popupView.findViewById(R.id.listView)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = data[position]
            println("Selected Item: $selectedItem")
            popupWindow.dismiss()
            val intent = Intent(this@SearchScreenActivity, WeatherForecastActivity::class.java)
            intent.putExtra("callerActivity", "SplashScreen")
            intent.putExtra("CITYLAT", cityLatLong.get(position).latitude)
            intent.putExtra("CITYLONG", cityLatLong.get(position).longitude)
            UserPermission.savePermission(this@SearchScreenActivity,true)
            selectedCityName=cityLatLong.get(position).city
            UserSearchHistory.addSearchTerm(this, selectedCityName)
            addToList(selectedCityName)
            startActivity(intent)


        }
    }


}
