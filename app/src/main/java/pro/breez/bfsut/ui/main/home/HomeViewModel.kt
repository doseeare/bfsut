package pro.breez.bfsut.ui.main.home

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.domain.model.output.FarmersCheckModelOut
import pro.breez.domain.model.output.FarmersModelOut

class HomeViewModel : BaseViewModel() {
    val hello = MutableLiveData<String>()

    val farmers = ArrayList<FarmersCheckModelOut>()

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        for (i in 0..20) {
            farmers.add(FarmersCheckModelOut("Садыр Жапаров"))
        }
    }

}