package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 23 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}


enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY,
}


fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = time
    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val diff = date.time - time
    val correctForm: String
    val minForms = listOf("минута", "минуты", "минут")
    val hourForms = listOf("час", "часа", "часов")
    val dayForms = listOf("день", "дня", "дней")
    val pluralFinder: (forms: List<String>, quantity: Long, unit: Long) -> String = { list, quantity, unit ->
        if (quantity / unit in 11L..14L) {
            list[2]
        } else {
            when ((quantity / unit) % 10) {
                1L -> list[0]
                2L, 3L, 4L -> list[1]
                in 5L..9L -> list[2]
                0L -> list[2]
                else -> throw IllegalStateException("wrong time")
            }
        }
    }
    return when (diff) {
        in 0L..SECOND -> "только что"
        in SECOND..SECOND * 45 -> "несколько секунд назад"
        in SECOND * 45..SECOND * 75 -> "минуту назад"
        in SECOND * 75..MINUTE * 45 -> {
            correctForm = pluralFinder(minForms, diff, MINUTE)
            "${diff / MINUTE} $correctForm назад"
        }
        in MINUTE * 45..MINUTE * 75 -> "час назад"
        in MINUTE * 75..HOUR * 22 -> {
            correctForm = pluralFinder(hourForms, diff, HOUR)
            "${diff / HOUR} $correctForm назад"
        }
        in HOUR * 22..HOUR * 26 -> "день назад"
        in HOUR * 26..DAY * 360 -> {
            correctForm = pluralFinder(dayForms, diff, DAY)
            "${diff / DAY} $correctForm назад"
        }
        else -> "более года назад"
    }
}

