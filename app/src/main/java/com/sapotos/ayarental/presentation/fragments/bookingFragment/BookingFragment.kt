package com.sapotos.ayarental.presentation.tabs

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sapotos.ayarental.R
import com.sapotos.ayarental.base.BaseFragment
import com.sapotos.ayarental.databinding.BookingHistoryScreenBinding
import com.sapotos.ayarental.presentation.fragments.bookingFragment.BookingFragment_View_Model
import com.sapotos.ayarental.presentation.profile.BookingHistoryAdapter

class BookingFragment : BaseFragment<BookingHistoryScreenBinding>() {

    private lateinit var vm: BookingFragment_View_Model
    private lateinit var adapter: BookingHistoryAdapter

    override fun inflateAndBind(inflater: LayoutInflater, container: ViewGroup?): BookingHistoryScreenBinding {
        val b: BookingHistoryScreenBinding =
            DataBindingUtil.inflate(inflater, R.layout.booking_history_screen, container, false)
        b.lifecycleOwner = viewLifecycleOwner
        return b
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm = ViewModelProvider(this)[BookingFragment_View_Model::class.java]
        binding?.viewModel = vm

        adapter = BookingHistoryAdapter(
            onDelete = { item -> vm.delete(item) },
            onItemClick = { item ->
                // optional: open details
                // findNavController().navigate(...)
            }
        )

        binding?.recycler?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@BookingFragment.adapter
        }

        vm.bookings.observe(viewLifecycleOwner) { list -> adapter.submitList(list) }
        vm.loadSample()

        binding?.btnBack?.setOnClickListener { parentFragmentManager.popBackStack() }
    }
}
