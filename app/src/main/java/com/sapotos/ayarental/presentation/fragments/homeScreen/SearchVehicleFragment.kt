package com.sapotos.ayarental.presentation.tabs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sapotos.ayarental.R
import com.sapotos.ayarental.base.BaseFragment
import com.sapotos.ayarental.databinding.FragmentSearchResultsBinding
import com.sapotos.ayarental.interfaces.NavigationInterface
import com.sapotos.ayarental.presentation.fragments.homeScreen.SearchVehicleFragment_View_Model
import com.sapotos.ayarental.presentation.selectCategoryScreeen.SelectCategoryScreen

class SearchVehicleFragment :
    BaseFragment<FragmentSearchResultsBinding>(),
    NavigationInterface {

    private lateinit var vm: SearchVehicleFragment_View_Model

    override fun inflateAndBind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchResultsBinding {
        val b: FragmentSearchResultsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_search_results, container, false)
        b.lifecycleOwner = viewLifecycleOwner
        return b
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm = ViewModelProvider(this)[SearchVehicleFragment_View_Model::class.java]

        // Open SelectCategory screen when "Low Cost" is tapped
        binding?.lowCost?.setOnClickListener {
            val intent = Intent(requireContext(),
                com.sapotos.ayarental.presentation.selectCategoryScreeen.SelectCategoryScreen::class.java
            )
            startActivity(intent)            // or: launch(intent) if you want to use BaseFragment launcher
        }
        binding?.normalCost?.setOnClickListener {
            val intent = Intent(requireContext(),
                com.sapotos.ayarental.presentation.selectCategoryScreeen.SelectCategoryScreen::class.java
            )
            startActivity(intent)

        // or: launch(intent) if you want to use BaseFragment launcher
        }
    }

    override fun requestDateTimePickUp(isPickup: Boolean) { /* not used here */ }
}
