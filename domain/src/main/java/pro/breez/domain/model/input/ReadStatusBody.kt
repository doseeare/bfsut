package pro.breez.domain.model.input

class ReadStatusBody(
    val id: String,
    val status: StatusBody
)

class StatusBody(val read_status: Boolean)