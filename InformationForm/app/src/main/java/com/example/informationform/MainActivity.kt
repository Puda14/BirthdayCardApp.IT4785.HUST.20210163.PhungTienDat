package com.example.informationform
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
class MainActivity : AppCompatActivity() {
    private lateinit var maSinhVienEditText: EditText
    private lateinit var hoTenEditText: EditText
    private lateinit var gioiTinhRadioGroup: RadioGroup
    private lateinit var namRadioButton: RadioButton
    private lateinit var nuRadioButton: RadioButton
    private lateinit var emailEditText: EditText
    private lateinit var soDienThoaiEditText: EditText
    private lateinit var ngaySinhCalendarView: CalendarView
    private lateinit var noiOHienTaiEditText: EditText
    private lateinit var tinhThanhSpinner: Spinner
    private lateinit var quanHuyenSpinner: Spinner
    private lateinit var phuongXaSpinner: Spinner
    private lateinit var theThaoCheckBox: CheckBox
    private lateinit var dienAnhCheckBox: CheckBox
    private lateinit var amNhacCheckBox: CheckBox
    private lateinit var dongYDieuKhoanCheckBox: CheckBox
    private lateinit var submitButton: Button
    private lateinit var ngaySinhTextView: TextView
    private lateinit var showCalendarToggleButton: ToggleButton
    private lateinit var addressHelper: AddressHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addressHelper = AddressHelper(resources)
        maSinhVienEditText = findViewById(R.id.maSinhVienEditText)
        hoTenEditText = findViewById(R.id.hoTenEditText)
        gioiTinhRadioGroup = findViewById(R.id.gioiTinhRadioGroup)
        namRadioButton = findViewById(R.id.namRadioButton)
        nuRadioButton = findViewById(R.id.nuRadioButton)
        emailEditText = findViewById(R.id.emailEditText)
        soDienThoaiEditText = findViewById(R.id.soDienThoaiEditText)
        ngaySinhCalendarView = findViewById(R.id.ngaySinhCalendarView)
        noiOHienTaiEditText = findViewById(R.id.noiOHienTaiEditText)
        tinhThanhSpinner = findViewById(R.id.tinhThanhSpinner)
        quanHuyenSpinner = findViewById(R.id.quanHuyenSpinner)
        phuongXaSpinner = findViewById(R.id.phuongXaSpinner)
        theThaoCheckBox = findViewById(R.id.theThaoCheckBox)
        dienAnhCheckBox = findViewById(R.id.dienAnhCheckBox)
        amNhacCheckBox = findViewById(R.id.amNhacCheckBox)
        dongYDieuKhoanCheckBox = findViewById(R.id.dongYDieuKhoanCheckBox)
        submitButton = findViewById(R.id.submitButton)
        val tinhThanhAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, addressHelper.getProvinces())
        tinhThanhAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tinhThanhSpinner.adapter = tinhThanhAdapter
        tinhThanhAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tinhThanhSpinner.adapter = tinhThanhAdapter
        tinhThanhSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedProvince = tinhThanhSpinner.selectedItem.toString()
                val quanHuyenAdapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, addressHelper.getDistricts(selectedProvince))
                quanHuyenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                quanHuyenSpinner.adapter = quanHuyenAdapter
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Không làm gì
            }
        }
        quanHuyenSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedProvince = tinhThanhSpinner.selectedItem.toString()
                val selectedDistrict = quanHuyenSpinner.selectedItem.toString()
                val phuongXaAdapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, addressHelper.getWards(selectedProvince, selectedDistrict))
                phuongXaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                phuongXaSpinner.adapter = phuongXaAdapter
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Không làm gì
            }
        }
        submitButton.setOnClickListener { submitForm() }
        ngaySinhTextView = findViewById(R.id.ngaySinhTextView)
        showCalendarToggleButton = findViewById(R.id.showCalendarToggleButton)
        ngaySinhCalendarView = findViewById(R.id.ngaySinhCalendarView)
        showCalendarToggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                ngaySinhCalendarView.visibility = View.VISIBLE
                ngaySinhCalendarView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            } else {
                ngaySinhCalendarView.visibility = View.GONE
                ngaySinhCalendarView.layoutParams.height = 0
            }
        }
    }
    private fun submitForm() {
        val maSinhVien = maSinhVienEditText.text.toString()
        val hoTen = hoTenEditText.text.toString()
        val gioiTinh = if (namRadioButton.isChecked) "Nam" else "Nữ"
        val email = emailEditText.text.toString()
        val soDienThoai = soDienThoaiEditText.text.toString()
        val ngaySinh = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(
            Calendar.getInstance().apply {
                timeInMillis = ngaySinhCalendarView.date
            }.time
        )
        val noiOHienTai = noiOHienTaiEditText.text.toString()
        val tinhThanh = tinhThanhSpinner.selectedItem.toString()
        val quanHuyen = quanHuyenSpinner.selectedItem.toString()
        val phuongXa = phuongXaSpinner.selectedItem.toString()
        val soThich = mutableListOf<String>()
        if (theThaoCheckBox.isChecked) soThich.add("Thể thao")
        if (dienAnhCheckBox.isChecked) soThich.add("Điện ảnh")
        if (amNhacCheckBox.isChecked) soThich.add("Âm nhạc")
        if (dongYDieuKhoanCheckBox.isChecked) {
            Toast.makeText(this, "Dữ liệu đã được gửi", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Vui lòng đồng ý với điều khoản", Toast.LENGTH_SHORT).show()
        }
    }
}