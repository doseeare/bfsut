package pro.breez.bfsut.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.viewbinding.ViewBinding
import pro.breez.bfsut.MainActivity
import pro.breez.bfsut.model.navigation.ActivityTransaction
import pro.breez.bfsut.model.navigation.FragmentTransaction
import pro.breez.bfsut.ui.main.active_logs.ActiveLogFragment
import pro.breez.bfsut.ui.main.all_logs.AllLogFragment
import pro.breez.bfsut.ui.main.credit.CreditIssuedTabFragment
import pro.breez.bfsut.ui.main.credit.CreditStatusTabFragment
import pro.breez.bfsut.ui.main.credit.CreditsFragment
import pro.breez.bfsut.ui.main.credit_issued.CreditIssuedFragment
import pro.breez.bfsut.ui.main.credit_status.CreditStatusFragment
import pro.breez.bfsut.ui.main.home.HomeFragment
import pro.breez.bfsut.ui.main.log.LogFragment
import pro.breez.bfsut.ui.main.paid_logs.PaidLogsFragment
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> :
    Fragment() {

    private var _binding: VB? = null
    protected val binding: VB get() = _binding!!
    protected lateinit var viewModel: VM private set

    private val type = (javaClass.genericSuperclass as ParameterizedType)
    private val classVB = type.actualTypeArguments[0] as Class<VB>
    private val classVM = type.actualTypeArguments[1] as Class<VM>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateMethod.invoke(null, layoutInflater, container, false) as VB
        return _binding!!.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = createViewModelLazy(classVM.kotlin, { viewModelStore }).value
        lifecycle.addObserver(viewModel)
        setupBaseViewModel()
        Log.d("Fragments", "onCreate: ${this::class.java}")
    }

    override fun onResume() {
        super.onResume()
        when (this) {
            is ActiveLogFragment,
            is PaidLogsFragment,
            is AllLogFragment,
            is HomeFragment,
            is LogFragment,
            is CreditStatusTabFragment,
            is CreditStatusFragment,
            is CreditIssuedTabFragment,
            is CreditIssuedFragment,
            is CreditsFragment -> hideOrShowBottomNavigation(false)
            else -> hideOrShowBottomNavigation(true)
        }
    }

    override fun onPause() {
        super.onPause()
        when (this) {
            is HomeFragment,
            is LogFragment,
            is CreditsFragment -> (requireActivity() as MainActivity).fabSelected = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("Fragments", "onDestroyView: ${this::class.java}")
    }

    private fun setupBaseViewModel() {
        viewModel.let { vm ->
            vm.navigateToFragment.observe(this) {
                navigateToFragment(it)
            }

            vm.navigateToActivity.observe(this) {
                navigateToActivity(it)
            }

            vm.popBackStack.observe(this) {
                popBackStack(it)
            }
            vm.showBottomSheetFragment.observe(this) { fragment ->
                fragment.show(childFragmentManager, fragment.tag)
            }

            vm.showSnackBar.observe(this) { builder ->
                builder.create(requireView()).show()
            }

            vm.getStringResource = { stringId, params -> getString(stringId, params) }

            vm.showLoadingView.observe(this) { params ->
                when (params.isVisible) {
                    true -> showLoading(params.text)
                    false -> hideLoading()
                }
            }

            vm.showSelectorDialog.observe(this) { builder ->
                builder.showDialog(requireContext())
            }
            vm.requiredArguments = { requireArguments() }

            vm.showAlertDialog.observe(this) {
                it.create(requireContext())
            }
            vm.showDialogFragment.observe(this) {
                it.show(childFragmentManager, "QuestionDialog")
            }
            vm.previousScreen.observe(this) {
                requireActivity().onBackPressed()
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
        (activity as? BaseActivity<*>)?.showLoading(resourceId)
    }

    private fun hideLoading() {
        (activity as? BaseActivity<*>)?.hideLoading()
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

    protected fun popBackStack(@IdRes id: Int? = null) {
        if (activity is BaseActivity<*>) {
            (activity as BaseActivity<*>).popBackStack(id)
        }
    }

}