package com.baka3k.architecture.core.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<Binding : ViewBinding> : AppCompatActivity() {
    private var _binding: ViewBinding? = null
    private val binding: Binding get() = (_binding!! as Binding)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = initViewBinding()
        setContentView(binding.root)
    }

    abstract fun initViewBinding(): ViewBinding?
    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}