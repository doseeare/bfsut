package pro.breez.bfsut.model

import java.io.Serializable

enum class CreditStatusEnum : Serializable {
    ALL,
    SENT,
    COMMIT,
    ACCEPTED,
    DENIED
}