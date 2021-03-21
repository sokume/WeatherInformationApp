package com.example.androiddevchallenge

import com.chibatching.kotpref.KotprefModel
import com.google.gson.annotations.SerializedName

object LocationSave : KotprefModel() {
    var saveInfo by stringPref()
}


