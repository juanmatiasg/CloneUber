package com.example.cloneuber.ui.util

import android.content.Context
import android.widget.Toast

fun Context.showToast(msg:CharSequence){
    Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
}