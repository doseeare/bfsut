package pro.breez.bfsut.ui.main.all_farmers

import androidx.lifecycle.LifecycleOwner
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class AllFarmersViewModel @Inject constructor() : BaseViewModel() {

    val farmers = ArrayList<String>()

    fun backClicked() {
        popBackStack.startEvent(Unit)
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        for (i in 0..20) {
            farmers.add("Акыл Акылов")
        }
    }

}