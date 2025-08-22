package com.sapotos.ayarental.presentation.faqs

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.sapotos.ayarental.R
import com.sapotos.ayarental.base.BaseActivity
import com.sapotos.ayarental.databinding.ActivityFaqsBinding
import com.sapotos.ayarental.viewholder.FaqAdapter
import com.sapotos.ayarental.viewholder.FaqItem

class FaqScreen : BaseActivity() {

    private lateinit var binding: ActivityFaqsBinding
    private lateinit var adapter: FaqAdapter

    override fun getLayoutResourceId(): View {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_faqs)

        binding.btnBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        adapter = FaqAdapter(sampleFaqs().toMutableList())
        binding.rvFaqs.layoutManager = LinearLayoutManager(this)
        binding.rvFaqs.adapter = adapter

        return binding.root
    }

    private fun sampleFaqs(): List<FaqItem> = listOf(
        FaqItem("What is AYA Rental?",
            "AYA Rental is a car rental platform where you can browse, book, and manage your car rentals securely."),
        FaqItem("How does AYA Rental work?",
            "Create an account, choose your car and dates, verify your ID if required, then pay to confirm your booking."),
        FaqItem("How do I book a car?",
            "Search by location and dates, pick a vehicle, review the price and policy, then proceed to checkout."),
        FaqItem("What payment methods can I use?",
            "We accept major credit/debit cards and popular UPI/wallets depending on your region."),
        FaqItem("Can I book a car for someone else?",
            "Yes, but the driver’s license and ID must match the person picking up the car."),
        FaqItem("Who can rent a car on AYA Rental?",
            "You must meet the minimum age requirement and hold a valid driver’s license for the vehicle class."),
        FaqItem("Can tourists use AYA Rental?",
            "Yes, tourists can rent with a valid international or country-accepted driver’s license."),
        FaqItem("Where will I pick up the car?",
            "Pickup location is shown on the car’s detail page and in your booking confirmation."),
        FaqItem("What happens if I return the car late?",
            "Late returns may incur additional charges as per the rental policy."),
        FaqItem("Do I need to refuel the car before returning it?",
            "Return fuel policy is shown in your booking; many vehicles require the same level as pickup.")
    )
}
