package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs
import ru.skillbranch.devintensive.extensions.TimeUnits.*

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = SECOND): Date {
    this.time += units.value * value
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val dif = abs(this.time -  date.time)
    val isPast = this.time < date.time

    return when {
        dif <= SECOND.value -> {"только что"}
        dif <= SECOND.value * 45 -> getTenseForm("несколько секунд", isPast)
        dif <= SECOND.value * 75 -> getTenseForm("минуту", isPast)
        dif <= MINUTE.value * 45 -> getTenseForm(getNormalizedInterval(dif, MINUTE), isPast)
        dif <= MINUTE.value * 75 -> getTenseForm("час", isPast)
        dif <= HOUR.value * 22 -> getTenseForm(getNormalizedInterval(dif, HOUR), isPast)
        dif <= HOUR.value * 26 -> getTenseForm("день", isPast)
        dif <= DAY.value * 360 -> getTenseForm(getNormalizedInterval(dif, DAY), isPast)
        else -> if(isPast) "более года назад" else "более чем через год"
    }
}

fun getTenseForm(interval: String, isPast: Boolean): String {
    val prefix = if (isPast) "" else "через"
    val postfix = if (isPast) "назад" else ""
    return "$prefix $interval $postfix".trim()
}

fun getNormalizedInterval(dif: Long, units: TimeUnits): String {
    val amount = dif / units.value
    return "$amount ${getPluralForm(amount, units)}"
}

fun getPluralForm(amount: Long, units: TimeUnits): String {
    val posAmount = abs(amount) % 100

    return when(posAmount){
        1L -> Plurals.ONE.get(units)
        in 2..4 -> Plurals.FEW.get(units)
        0L, in 5..19 -> Plurals.MANY.get(units)
        else -> getPluralForm(posAmount % 10, units)
    }
}

enum class Plurals(private val minute: String, private val hour: String, private val day: String){
    ONE("минуту", "час", "день"),
    FEW("минуты", "часа", "дня"),
    MANY("минут", "часов", "дней");

    fun get(unit: TimeUnits): String {
        return when(unit){
            MINUTE -> minute
            HOUR -> hour
            DAY -> day
            else -> ""
        }
    }
}

enum class TimeUnits(val value:Long){
    SECOND(1000L),
    MINUTE(60 * SECOND.value),
    HOUR(60 * MINUTE.value),
    DAY(24 * HOUR.value);
}