package com.example.shiyan6.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.shiyan6.R
import com.example.shiyan6.data.PersonalInfo
import com.example.shiyan6.util.FileStore

class InfoFragment : Fragment() {

    private lateinit var tvStatus: TextView
    private lateinit var etName: EditText
    private lateinit var etStudentId: EditText
    private lateinit var rbMale: RadioButton
    private lateinit var rbFemale: RadioButton
    private lateinit var etPhone: EditText
    private lateinit var etRemark: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_info, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvStatus = view.findViewById(R.id.tv_status)
        etName = view.findViewById(R.id.et_name)
        etStudentId = view.findViewById(R.id.et_student_id)
        rbMale = view.findViewById(R.id.rb_male)
        rbFemale = view.findViewById(R.id.rb_female)
        etPhone = view.findViewById(R.id.et_phone)
        etRemark = view.findViewById(R.id.et_remark)

        val info = FileStore.load<PersonalInfo>(requireContext(), FILE_NAME)
        if (info != null) {
            etName.setText(info.name)
            etStudentId.setText(info.studentId)
            if (info.gender == "女") rbFemale.isChecked = true else rbMale.isChecked = true
            etPhone.setText(info.phone)
            etRemark.setText(info.remark)
            tvStatus.text = "已恢复上次保存的历史信息"
        } else {
            tvStatus.text = "暂无历史记录"
        }

        view.findViewById<Button>(R.id.btn_save).setOnClickListener { saveInfo() }
    }

    private fun saveInfo() {
        val info = PersonalInfo()
        info.name = etName.text.toString().trim()
        info.studentId = etStudentId.text.toString().trim()
        info.gender = if (rbFemale.isChecked) "女" else "男"
        info.phone = etPhone.text.toString().trim()
        info.remark = etRemark.text.toString().trim()

        if (info.name.isEmpty() || info.studentId.isEmpty()) {
            Toast.makeText(requireContext(), "姓名和学号不能为空", Toast.LENGTH_SHORT).show()
            return
        }

        FileStore.save(requireContext(), FILE_NAME, info)
        tvStatus.text = "已保存：${info.name} ${info.studentId}"
        Toast.makeText(requireContext(), "信息保存成功", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val FILE_NAME = "info.dat"
    }
}
