package com.sapotos.ayarental.presentation.selectCategoryScreeen  // ← keep exactly the same as your other files

import android.content.Intent
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sapotos.ayarental.R
import com.sapotos.ayarental.base.BaseActivity
import com.sapotos.ayarental.databinding.SelectCategoryScreenBinding

class SelectCategoryScreen : BaseActivity() {   // ← renamed

    private lateinit var binding: SelectCategoryScreenBinding
    private lateinit var vm: SelectCategoryScreen_View_Model   // ← init properly

    override fun getLayoutResourceId(): View {
        binding = DataBindingUtil.setContentView(this, R.layout.select_category_screen)
        vm = ViewModelProvider(this)[SelectCategoryScreen_View_Model::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = vm

        val adapter = CategoryAdapter { cat ->
            val i = Intent(this, SelectSubCategoryScreen::class.java).apply {
                putExtra(SelectSubCategoryScreen.EXTRA_NAME, cat.name)
                putExtra(SelectSubCategoryScreen.EXTRA_IMG, cat.imageRes)
            }
            startActivity(i)

        }
        binding.rvCategories.layoutManager = LinearLayoutManager(this)
        binding.rvCategories.adapter = adapter

        vm.categories.observe(this) { adapter.submitList(it) }

        binding.btnBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        return binding.root
    }
}
