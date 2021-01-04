package com.example

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(crossinline bindingInflater: (LayoutInflater) -> T) =
        lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            val invoke = bindingInflater.invoke(layoutInflater)
            setContentView(invoke.root)
            invoke
        }