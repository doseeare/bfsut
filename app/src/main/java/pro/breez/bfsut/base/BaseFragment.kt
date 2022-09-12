package pro.breez.bfsut.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.viewbinding.ViewBinding
import pro.breez.bfsut.model.navigation.ActivityTransaction
import pro.breez.bfsut.model.navigation.FragmentTransaction
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment() {

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
        viewModel = createViewModelLazy(classVM.kotlin, { viewModelStore }).value
        lifecycle.addObserver(viewModel)
        setupBaseViewModel()
        return _binding!!.root
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