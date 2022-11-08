package pro.breez.bfsut.model

import androidx.annotation.IdRes
import pro.breez.bfsut.R

enum class GenderEnum(val key: Int, val value: String) {
    MALE(1, "Мужской"),
    FEMALE(0, "Женский");

    companion object {
        fun fromId(@IdRes id: Int): GenderEnum {
            return when (id) {
                R.id.gender_male -> MALE
                else -> FEMALE
            }
        }

        fun fromValue(value: String): GenderEnum {
            return when (value) {
                "Мужской" -> MALE
                else -> FEMALE
            }
        }
    }
}