package com.hdk.radiokotlin.fragment

import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.hdk.radiokotlin.DatabaseHelper
import com.hdk.radiokotlin.MainActivity
import com.hdk.radiokotlin.MusicService
import com.hdk.radiokotlin.adapter.MyAdapter
import com.hdk.radiokotlin.R
import com.hdk.radiokotlin.data.RadioStation
import com.hdk.radiokotlin.databinding.FragmentRadioBinding
import java.io.IOException
import java.net.URL
import kotlin.concurrent.thread

class RadioFragment : Fragment(), OnItemSelectedListener, MyAdapter.ItemClickListener {

    private lateinit var binding: FragmentRadioBinding
    var items = mutableListOf<RadioStation>()

    var spList: Array<String> = arrayOf(
        "\uD83C\uDDFA\uD83C\uDDF8 US", // 미국
        "\uD83C\uDDE9\uD83C\uDDEA DE", // 독일
        "\uD83C\uDDE8\uD83C\uDDF3 CN", // 중국
        "\uD83C\uDDEB\uD83C\uDDF7 FR", // 프랑스
        "\uD83C\uDDF7\uD83C\uDDFA RU", // 러시아
        "\uD83C\uDDEC\uD83C\uDDF7 GR", // 그리스
        "\uD83C\uDDF2\uD83C\uDDFD MX", // 멕시코
        "\uD83C\uDDEE\uD83C\uDDF9 IT",
        "\uD83C\uDDEC\uD83C\uDDE7 GB",
        "\uD83C\uDDF5\uD83C\uDDF1 PL",
        "\uD83C\uDDE8\uD83C\uDDE6 CA",
        "\uD83C\uDDE7\uD83C\uDDF7 BR",
        "\uD83C\uDDEA\uD83C\uDDF8 ES",
        "\uD83C\uDDF3\uD83C\uDDF1 NL",
        "\uD83C\uDDE6\uD83C\uDDF7 AR",
        "\uD83C\uDDE8\uD83C\uDDED CH",
        "\uD83C\uDDF7\uD83C\uDDF4 RO",
        "\uD83C\uDDF9\uD83C\uDDF7 TR",
        "\uD83C\uDDE6\uD83C\uDDFA AU",
        "\uD83C\uDDF5\uD83C\uDDED PH",
        "\uD83C\uDDEE\uD83C\uDDF3 IN",
        "\uD83C\uDDF7\uD83C\uDDF8 RS",
        "\uD83C\uDDE7\uD83C\uDDEA BE",
        "\uD83C\uDDED\uD83C\uDDFA HU",
        "\uD83C\uDDE6\uD83C\uDDF9 AT",
        "\uD83C\uDDE7\uD83C\uDDEC BG",
        "\uD83C\uDDFA\uD83C\uDDEC UG",
        "\uD83C\uDDF5\uD83C\uDDEA PE",
        "\uD83C\uDDE8\uD83C\uDDF1 CL",
        "\uD83C\uDDF5\uD83C\uDDF9 PT",
        "\uD83C\uDDE6\uD83C\uDDEA AE",
        "\uD83C\uDDE9\uD83C\uDDF0 DK",
        "\uD83C\uDDEE\uD83C\uDDE9 ID",
        "\uD83C\uDDE8\uD83C\uDDF4 CO",
        "\uD83C\uDDE8\uD83C\uDDFF CZ",
        "\uD83C\uDDED\uD83C\uDDF7 HR",
        "\uD83C\uDDF3\uD83C\uDDFF NZ",
        "\uD83C\uDDFA\uD83C\uDDE6 UA",
        "\uD83C\uDDF8\uD83C\uDDEA SE",
        "\uD83C\uDDE6\uD83C\uDDEB AF",
        "\uD83C\uDDEE\uD83C\uDDEA IE",
        "\uD83C\uDDF0\uD83C\uDDEA KE",
        "\uD83C\uDDF8\uD83C\uDDF0 SK",
        "\uD83C\uDDFF\uD83C\uDDE6 ZA",
        "\uD83C\uDDEB\uD83C\uDDEE FI",
        "\uD83C\uDDEA\uD83C\uDDE8 EC",
        "\uD83C\uDDF3\uD83C\uDDF4 NO",
        "\uD83C\uDDF2\uD83C\uDDE6 MA",
        "\uD83C\uDDEF\uD83C\uDDF5 JP",
        "\uD83C\uDDF9\uD83C\uDDFC TW",
        "\uD83C\uDDF9\uD83C\uDDF3 TN",
        "\uD83C\uDDEE\uD83C\uDDF1 IL",
        "\uD83C\uDDF8\uD83C\uDDEE SI",
        "\uD83C\uDDE9\uD83C\uDDF4 DO",
        "\uD83C\uDDF1\uD83C\uDDFB LV",
        "\uD83C\uDDF1\uD83C\uDDF9 LT",
        "\uD83C\uDDF9\uD83C\uDDED TH",
        "\uD83C\uDDF0\uD83C\uDDF7 KR",// 대한민국
        "\uD83C\uDDEA\uD83C\uDDEA EE",
        "\uD83C\uDDE7\uD83C\uDDE6 BA",
        "\uD83C\uDDF2\uD83C\uDDEA ME",
        "\uD83C\uDDE7\uD83C\uDDF4 BO",
        "\uD83C\uDDF2\uD83C\uDDFE MY",
        "\uD83C\uDDF8\uD83C\uDDE6 SA",
        "\uD83C\uDDF5\uD83C\uDDF0 PK",
        "\uD83C\uDDE7\uD83C\uDDFE BY",
        "\uD83C\uDDFA\uD83C\uDDFE UY",
        "\uD83C\uDDE9\uD83C\uDDFF DZ",
        "\uD83C\uDDEA\uD83C\uDDEC EG",
        "\uD83C\uDDF2\uD83C\uDDE9 MD",
        "\uD83C\uDDF1\uD83C\uDDE7 LB",
        "\uD83C\uDDF8\uD83C\uDDFE SY",
        "\uD83C\uDDED\uD83C\uDDF0 HK",
        "\uD83C\uDDF3\uD83C\uDDEC NG",
        "\uD83C\uDDE8\uD83C\uDDF7 CR",
        "\uD83C\uDDE6\uD83C\uDDF1 AL",
        "\uD83C\uDDF1\uD83C\uDDF0 LK",
        "\uD83C\uDDFB\uD83C\uDDEA VE",
        "\uD83C\uDDEC\uD83C\uDDF9 GT",
        "\uD83C\uDDF8\uD83C\uDDFB SV",
        "\uD83C\uDDEE\uD83C\uDDF4 IO",
        "\uD83C\uDDEE\uD83C\uDDF7 IR",
        "\uD83C\uDDE8\uD83C\uDDFA CU",
        "\uD83C\uDDF8\uD83C\uDDEC SG",
        "\uD83C\uDDF1\uD83C\uDDFA LU",
        "\uD83C\uDDFB\uD83C\uDDF3 VN",
        "\uD83C\uDDE8\uD83C\uDDFE CY",
        "\uD83C\uDDED\uD83C\uDDF3 HN",
        "\uD83C\uDDF5\uD83C\uDDF7 PR",
        "\uD83C\uDDF9\uD83C\uDDFF TZ",
        "\uD83C\uDDF5\uD83C\uDDFE PY",
        "\uD83C\uDDEE\uD83C\uDDF8 IS",
        "\uD83C\uDDF0\uD83C\uDDFF KZ",
        "\uD83C\uDDF3\uD83C\uDDEE NI",
        "\uD83C\uDDE7\uD83C\uDDE9 BD",
        "\uD83C\uDDF3\uD83C\uDDF5 NP",
        "\uD83C\uDDF0\uD83C\uDDFC KW",
        "\uD83C\uDDE8\uD83C\uDDE9 CD",
        "\uD83C\uDDEC\uD83C\uDDED GH",
        "\uD83C\uDDE6\uD83C\uDDFF AZ",
    )

    private lateinit var mediaPlayer: MediaPlayer


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mediaPlayer = MediaPlayer()

        binding = FragmentRadioBinding.inflate(layoutInflater)

        var arrayAdapter = ArrayAdapter(requireContext(), R.layout.spinner_dropdown_item, spList)
        binding.spinner.adapter = arrayAdapter

        // dropDown 위치
        binding.spinner.dropDownHorizontalOffset = 30
        binding.spinner.dropDownVerticalOffset = -200

        binding.spinner.adapter = arrayAdapter
        binding.spinner.onItemSelectedListener = this

        // 저장된 값을 불러옴
        val savedNumber = getNumberFromPreferences(requireContext())
        // 이후 savedNumber 변수에는 30이 저장되어 있음

        binding.spinner.setSelection(savedNumber)

//        view.findViewById<>(R.id.animation_view)

        return binding.root
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        var selectedItem = spList[position]
        var str = selectedItem.substringAfterLast(" ")

        saveNumberToPreferences(requireContext(), position)

        thread {
            var server: String =
                "https://nl1.api.radio-browser.info/json/stations/search?limit=10000&countrycode=${str}&hidebroken=true&order=clickcount&reverse=true"
            var url = URL(server)
            val jsonText = url.readText()

            val gson = Gson()
            val stations: Array<RadioStation> =
                gson.fromJson(jsonText, Array<RadioStation>::class.java)

            items.clear()
            for (position in stations) {
                items.add(
                    RadioStation(
                        position.name,
                        position.favicon,
                        position.url,
                        position.url_resolved,
                    )
                )
            }

            requireActivity().runOnUiThread {
                var adapter = MyAdapter(requireContext(), items, this)
                binding.recyclerView.adapter?.notifyDataSetChanged()
                binding.recyclerView.adapter = adapter
            }
        }


    }

    // 값을 불러올 때 호출하는 함수
    fun getNumberFromPreferences(context: Context): Int {
        val sharedPref = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        return sharedPref.getInt("KEY_NUMBER", 0) // 기본값으로 0을 설정 (만약 저장된 값이 없을 경우)
    }

    // 값을 저장할 때 호출하는 함수 (덮어쓰기 방식)
    fun saveNumberToPreferences(context: Context, number: Int) {
        val sharedPref = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        // 기존의 값을 삭제
        editor.clear()

        // 새로운 값을 저장
        editor.putInt("KEY_NUMBER", number)
        editor.apply()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemClick(url: String, favicon: String, name: String, url_resolved: String) {

        val mainActivity = activity as MainActivity
        mainActivity.getMedia(favicon, name, url, url_resolved)

//        var db = DatabaseHelper(context)
//        var all = db.getAllData()
//        if(all.contains("MBC FM4U")){
//            MainActivity().apply {
//                save()
//            }
//        }
//        MainActivity().getMedia(favicon, name, url, url_resolved)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer.release()
    }

}// RadioFragment...