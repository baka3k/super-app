package com.baka3k.architecture.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
abstract class BaseFragment<Binding : ViewBinding> : Fragment() {
    private var _binding: ViewBinding? = null
    protected val binding:Binding get() = (_binding!! as Binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = initViewBinding(inflater, container)
        return binding.root
    }
    abstract fun initViewBinding(inflater: LayoutInflater, container: ViewGroup?): ViewBinding?

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}