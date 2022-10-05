package pro.breez.domain.model.output

import java.io.Serializable

data class GoalModel(
    val id: Int,
    val mfsys_id: String,
    val name: String,
    val parent_id: String
): Serializable