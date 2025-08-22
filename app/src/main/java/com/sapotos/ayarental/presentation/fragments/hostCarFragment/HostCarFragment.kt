package com.sapotos.ayarental.presentation.fragments.hostCarFragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sapotos.ayarental.R
import com.sapotos.ayarental.base.BaseFragment
import com.sapotos.ayarental.databinding.HostcarFragmentScreenBinding
import com.sapotos.ayarental.interfaces.NavigationInterface

class HostCarFragment :
    BaseFragment<HostcarFragmentScreenBinding>(),
    NavigationInterface {

    private lateinit var vm: HostCarFragment_View_Model

    override fun inflateAndBind(inflater: LayoutInflater, container: ViewGroup?): HostcarFragmentScreenBinding {
        val b: HostcarFragmentScreenBinding =
            DataBindingUtil.inflate(inflater, R.layout.hostcar_fragment_screen, container, false)
        b.lifecycleOwner = viewLifecycleOwner
        return b
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm = ViewModelProvider(this)[HostCarFragment_View_Model::class.java]
        binding?.viewModel = vm
//        vm.routingListener = this


    }

    override fun requestDateTimePickUp(isPickup: Boolean) {
        TODO("Not yet implemented")
    }


}