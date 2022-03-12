package im.danielzwhe.app.anywhere.extension

import androidx.appcompat.widget.AppCompatEditText
import java.math.BigDecimal

fun AppCompatEditText?.getBigDecimal(): BigDecimal {
    return try {
        BigDecimal(this?.text?.toString())
    } catch (e: Exception) {
        BigDecimal.ZERO
    }
}

fun AppCompatEditText?.getDouble(): Double {
    return this.getBigDecimal().toDouble()
}

fun AppCompatEditText?.getLong(): Long {
    return this.getBigDecimal().toLong()
}

fun AppCompatEditText?.getInt(): Int {
    return this.getBigDecimal().toInt()
}