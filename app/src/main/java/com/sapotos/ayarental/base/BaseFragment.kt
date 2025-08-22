// package com.sapotos.ayarental.base
package com.sapotos.ayarental.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * BaseFragment similar to your BaseActivity.
 * Child fragments must return the root view via inflateAndBind().
 */
abstract class BaseFragment<VB : ViewDataBinding> : Fragment() {

    protected var parentContainer: View? = null
    protected var binding: VB? = null

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    /** Child must inflate the binding and return it. Also set lifecycleOwner if needed. */
    abstract fun inflateAndBind(inflater: LayoutInflater, container: ViewGroup?): VB

    /** Override if you need the result. */
    open fun onActivityResult(result: ActivityResult) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            onActivityResult(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val b = inflateAndBind(inflater, container)
        binding = b
        parentContainer = b.root
        return b.root
    }

    override fun onDestroyView() {
        binding = null
        parentContainer = null
        super.onDestroyView()
    }

    /** Convenience launcher from fragments */
    protected fun launch(intent: Intent) = resultLauncher.launch(intent)
}
