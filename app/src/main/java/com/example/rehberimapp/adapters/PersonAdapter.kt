package com.example.rehberimapp.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.rehberimapp.R
import com.example.rehberimapp.models.Person

class PersonAdapter(context: Context, val people: List<Person>) :
    ArrayAdapter<Person>(context, R.layout.lw_custom_line, people) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater = LayoutInflater.from(context)
        val customView: View?
        val viewHolder: ViewHolder

        if (convertView == null) {
            customView = layoutInflater.inflate(R.layout.lw_custom_line, parent, false)
            viewHolder = ViewHolder()
            viewHolder.fullNameTextView = customView.findViewById(R.id.fullNameTextView)
            viewHolder.phoneNumberTextView = customView.findViewById(R.id.phoneNumberTextView)
            viewHolder.addressTextView = customView.findViewById(R.id.addressTextView)
            viewHolder.groupTextView = customView.findViewById(R.id.groupTextView)
            customView.tag = viewHolder
        } else {
            customView = convertView
            viewHolder = convertView.tag as ViewHolder
        }

        viewHolder.fullNameTextView?.text = "${people[position].name} ${people[position].surname}"
        viewHolder.phoneNumberTextView?.text = "${people[position].phone}"
        viewHolder.addressTextView?.text = "${people[position].address}"
        when (people[position].group) {
            "family" -> viewHolder.groupTextView?.text = "Aile"
            "course" -> viewHolder.groupTextView?.text = "Ingilizce Kursu"
            "friends" -> viewHolder.groupTextView?.text = "Arkadaşlar"
            "school" -> viewHolder.groupTextView?.text = "Okul"
            "work" -> viewHolder.groupTextView?.text = "İş"
        }

        Log.d("mKm", position.toString())

        when (position % 4) {
            0 -> customView?.setBackgroundColor(context.resources.getColor(R.color.colorOne))
            1 -> customView?.setBackgroundColor(context.resources.getColor(R.color.colorTwo))
            2 -> customView?.setBackgroundColor(context.resources.getColor(R.color.colorThree))
            3 -> customView?.setBackgroundColor(context.resources.getColor(R.color.colorFour))
        }

        return customView as View
    }

    class ViewHolder {
        var fullNameTextView: TextView? = null
        var phoneNumberTextView: TextView? = null
        var addressTextView: TextView? = null
        var groupTextView: TextView? = null
    }
}