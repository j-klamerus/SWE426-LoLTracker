package com.example.loltracker.misc

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

// Allows two-way databinding for spinners (see fragment_favorites.xml)
@BindingAdapter("selectedItem")
fun Spinner.setSelectedItem(value: String?) {
    if (value == null) return
    val pos = (0 until count).firstOrNull { getItemAtPosition(it)?.toString() == value } ?: 0
    if (selectedItemPosition != pos) setSelection(pos)
}

@InverseBindingAdapter(attribute = "selectedItem", event = "selectedItemAttrChanged")
fun Spinner.getSelectedItemString(): String {
    return selectedItem?.toString().orEmpty()
}

@BindingAdapter("selectedItemAttrChanged")
fun Spinner.setSelectedItemListener(attrChanged: InverseBindingListener?) {
    if (attrChanged == null) return
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
            attrChanged.onChange()
        }
        override fun onNothingSelected(parent: AdapterView<*>) {
            attrChanged.onChange()
        }
    }
}