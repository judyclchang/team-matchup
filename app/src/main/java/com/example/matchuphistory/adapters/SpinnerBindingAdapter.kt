package com.example.matchuphistory.adapters

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData

object SpinnerBindingAdapter {

    @JvmStatic
    @BindingAdapter("entries")
    fun Spinner.setEntries(entries: LiveData<List<String>>) {
        if (entries.value != null) {
            val arrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, entries.value!!)
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            adapter = arrayAdapter
        }
    }

    interface onItemSelected {
        fun onItemSelected(value: String)
    }

    @JvmStatic
    @BindingAdapter("onItemSelected")
    fun Spinner.setOnItemSelected(value: onItemSelected) {
        onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (parent != null) {
                    value.onItemSelected(parent.getItemAtPosition(position) as String)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }
}
