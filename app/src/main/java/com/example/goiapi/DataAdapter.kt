package com.example.goiapi

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.goiapi.data.Result
import com.example.goiapi.data.User
import com.example.goiapi.databinding.DataItemListBinding
import java.text.ParseException
import java.text.SimpleDateFormat


class DataAdapter() : RecyclerView.Adapter<DataAdapter.ViewHolder>() {
    private lateinit var binding : DataItemListBinding
    private lateinit var context: Context
    companion object {
        var DataList : MutableList<Result> = mutableListOf()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataItemListBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun getItemCount(): Int {
        return DataList.size
    }

    override fun onBindViewHolder(holder: DataAdapter.ViewHolder, position: Int) {
        return holder.bind(DataList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : Result) {
            binding.apply {
                ImgUser.load(item.picture.large){
                    crossfade(true)
                    placeholder(R.color.gray)
                    scale(Scale.FILL)
                }
                userGender.apply {
                    if (item.gender.toString() == "male") {
                        text = "Nam"
                        setTextColor(getResources().getColor(R.color.blue))
                    } else {
                        text = "Nữ"
                        setTextColor(getResources().getColor(R.color.pink))
                    }
                }
                userDob.text = FormatTime(item.dob.date) + " (${item.dob.age} tuổi)"
                userName.text = item.name.title + ". " + item.name.first + " " + item.name.last
                userAddress.text = item.location.street.number + " " + item.location.street.name + ", " + item.location.city + ", " + item.location.state + ", " + item.location.country
                userTimezone.text = item.location.timezone.offset + " (${item.location.timezone.description})"
                userPhone.text = item.phone + " (máy bàn), " + item.cell + " (cá nhân)"
                userEmail.text = item.email
            }
        }
    }
    private fun FormatTime(time : String) : String {
        var inputTime : SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        var outputTime : SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        try {
            var date = inputTime.parse(time)
            var timeFormat = outputTime.format(date)
            return timeFormat
        } catch (e : ParseException) {
            return time
        }
    }
}
