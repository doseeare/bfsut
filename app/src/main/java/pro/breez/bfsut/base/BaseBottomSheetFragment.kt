package pro.breez.bfsut.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.createViewModelLazy
import androidx.viewbinding.ViewBinding
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import pro.breez.bfsut.R
import pro.breez.bfsut.model.navigation.ActivityTransaction
import pro.breez.bfsut.model.navigation.FragmentTransaction
import java.lang.reflect.ParameterizedType

abstract class BaseBottomSheetFragment<VB : ViewBinding, VM : BaseViewModel> :
    SuperBottomSheetFragment() {

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
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = inflateMethod.invoke(null, layoutInflater, container, false) as VB
        viewModel = createViewModelLazy(classVM.kotlin, { viewModelStore }).value
        lifecycle.addObserver(viewModel)
        setupBaseViewModel()
        return _binding!!.root
    }

    override fun getExpandedHeight(): Int {
        return  ViewGroup.LayoutParams.WRAP_CONTENT
    }

    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLoadingView()
    }

    private fun setupLoadingView() {
        val view = LayoutInflater
            .from(context)
            .inflate(
                R.layout.view_progress,
                (view as ViewGroup), false
            )

        loadingView = view.findViewById(R.id.progress_overlay)
        textViewLoadingMessage = view.findViewById(R.id.textView_progressMessage)
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

            vm.showDialogFragment.observe(this){
                it.show(childFragmentManager, "QuestionDialog")
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

    protected fun popBackStack(@IdRes destinationId: Int, inclusive: Boolean = false) {
        if (activity is BaseActivity<*>) {
            (activity as BaseActivity<*>).popBackStack(destinationId, inclusive)
        }
    }

}