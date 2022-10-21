package pro.breez.bfsut.model

import androidx.annotation.IdRes
import pro.breez.bfsut.R

enum class GenderEnum(val key: Int) {
    MALE(1),
    FEMALE(0);

    companion object {
        fun fromId(@IdRes id: Int): GenderEnum {
            return when (id) {
                R.id.gender_male -> MALE
                else -> FEMALE
            }
        }
    }
}