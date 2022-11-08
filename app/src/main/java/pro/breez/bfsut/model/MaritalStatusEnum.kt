package pro.breez.bfsut.model

import androidx.annotation.IdRes
import pro.breez.bfsut.R

/*
(MARRIED = "01", "Женат/Замужем"
SINGLE = "02", "Не состою в браке"
WIDOWED = "03", "Вдовец/Вдова"
DIVORCED = "04", "В разводе")
*/

enum class MaritalStatusEnum(val key: String, val value: String) {
    MARRIED("01", "Женат/Замужем"),
    SINGLE("02", "Не состою в браке"),
    WIDOWED("03", "Вдовец/Вдова"),
    DIVORCED("04", "В разводе");
    companion object {
        fun fromId(@IdRes id: Int): MaritalStatusEnum {
            return when (id) {
                R.id.married_btn -> MARRIED
                R.id.single_btn -> SINGLE
                R.id.widower_btn -> WIDOWED
                else -> DIVORCED
            }
        }
    }
}