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

fun Any?.isNull(): Boolean {
    return this == null
}