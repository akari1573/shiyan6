package com.example.shiyan6.data

import java.io.Serializable

class PersonalInfo : Serializable {
    var name: String = ""
    var studentId: String = ""
    var gender: String = "男"
    var phone: String = ""
    var remark: String = ""

    companion object {
        private const val serialVersionUID = 1L
    }
}
