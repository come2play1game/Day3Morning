package com.example.pages.control

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.pages.R
import com.example.pages.model.PersonInfo

class PersonAdapter : BaseAdapter() {


    lateinit var personList:List<PersonInfo>


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var testV:View? =convertView
        var holder:PersonHolder
        if (testV == null){
            testV=LayoutInflater.from(parent?.context).inflate(R.layout.item_test_list_view2,parent,false)
            holder= PersonHolder()
//            holder.ageView = rView?.findView as TextView

        }else{
        }

        return testV
    }

    override fun getItem(position: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getItemId(position: Int): Long {
        TODO("Not yet implemented")
    }

    override fun getCount(): Int {
        TODO("Not yet implemented")
    }

    class PersonHolder {

        var nameView:TextView? =null
        var ageView:TextView? =null



    }

    companion object{
        fun doCom(){
        }
    }

    object  MyObj{

        fun doMyObj(){



        }
    }


}
