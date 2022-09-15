package pro.breez.domain.model.output

data class CategoryModelOut(
    val id: Int,
    val mfsys_id: String,
    val name: String,
    val parent_id: String
)