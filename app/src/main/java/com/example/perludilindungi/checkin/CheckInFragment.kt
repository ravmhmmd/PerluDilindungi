package com.example.perludilindungi.checkin

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.example.perludilindungi.ApiService
import com.example.perludilindungi.R
import com.example.perludilindungi.data.*
import com.example.perludilindungi.databinding.FragmentCheckInBinding
import com.example.perludilindungi.list.FacilityListFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.zxing.integration.android.IntentIntegrator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CheckInFragment : Fragment() {

    lateinit var buttonScan : Button
    lateinit var intentIntegrator : IntentIntegrator

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var ivImg : ImageView
    private lateinit var tvJdg : TextView
    private lateinit var tvRsn : TextView

    private lateinit var qrCode : String
    private var latitude : Double = 0.0
    private var longitude: Double = 0.0

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentCheckInBinding>(inflater,
            R.layout.fragment_check_in,container,false)

        intentIntegrator = IntentIntegrator.forSupportFragment(this)

        ivImg = binding.imgPc
        tvJdg = binding.txtJdgPc
        tvRsn = binding.txtRsnPc
        buttonScan = binding.btnScan

        buttonScan.setOnClickListener {
            intentIntegrator.setOrientationLocked(false)
            intentIntegrator.setPrompt("Scan QR Code")
            intentIntegrator.setBeepEnabled(false)
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            intentIntegrator.initiateScan()

            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
            getLastKnownLocation()
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (intentResult != null) {
            if (intentResult.contents == null) {
                Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                qrCode = intentResult.contents
                checkIn()
            }
        }
    }

    private fun checkIn() {
        val checkInRequest = qrCode?.let { itCont ->
            latitude?.let { itLat ->
                longitude?.let { itLong ->
                    CheckInRequest(
                        itCont,
                        itLat,
                        itLong
                    )
                }
            }
        }

        val ciApiService = CheckInApiService()

        if (checkInRequest != null) {
            Log.i("CheckPBD", "mau cek 1")
            ciApiService.doCheckIn(checkInRequest) {
                if (it != null) {
                    Log.i("CheckPBD", "mau cek 2")
                    if (it.data != null) {
                        Log.i("CheckPBD", "mau cek 3")
                        tvRsn.text = it.data.reason

                        if (it.data.userStatus == "green" || it.data.userStatus == "yellow") {
                            Log.i("CheckPBD", "mau cek 4")
                            tvJdg.text = "Berhasil"
                            ivImg.setImageResource(R.drawable.ready_icon)
                        } else {
                            tvJdg.text = "Gagal"
                            ivImg.setImageResource(R.drawable.unready_icon)
                        }
                    }
                }
            }
        }
    }

//    private fun doCheckInReq() {
//
//        fun checkIn(checkInData : CheckInRequest){
//            val retrofit = Retrofit.Builder()
//                .baseUrl(FacilityListFragment.url)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//            val service = retrofit.create(ApiService::class.java).checkIn(checkInData)
//
//            service.enqueue(
//                object : Callback<CheckInResponse> {
//                    override fun onResponse(
//                        call: Call<CheckInResponse>,
//                        response: Response<CheckInResponse>
//                    ) {
//                        val res = response.body()
//
//                        if (res!!.data != null) {
//                            tvRsn.text = res.data!!.reason
//
//                            if (res.data!!.userStatus == "green" || res.data!!.userStatus == "yellow") {
//                                tvJdg.text = "Berhasil"
//                                ivImg.setImageResource(R.drawable.ready_icon)
//                            } else {
//                                tvJdg.text = "Gagal"
//                                ivImg.setImageResource(R.drawable.unready_icon)
//                            }
//                        } else {
//                            if (res == null) {
//                                Toast.makeText(requireContext(),"Response kosong",Toast.LENGTH_SHORT).show()
//                            } else {
//                                Toast.makeText(requireContext(),res.success.toString(),Toast.LENGTH_SHORT).show()
//                            }
//                        }
//
//                        CheckInResponse(res.success,res.code,res.message,res.data)
//                    }
//
//                    override fun onFailure(call: Call<CheckInResponse>, t: Throwable) {
//                        CheckInResponse(null,null,null,null)
//                    }
//                })
//        }
//
//        val checkInData = CheckInRequest(
//            qrCode = qrCode,
//            latitude = latitude,
//            longitude = longitude
//        )
//
//        if (checkInData != null) {
//            checkIn(checkInData)
//        }
//    }
}