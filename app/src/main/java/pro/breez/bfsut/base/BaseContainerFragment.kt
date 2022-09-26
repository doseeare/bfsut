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
import pro.breez.bfsut.ui.main.log.pager.active.ActiveLogFragment
import pro.breez.bfsut.ui.main.log.pager.all.AllLogFragment
import pro.breez.bfsut.ui.main.log.pager.calculated.CalculatedLogFragment
import java.lang.reflect.ParameterizedType

abstract class BaseContainerFragment<VB : ViewBinding> :
    Fragment() {

    private var _binding: VB? = null
    protected val binding: VB get() = _binding!!

    private val type = (javaClass.genericSuperclass as ParameterizedType)
    private val classVB = type.actualTypeArguments[0] as Class<VB>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateMethod.invoke(null, layoutInflater, container, false) as VB
        return _binding!!.root
    }

    private val inflateMethod = classVB.getMethod(
        "inflate",
        LayoutInflater::class.java,
        ViewGroup::class.java,
        Boolean::class.java
    )

}