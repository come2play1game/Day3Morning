package com.u2deom.jetpack.setting.model

import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField

class SettingItem {


    var title: ObservableField<String>? = null
    var displayValue: ObservableBoolean? = null
    var displayLoading: ObservableBoolean? = null
    var displaySwitch: ObservableBoolean? = null
    var value: ObservableField<String>? = null


//    @BindingAdapter("")
    fun loadImage() {

    }
}