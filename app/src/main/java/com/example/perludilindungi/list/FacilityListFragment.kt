package com.example.perludilindungi.list

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perludilindungi.ApiService
import com.example.perludilindungi.R
import com.example.perludilindungi.data.*
import com.example.perludilindungi.database.Bookmark
import com.example.perludilindungi.databinding.FragmentListBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList


class FacilityListFragment() : Fragment() {
    // your fragment parameter, a string

    var bookmarkData: List<Bookmark> = ArrayList()

    private lateinit var recyclerView: RecyclerView
    private lateinit var spinnerProvince: Spinner
    private lateinit var spinnerCity: Spinner
    private lateinit var search: Button

    private lateinit var adapterProvince: SpinnerAdapter
    private lateinit var adapterCity: SpinnerAdapter

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var latitude : Double = 0.0
    private var longitude: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = DataBindingUtil.inflate<FragmentListBinding>(inflater,R.layout.fragment_list,container,false)

        spinnerProvince = rootView.root.findViewById(R.id.spinner_province)
        spinnerCity = rootView.root.findViewById(R.id.spinner_city)
        search = rootView.root.findViewById(R.id.buttonSearch)
        recyclerView = rootView.root.findViewById(R.id.recyclerviewFaskes)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        return rootView.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        getLastKnownLocation()


        getProvinceData().observe(viewLifecycleOwner,
            { newProvinceData ->
                provinceData = newProvinceData

                adapterProvince = SpinnerAdapter(requireContext(), newProvinceData!!)
                spinnerProvince.adapter = adapterProvince

                if (provinceData.size > 0 ) {
                    Log.i("kode", provinceData.size.toString())
                    spinnerProvince.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                        override fun onNothingSelected(parent: AdapterView<*>?) {
                        }

                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            _provinceVal.value = provinceData[position]
                            val adapterFaskes = CustomAdapter(
                                requireContext(),
                                ArrayList(),
                                ArrayList(),
                                false
                            )
                            recyclerView.adapter = adapterFaskes
                        }
                    }
                }
            }
        )

        provinceVal.observe(viewLifecycleOwner,
            { newProvinceVal ->
                getCityData(spinnerProvince, newProvinceVal).observe(viewLifecycleOwner,
                    { newCityData ->
                        cityData = newCityData

                        adapterCity = SpinnerAdapter(requireContext(), newCityData!!)
                        spinnerCity.adapter = adapterCity

                        if (newCityData.size > 0 ) {
                            spinnerCity.onItemSelectedListener =
                                object : AdapterView.OnItemSelectedListener {
                                    override fun onNothingSelected(parent: AdapterView<*>?) {
                                    }

                                    override fun onItemSelected(
                                        parent: AdapterView<*>?,
                                        view: View?,
                                        position: Int,
                                        id: Long
                                    ) {
                                        _cityVal.value = cityData[position]
                                        val adapterFaskes = CustomAdapter(
                                            requireContext(),
                                            ArrayList(),
                                            ArrayList(),
                                            false
                                        )
                                        recyclerView.adapter = adapterFaskes
                                    }
                                }
                        }
                    }
                )
            }
        )

        cityVal.observe(viewLifecycleOwner,
            { newCityVal ->
                getFaskesList(_provinceVal.value!!, newCityVal).observe(viewLifecycleOwner,
                    { newFaskesData ->
                        val adapterFaskes = CustomAdapter(
                            requireContext(),
                            newFaskesData,
                            bookmarkData,
                            false
                        )
                        search.setOnClickListener {
                            recyclerView.adapter = adapterFaskes
                        }
                    }
                )
            }
        )
//        if(savedInstanceState != null) {
//            this.Bundle = savedInstanceState;
//        }
    }


    private fun getProvinceData(): LiveData<MutableList<String>> {
        val currentProvinceData = MutableLiveData<MutableList<String>>(ArrayList())

        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ApiService::class.java)
        val provinceCall = service.getProvinceList()

        provinceCall.enqueue(object : Callback<Province> {
            override fun onResponse(call: Call<Province>, response: Response<Province>) {
                if (response.code() == 200) {
                    var province = response.body()

                    var tempProvinceList = response.body()?.results as ArrayList<ResultsProvince>
                    var provinceList: MutableList<String> = ArrayList()
                    tempProvinceList.forEach {
                        provinceList.add(it.value.toString())
                    }
                    currentProvinceData.value = provinceList
                } else {

                }
            }

            override fun onFailure(call: Call<Province>, t: Throwable) {
                // Log error here since request failed
            }
        })
        return currentProvinceData
    }

    private fun getCityData(spinner: Spinner, provinceValue: String): LiveData<MutableList<String>> {
        val currentCityData = MutableLiveData<MutableList<String>>(ArrayList())

        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ApiService::class.java)
        val cityCall = service.getCityList(provinceValue)

        cityCall.enqueue(object : Callback<City> {
            override fun onResponse(call: Call<City>, response: Response<City>) {
                if (response.code() == 200) {
                    var tempCityList = response.body()?.results as ArrayList<ResultsCity>
                    var cityList: MutableList<String> = ArrayList()
                    tempCityList.forEach {
                        cityList.add(it.value.toString())
                    }
                    currentCityData.value = cityList
                } else {

                }
            }

            override fun onFailure(call: Call<City>, t: Throwable) {
                // Log error here since request failed
            }
        })

        return currentCityData;
    }

    private fun getFaskesList(provinceVal: String, cityVal: String): LiveData<MutableList<ResultsFaskes>> {
        val currentFaskesData = MutableLiveData<MutableList<ResultsFaskes>>(ArrayList())

        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ApiService::class.java)

        val faskesCall = service.getFaskesList(provinceVal, cityVal)

        faskesCall.enqueue(object : Callback<Faskes> {
            override fun onResponse(call: Call<Faskes>, response: Response<Faskes>) {
                if (response.code() == 200) {

                    faskesDataTemp = sortLatLong(response.body()?.data!!)

//                    currentFaskesData.value = response.body()?.data!!
                    currentFaskesData.value = faskesDataTemp
                }
            }

            override fun onFailure(call: Call<Faskes>, t: Throwable) {
                // Log error here since request failed
            }
        })

        return currentFaskesData
    }

    private fun sortLatLong(faskesDataToSort: MutableList<ResultsFaskes> ): MutableList<ResultsFaskes> {
//        val resFaskesData: MutableList<ResultsFaskes> = ArrayList()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        getLastKnownLocation()
        Log.d("lat, long", latitude.toString() +", "+ longitude.toString())
        Log.d("before sort", faskesDataToSort[0].distance.toString()+", "+faskesDataToSort[1].distance.toString()+", "+faskesDataToSort[2].distance.toString())

        for(item in faskesDataToSort){
            item.distance = item.latitude?.let { item.longitude?.let { it1 -> distance(latitude, longitude, it.toDouble(), it1.toDouble()) } }
        }

        faskesDataToSort.sortBy { it.distance }

        Log.d("after sort", faskesDataToSort[0].distance.toString() +", "+faskesDataToSort[1].distance.toString()+", "+faskesDataToSort[2].distance.toString())


        return faskesDataToSort

    }

    fun distance(fromLat: Double, fromLon: Double, toLat: Double, toLon: Double): Double {
        val radius = 6378137.0 // approximate Earth radius, *in meters*
        val deltaLat = toLat - fromLat
        val deltaLon = toLon - fromLon
        val angle = 2 * Math.asin(
            Math.sqrt(
                Math.pow(Math.sin(deltaLat / 2), 2.0) +
                        Math.cos(fromLat) * Math.cos(toLat) *
                        Math.pow(Math.sin(deltaLon / 2), 2.0)
            )
        )
        return radius * angle
    }

    fun getLastKnownLocation(){
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                (context as Activity?)!!,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                101
            )
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                latitude = location.latitude
                longitude = location.longitude
            }
        }
    }

    companion object {
        const val url = "https://perludilindungi.herokuapp.com/api/"
        var provinceData: MutableList<String> = ArrayList()
        var cityData: MutableList<String> = ArrayList()
        var faskesDataTemp: MutableList<ResultsFaskes> = ArrayList()
        val faskesData: MutableList<ResultsFaskes> = ArrayList()

        private val _provinceVal = MutableLiveData("")
        val provinceVal: LiveData<String>
            get() = _provinceVal

        private val _cityVal = MutableLiveData("")
        val cityVal: LiveData<String>
            get() = _cityVal
    }
}
