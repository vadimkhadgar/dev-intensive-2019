package ru.skillbranch.devintensive.extension


import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 23 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd:MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

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

TODO()
//    val t: Int = 1
//
//
//
//       if (Date().add(t, units = TimeUnits.SECOND)<= 1 && Date().add(t, units = TimeUnits.SECOND >= 0)  "только что"
//
//        0 с -1 с "только что"
//
//            1с - 45 с "несколько секунд назад"
//
//        45 с -75 с "минуту назад"
//
//            75с - 45 мин "N минут назад"
//
//        45 мин -75 мин "час назад"
//
//            75мин
//        22 ч "N часов назад"
//
//            22ч - 26 ч "день назад"
//
//        26 ч -360 д "N дней назад"
//
//                > 360 д "более года назад"
//    }
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY
}