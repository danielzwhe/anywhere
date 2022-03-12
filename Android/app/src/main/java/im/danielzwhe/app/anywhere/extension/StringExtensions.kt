package im.danielzwhe.app.anywhere.extension

import java.math.BigDecimal

fun String?.toBigDecimalOrZero(): BigDecimal {
    return try {
        BigDecimal(this)
    } catch (e: Exception) {
        BigDecimal.ZERO
    }
}