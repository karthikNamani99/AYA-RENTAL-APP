package com.sapotos.ayarental.presentation.tabs

import PreferenceHelper.edit
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sapotos.ayarental.R
import com.sapotos.ayarental.base.BaseFragment
import com.sapotos.ayarental.constants.Constants
import com.sapotos.ayarental.databinding.FragmentProfileBinding
import com.sapotos.ayarental.presentation.comingSoon.ComingSoon
import com.sapotos.ayarental.presentation.appSettingsScreen.AppSettingsScreen
import com.sapotos.ayarental.presentation.customerSupport.CustomerSupportScreen
import com.sapotos.ayarental.presentation.editProfileScreen.EditProfileScreen
import com.sapotos.ayarental.presentation.faqs.FaqScreen
import com.sapotos.ayarental.presentation.fragments.profile.ProfileFragment_View_Model
import com.sapotos.ayarental.presentation.legal.TermsAndConditionsScreen
import com.sapotos.ayarental.presentation.login_page.LoginScreen

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private lateinit var vm: ProfileFragment_View_Model

    override fun inflateAndBind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding {
        val b: FragmentProfileBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        b.lifecycleOwner = viewLifecycleOwner
        return b
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm = ViewModelProvider(this)[ProfileFragment_View_Model::class.java]
        binding?.viewModel = vm

        // Row clicks (wire these to your real destinations)
        binding?.rowEditProfile?.root?.setOnClickListener {
            val intent = Intent(requireContext(), EditProfileScreen::class.java).apply {
            }
            startActivity(intent)
        }
        binding?.rowMyBooking?.root?.setOnClickListener {
            val intent = Intent(
                requireContext(),
                ComingSoon::class.java
            ).apply {
            }
            startActivity(intent)
        }
        binding?.rowNotifications?.root?.setOnClickListener {
            val intent = Intent(
                requireContext(),
                ComingSoon::class.java
            ).apply {
            }
            startActivity(intent)
        }
        binding?.rowSettings?.root?.setOnClickListener {
            val intent = Intent(requireContext(), AppSettingsScreen::class.java).apply {
            }
            startActivity(intent)
        }
        binding?.rowTerms?.root?.setOnClickListener {
            val intent = Intent(requireContext(), TermsAndConditionsScreen::class.java).apply {
            }
            startActivity(intent)
        }
        binding?.rowFaq?.root?.setOnClickListener {
            val intent = Intent(requireContext(), FaqScreen::class.java).apply {
            }
            startActivity(intent)
        }
        binding?.rowSupport?.root?.setOnClickListener {
            val intent = Intent(requireContext(), CustomerSupportScreen::class.java).apply {
            }
            startActivity(intent)
        }
        binding?.rowRate?.root?.setOnClickListener {
            showRatingDialog()
        }
        binding?.rowLogout?.root?.setOnClickListener {

            showLogoutDialog()
        }
    }

    private fun showRatingDialog() {
        val ctx = requireContext()
        val dialogView = LayoutInflater.from(ctx)
            .inflate(R.layout.dialog_rate_experience, null, false)

        val ratingBar = dialogView.findViewById<RatingBar>(R.id.ratingBar)
        val btnSubmit = dialogView.findViewById<MaterialButton>(R.id.btnSubmit)
        val btnSkip = dialogView.findViewById<TextView>(R.id.btnSkip)

        // Ensure star tint matches in all API levels (some devices ignore XML tints)
        val starColor = ContextCompat.getColor(ctx, R.color.rating_star)
        ratingBar.progressTintList = ColorStateList.valueOf(starColor)
        ratingBar.secondaryProgressTintList = ColorStateList.valueOf(starColor)

        val dialog = MaterialAlertDialogBuilder(ctx)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        btnSubmit.setOnClickListener {
            val rating = ratingBar.rating
            if (rating < 1f) {
                // at least 1 star
                toast("Please select a rating")
                return@setOnClickListener
            }
            // Persist flag so we do not ask again
            PreferenceHelper.defaultPrefs(ctx).edit {
                it.putBoolean(Constants.PREF_RATING_GIVEN, true)
                it.putFloat(Constants.PREF_RATING_VALUE, rating)
            }

            // TODO: send to your ViewModel / API
            // viewModel.submitRating(rating)

            toast("Thanks for rating $rating ★")
            dialog.dismiss()
        }

        btnSkip.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun toast(msg: String) =
        android.widget.Toast.makeText(requireContext(), msg, android.widget.Toast.LENGTH_SHORT)
            .show()

    @SuppressLint("UseCompatLoadingForDrawables", "UseKtx")
    private fun showLogoutDialog() {
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setMessage("Are you sure you want to logout?")
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Logout") { d, _ ->
                // do the logout
                val prefs = PreferenceHelper.defaultPrefs(requireContext())
                prefs.edit()
                    .putString(Constants.IS_LOGIN_FIRST_TIME, "N")
                    .apply()

                val intent = Intent(requireContext(), LoginScreen::class.java).apply {
                    addFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                Intent.FLAG_ACTIVITY_NEW_TASK or
                                Intent.FLAG_ACTIVITY_CLEAR_TASK
                    )
                }
                startActivity(intent)
                requireActivity().finish()
            }
            .create()

        // Style the positive button to look like the screenshot (green pill)
        dialog.setOnShowListener {
            val positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positive.setTextColor(Color.BLACK)
            positive.background =
                requireContext().getDrawable(R.drawable.bg_dialog_positive) // see below
            positive.isAllCaps = false
            positive.setPadding(48, 16, 48, 16) // left, top, right, bottom (px)
            // Optional: make Cancel look like text button
            val negative = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            negative.isAllCaps = false
        }

        dialog.show()
    }
}

