package pro.breez.bfsut.ui.main.home

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.model.navigation.FragmentTransaction
import pro.breez.domain.model.output.FarmersCheckModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel() {
    val farmersLV = MutableLiveData<ArrayList<FarmersCheckModel>>()
    var showMore = false
        set(value) {
            if (value)
                farmersLV.postValue(farmers.take(12) as ArrayList)
            else
                farmersLV.postValue(farmers.take(8) as ArrayList)
            field = !value
        }

    val hello = MutableLiveData<String>()

    val farmers = ArrayList<FarmersCheckModel>()

    fun showAll() {
        navigateToFragment.startEvent(FragmentTransaction(R.id.home_fragment_to_all_farmers))
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        for (i in 0..20) {
            farmers.add(FarmersCheckModel("Райым Маткасымов"))
        }
    }

}