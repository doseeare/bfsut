package pro.breez.bfsut.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.viewbinding.ViewBinding
import pro.breez.bfsut.R
import pro.breez.bfsut.model.navigation.ActivityTransaction
import pro.breez.bfsut.model.navigation.FragmentTransaction
import pro.breez.bfsut.ui.main.credit.CreditsFragment
import pro.breez.bfsut.ui.main.home.HomeFragment
import pro.breez.bfsut.ui.main.log.LogFragment
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> :
    Fragment() {

    private var _binding: VB? = null
    protected val binding: VB get() = _binding!!
    protected lateinit var viewModel: VM private set

    private val type = (javaClass.genericSuperclass as ParameterizedType)
    private val classVB = type.actualTypeArguments[0] as Class<VB>
    private val classVM = type.actualTypeArguments[1] as Class<VM>

    private var loadingView: ConstraintLayout? = null
    private var textViewLoadingMessage: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateMethod.invoke(null, layoutInflater, container, false) as VB
        viewModel = createViewModelLazy(classVM.kotlin, { viewModelStore }).value
        lifecycle.addObserver(viewModel)
        setupBaseViewModel()
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLoadingView()
    }

    override fun onResume() {
        super.onResume()
        when (this) {
            is HomeFragment,
            is LogFragment,
            is CreditsFragment -> hideOrShowBottomNavigation(false)
            else -> hideOrShowBottomNavigation(true)
        }
    }

    private fun setupLoadingView() {
        val view = LayoutInflater.from(context).inflate(R.layout.view_progress, (view as ViewGroup), false)

        loadingView = view.findViewById(R.id.progress_overlay)
        textViewLoadingMessage = view.findViewById(R.id.textView_progressMessage)
    }

    private fun setupBaseViewModel() {
        viewModel.let { vm ->
            vm.navigateToFragment.observe(viewLifecycleOwner) {
                navigateToFragment(it)
            }

            vm.navigateToActivity.observe(viewLifecycleOwner) {
                navigateToActivity(it)
            }

            vm.popBackStack.observe(viewLifecycleOwner) {
                popBackStack(it)
            }
            vm.showBottomSheetFragment.observe(viewLifecycleOwner) { fragment ->
                fragment.show(childFragmentManager, fragment.tag)
            }

            vm.showSnackBar.observe(viewLifecycleOwner) { builder ->
                builder.create(requireView()).show()
            }

            vm.getStringResource = { stringId, params -> getString(stringId, params) }

            vm.showLoadingView.observe(viewLifecycleOwner) { params ->
                when (params.isVisible) {
                    true -> showLoading(params.text)
                    false -> hideLoading()
                }
            }

            vm.showSelectorDialog.observe(viewLifecycleOwner) { builder ->
                builder.showDialog(requireContext())
            }
        }
    }

    private fun hideOrShowBottomNavigation(shouldHide: Boolean?) {
        if (shouldHide == null) return
        if (shouldHide) {
            (activity as? BaseActivity<*>)?.hideBottomNavigation()
        } else {
            (activity as? BaseActivity<*>)?.showBottomNavigation()
        }
    }

    private fun showLoading(@StringRes resourceId: Int) {
        loadingView?.let { loadingView ->
            loadingView.visibility = View.VISIBLE
            textViewLoadingMessage?.let {
                it.text = getString(resourceId)
            }
        }
    }

    private fun hideLoading() {
        loadingView?.let {
            it.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private val inflateMethod = classVB.getMethod(
        "inflate",
        LayoutInflater::class.java,
        ViewGroup::class.java,
        Boolean::class.java
    )

    protected fun navigateToFragment(fragmentTransaction: FragmentTransaction) {
        if (activity is BaseActivity<*>) {
            (activity as BaseActivity<*>).navigateToFragment(fragmentTransaction)
        }
    }

    private fun navigateToActivity(activityTransaction: ActivityTransaction) {
        if (activity is BaseActivity<*>) {
            (activity as BaseActivity<*>).navigateToActivity(activityTransaction)
        }
    }

    protected fun popBackStack(@IdRes destinationId: Int, inclusive: Boolean = false) {
        if (activity is BaseActivity<*>) {
            (activity as BaseActivity<*>).popBackStack(destinationId, inclusive)
        }
    }

}