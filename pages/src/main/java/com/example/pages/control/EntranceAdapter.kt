package com.example.pages.control

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.example.commonlib.utils.ToastUtil
import com.example.pages.R
import com.example.pages.model.EntranceInfo
import kotlinx.android.synthetic.main.item_entrance_display.view.*

class EntranceAdapter(var context: Context) : RecyclerView.Adapter<EntranceAdapter.EntranceHolder>() {

    var list: List<EntranceInfo>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntranceHolder {
        val itemView = LayoutInflater
                .from(context)
                .inflate(R.layout.item_entrance_display, parent, false)

        return EntranceHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: EntranceHolder, position: Int) {
        val entranceInfo = list?.get(position)
        holder.itemView.tv_entrance_name.text = entranceInfo?.title ?: ""
        holder.itemView.tv_entrance_name.setOnClickListener {
            when {
                entranceInfo?.onClick != null -> {
                    entranceInfo.onClick?.onClick(it)
                }
                entranceInfo?.path != null -> {
                    ARouter.getInstance().build(entranceInfo.path).navigation()
                }
                else -> {
                    ToastUtil.showShort("please config ")
                }
            }
        }
    }


    class EntranceHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

}

fun main() {
    when {
        true -> {
            println("first")
        }

        true -> {
            println("second")
        }

        false -> {
            println("third")
        }
    }
}