package com.example.piano

import android.graphics.Color
import android.view.View
import android.widget.AdapterView

import android.widget.AdapterView.OnItemSelectedListener
import android.widget.TextView


internal class MyOnItemSelectedListener : OnItemSelectedListener {
    var chunkSize : Int = 0
    override fun onItemSelected(
        parent: AdapterView<*>,
        view: View?, pos: Int, id: Long
    ) {
        chunkSize = parent.getItemAtPosition(pos).toString().toInt()
        (view as TextView).setTextColor(Color.YELLOW) //Change selected text color
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Dummy
    }
}