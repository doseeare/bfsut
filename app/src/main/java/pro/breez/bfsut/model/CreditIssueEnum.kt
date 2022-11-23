package pro.breez.bfsut.model

enum class CreditIssueEnum(val key: String?) {
    PLAN("pending"),
    OVERDUE("overdue"),
    ALL(null)
}