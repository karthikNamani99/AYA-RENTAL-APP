package com.sapotos.ayarental.presentation.comingSoon


import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sapotos.ayarental.R
import com.sapotos.ayarental.base.BaseActivity
import com.sapotos.ayarental.databinding.ComingSoonBinding

class ComingSoon : BaseActivity() {

    companion object {

        private var viewModel: ComingSoonScreen_View_Model? = null
    }

    override fun getLayoutResourceId(): View {
        val binding: ComingSoonBinding = DataBindingUtil.setContentView(this, R.layout.coming_soon)
        viewModel = ViewModelProvider(this)[ComingSoonScreen_View_Model::class.java]
        val prefs = PreferenceHelper.defaultPrefs(this)

        binding.lifecycleOwner = this

//        viewModel!!.routingListener = this
        return binding.root

    }
}