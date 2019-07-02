package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

private const val SECOND = 1000L
private const val MINUTE = 60L * SECOND
private const val HOUR = 60L * MINUTE
private const val DAY = 24L * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, timeUnit: TimeUnits): Date {
    return this.apply {
        time += when (timeUnit) {
            TimeUnits.SECOND -> value * SECOND
            TimeUnits.DAY -> value * DAY
            TimeUnits.HOUR -> value * HOUR
            TimeUnits.MINUTE -> value * MINUTE
        }
    }

}

fun Date.humanizeDiff(date: Date = Date()): String = when {
    this == date -> "только что"
    this.time < date.time - 365 * DAY -> "более года назад"
    this.time > date.time + 365 * DAY -> "более чем через год"
    else -> {
        val template = if (this > date) "через %s" else "%s назад"
        val (value, unit) = when (val diff = abs(this.time - date.time)) {
            in 0 until MINUTE -> diff / SECOND to TimeUnits.SECOND
            in MINUTE until HOUR -> diff / MINUTE to TimeUnits.MINUTE
            in HOUR until DAY -> diff / HOUR to TimeUnits.HOUR
            else -> diff / DAY to TimeUnits.DAY
        }
        template.format(pluralForm(value.toInt(), unit))
    }

}

private fun pluralForm(value: Int, unit: TimeUnits): String {
    val form = when {
        value % 10 == 1 && value != 11 -> 0
        value % 10 in 2..4 -> 1
        else -> 2
    }
    return arrayListOf(
        "%s секунда",
        "%s секунды",
        "%s секунд",
        "%s минута",
        "%s минуты",
        "%s минут",
        "%s час",
        "%s часа",
        "%s часов",
        "%s день",
        "%s дня",
        "%s дней"
    )[form + 3 * when (unit) {
        TimeUnits.SECOND -> 0
        TimeUnits.MINUTE -> 1
        TimeUnits.HOUR -> 2
        TimeUnits.DAY -> 3
    }].format(value)
}

enum class TimeUnits { SECOND, MINUTE, HOUR, DAY }