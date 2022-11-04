package pro.breez.bfsut.util

fun Boolean.ifTrue(block: () -> Unit) {
    if (this) {
        block.invoke()
    }
}

fun Boolean.ifFalse(block: () -> Unit) {
    if (!this) {
        block.invoke()
    }
}

fun <T> T?.ifNull(block: () -> Unit) {
    if (this == null)
        block.invoke()
}

fun <T> T?.ifNotNull(block: (T) -> Unit) {
    if (this != null)
        block.invoke(this)
}

fun Any?.isNull(): Boolean {
    return this == null
}