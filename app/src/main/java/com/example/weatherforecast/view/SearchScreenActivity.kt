package com.example.weatherforecast.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecast.databinding.ActivitySearchScreenBinding
import com.example.weatherforecast.databinding.ActivityWeatherForecastBinding
import com.example.weatherforecast.sharedpreferences.UserSearchHistory
import com.example.weatherforecast.viewmodel.SearchHistoryAdapter

class SearchScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchScreenBinding
    private lateinit var searchHistoryList: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // onCreate içinde çağrı yapın
        searchHistoryList = UserSearchHistory.getSearchHistory(this)

        val searchHistoryAdapter = SearchHistoryAdapter(searchHistoryList) { selectedItem ->
            // Seçilen öğeye tıklandığında yapılacak işlemler
            binding.editTextSearch.setText(selectedItem)
            // Diğer işlemleri buraya ekleyebilirsiniz
        }

        // RecyclerView için layoutManager ve adapter ayarları
        binding.recyclerViewSearchHistory.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewSearchHistory.adapter = searchHistoryAdapter

        binding.btnSearch.setOnClickListener {
            val searchedItem = binding.editTextSearch.text.toString()
            UserSearchHistory.addSearchTerm(this, searchedItem)

            // Yeni öğeyi listenin başına ekle
            searchHistoryList.add(0, searchedItem)

            // Eğer listenin boyutu 7'den büyükse, en eski öğeyi sil
            if (searchHistoryList.size > 7) {
                searchHistoryList.removeAt(7)
            }

            // RecyclerView'ı güncelle
            searchHistoryAdapter.notifyDataSetChanged()

            // EditText'i temizle
            binding.editTextSearch.text.clear()
        }


    }
}
