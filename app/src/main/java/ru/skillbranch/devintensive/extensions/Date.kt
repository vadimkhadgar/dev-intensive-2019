package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
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

fun Date.humanizeDiff(): String {
    var periodDate: Pair<Date, Date>

    //    только что
    periodDate = Pair(Date().add(1, TimeUnits.SECOND), Date().add(-1, TimeUnits.SECOND))
    if (this.before(periodDate.first) && this.after(periodDate.second)) {
        return "только что"
    }

    //    несколько секунд назад
    periodDate = Pair(Date().add(45, TimeUnits.SECOND), Date().add(-45, TimeUnits.SECOND))
    if (this.before(periodDate.first) && this.after(periodDate.second)) {
        return when {
            this.before(Date()) -> "несколько секунд назад"
            else -> "через несколько секунд"
        }
    }

    //    минуту назад
    periodDate = Pair(Date().add(75, TimeUnits.SECOND), Date().add(-75, TimeUnits.SECOND))
    if (this.before(periodDate.first) && this.after(periodDate.second)) {
        return when {
            this.before(Date()) -> "минуту назад"
            else -> "через минуту"
        }
    }

    //    N минут назад
    periodDate = Pair(Date().add(45, TimeUnits.MINUTE), Date().add(-45, TimeUnits.MINUTE))
    if (this.before(periodDate.first) && this.after(periodDate.second)) {
        val duration = TimeUnit.MINUTES.convert((Date().time - this.time), TimeUnit.MILLISECONDS).toInt()
        val plurals =  getPlurals(abs(duration), Triple("минуту", "минуты", "минут"))
        return when {
            duration > 0 -> "$duration $plurals назад"
            else -> "через ${abs(duration)} $plurals"
        }
    }

    //    час назад
    periodDate = Pair(Date().add(75, TimeUnits.MINUTE), Date().add(-75, TimeUnits.MINUTE))
    if (this.before(periodDate.first) && this.after(periodDate.second)) {
        return when {
            this.before(Date()) -> "час назад"
            else -> "через час"
        }
    }

    //    N часов назад
    periodDate = Pair(Date().add(22, TimeUnits.HOUR), Date().add(-22, TimeUnits.HOUR))
    if (this.before(periodDate.first) && this.after(periodDate.second)) {
        val duration = TimeUnit.HOURS.convert((Date().time - this.time), TimeUnit.MILLISECONDS).toInt()
        val plurals =  getPlurals(abs(duration), Triple("час", "часа", "часов"))
        return when {
            duration > 0 -> "$duration $plurals назад"
            else -> "через ${abs(duration)} $plurals"
        }
    }

    //    день назад
    periodDate = Pair(Date().add(26, TimeUnits.HOUR), Date().add(-26, TimeUnits.HOUR))
    if (this.before(periodDate.first) && this.after(periodDate.second)) {
        return when {
            this.before(Date()) -> "день назад"
            else -> "через день"
        }
    }

    //    N дней назад
    periodDate = Pair(Date().add(360, TimeUnits.DAY), Date().add(-360, TimeUnits.DAY))
    if (this.before(periodDate.first) && this.after(periodDate.second)) {
        val duration = TimeUnit.DAYS.convert((Date().time - this.time), TimeUnit.MILLISECONDS).toInt()
        val plurals =  getPlurals(abs(duration), Triple("день", "дня", "дней"))
        return when {
            duration > 0 -> "$duration $plurals назад"
            else -> "через ${abs(duration)} $plurals"
        }
    }

    return when {
        this.before(Date()) -> "более года назад"
        else -> "более чем через год"
    }
}

fun getPlurals(value: Int, dict: Triple<String, String, String>): String {
    return when {
        value % 100 in 5..20 -> dict.third
        value % 10 == 1 -> dict.first
        value % 10 in 2..4 -> dict.second
        else -> dict.third
    }
}

enum class TimeUnits { SECOND, MINUTE, HOUR, DAY }