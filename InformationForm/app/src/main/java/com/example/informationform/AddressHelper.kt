package com.example.informationform
import android.content.res.Resources
import org.json.JSONObject
import java.io.InputStreamReader
class AddressHelper(resources: Resources) {
    private val data: JSONObject
    init {
        val inputStream = resources.openRawResource(R.raw.data) // Ensure data.json is in res/raw
        val reader = InputStreamReader(inputStream)
        val content = reader.readText()
        reader.close()
        data = JSONObject(content)
    }
    fun getProvinces(): List<String> {
        val list = mutableListOf<String>()
        val keys = data.keys()
        for (key in keys)
            list.add(data.getJSONObject(key).getString("name"))
        return list
    }
    fun getDistricts(province: String): List<String> {
        val list = mutableListOf<String>()
        var jProvince: JSONObject? = null
        val keys = data.keys()
        for (key in keys)
            if (data.getJSONObject(key).getString("name").equals(province)) {
                jProvince = data.getJSONObject(key)
                break
            }
        if (jProvince != null) {
            val jDistricts = jProvince.getJSONObject("quan-huyen")
            val keys = jDistricts.keys()
            for (key in keys)
                list.add(jDistricts.getJSONObject(key).getString("name"))
        }
        return list
    }
    fun getWards(province: String, district: String): List<String> {
        val list = mutableListOf<String>()
        var jProvince: JSONObject? = null
        var jDistrict: JSONObject? = null
        val keys = data.keys()
        for (key in keys)
            if (data.getJSONObject(key).getString("name").equals(province)) {
                jProvince = data.getJSONObject(key)
                break
            }
        if (jProvince != null) {
            val jDistricts = jProvince.getJSONObject("quan-huyen")
            val keys = jDistricts.keys()
            for (key in keys)
                if (jDistricts.getJSONObject(key).getString("name").equals(district)) {
                    jDistrict = jDistricts.getJSONObject(key)
                    break
                }
        }
        if (jDistrict != null) {
            val jWards = jDistrict.getJSONObject("xa-phuong")
            val keys = jWards.keys()
            for (key in keys)
                list.add(jWards.getJSONObject(key).getString("name"))
        }
        return list
    }
}