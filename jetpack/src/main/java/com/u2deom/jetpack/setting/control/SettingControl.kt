package com.u2deom.jetpack.setting.control

import android.view.ViewStub
import com.u2deom.jetpack.setting.model.SettingControlOption
import com.u2deom.jetpack.setting.model.SettingItem

open class SettingControl {



    private lateinit var settingItemControlOption: SettingControlOption
    private lateinit var settingItem: SettingItem

    open fun inflateItem(viewStub: ViewStub) {


    }





}

fun main(){

    println(getInt(null))

}

fun getInt(int:Int?):Int{

    int?: return 0

    return 1


}
