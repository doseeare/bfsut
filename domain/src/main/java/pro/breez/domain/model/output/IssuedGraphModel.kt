package pro.breez.domain.model.output

data class IssuedGraphModel(
    val amount: String,
    val credit: String,
    val date_pay: String,
    val farmer: String,
    val status: String
)