package com.sapotos.ayarental.presentation.tabs

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil   // ← import this
import androidx.lifecycle.ViewModelProvider
import com.sapotos.ayarental.R
import com.sapotos.ayarental.base.BaseFragment
import com.sapotos.ayarental.databinding.HomeScreenBinding
import com.sapotos.ayarental.interfaces.NavigationInterface
import com.sapotos.ayarental.presentation.fragments.homeScreen.HomeFragment_View_Model
import java.util.Calendar

class HomeFragment :
    BaseFragment<HomeScreenBinding>(),
    NavigationInterface {

    private lateinit var vm: HomeFragment_View_Model

    override fun inflateAndBind(inflater: LayoutInflater, container: ViewGroup?): HomeScreenBinding {
        val b: HomeScreenBinding =
            DataBindingUtil.inflate(inflater, R.layout.home_screen, container, false)
        b.lifecycleOwner = viewLifecycleOwner
        return b
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm = ViewModelProvider(this)[HomeFragment_View_Model::class.java]
        binding?.viewModel = vm
        vm.routingListener = this

        vm.openResults.observe(viewLifecycleOwner) { e ->
            e.getIfNotHandled()?.let { openResultsScreen() }
        }
    }

    private fun openResultsScreen() {
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right, R.anim.fade_out,
                R.anim.fade_in, R.anim.slide_out_right
            )
            .replace(R.id.fragmentContainer, SearchVehicleFragment())
            .addToBackStack("results")
            .commit()
    }

    // ---- NavigationInterface implementation
    override fun requestDateTimePickUp(isPickup: Boolean) {
        val baseMillis = if (isPickup) vm.pickDateTimeMillis else vm.dropDateTimeMillis
        val base = Calendar.getInstance().apply { timeInMillis = baseMillis }

        DatePickerDialog(
            requireContext(),
            { _, y, m, d -> showTimePicker(isPickup, y, m, d) },
            base.get(Calendar.YEAR),
            base.get(Calendar.MONTH),
            base.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun showTimePicker(isPickup: Boolean, year: Int, month: Int, day: Int) {
        val now = Calendar.getInstance()
        TimePickerDialog(
            requireContext(),
            { _, hour, minute ->
                val cal = Calendar.getInstance().apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, month)
                    set(Calendar.DAY_OF_MONTH, day)
                    set(Calendar.HOUR_OF_DAY, hour)
                    set(Calendar.MINUTE, minute)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                vm.setDateTime(isPickup, cal.timeInMillis)
            },
            now.get(Calendar.HOUR_OF_DAY),
            now.get(Calendar.MINUTE),
            false
        ).show()
    }
}
