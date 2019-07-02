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

fun Date.humanizeDiff(date: Date = Date()): String{
    val diff: Long
    if(date.time == Date().time){
        //сравниваем с внутренним состоянием
        diff = this.time - Date().time
    }
    else{
        //сравниваем с заданным в параметре
        diff = date.getTime() - Date().time
    }

    if(diff < 0){
        when(diff){
            in -1*1000..0 -> return("только что")
            in -45*1000..-1*1000+1 -> return("несколько секунд назад")
            in -75*1000..-45*1000+1 -> return("минуту назад")
            in -45*60*1000..-75*1000+1 -> {
                val min: Int = -(diff/1000/60).toInt()
                var minText = "минут"
                when(min.toString().substring(min.toString().length-1).toInt()){
                    1 -> minText = "минута"
                    in (2..4) -> minText = "минуты"
                }
                if(min in (11..14)) minText = "минут"
                return("$min $minText назад")
            }
            in -75*60*1000..-45*60*1000+1 -> return("час назад")
            in -22*60*60*1000..-75*60*1000+1 -> {
                val hours: Int = -(diff/1000/60/60).toInt()
                var hourText = "часов"
                when(hours.toString().substring(hours.toString().length-1).toInt()){
                    1 -> hourText = "час"
                    in (2..4) -> hourText = "часа"
                }
                if(hours in (11..14)) hourText = "часов"
                return("$hours $hourText назад")
            }
            in -26*60*60*1000..-22*60*60*1000+1 -> return("день назад")
            in -360*24*60*60*1000..-26*60*60*1000+1 -> {
                val days: Int = -(diff/1000/60/60/24).toInt()
                var daysText = "дней"
                when(days.toString().substring(days.toString().length-1,1).toInt()){
                    1 -> daysText = "день"
                    in (2..4) -> daysText = "дня"
                }
                if(days in (11..14)) daysText = "дней"
                return("$days $daysText назад")
            }
            else -> return("более года назад")
        }
    }
    else{
        when(diff){
            in 0..1*1000 -> return("только что")
            in 1*1000+1..45*1000 -> return("через несколько секунд")
            in 45*1000+1..75*1000 -> return("через минуту")
            in 75*1000+1..45*60*1000 -> {
                val min: Int = (diff/1000/60).toInt()
                var minText = "минут"
                when(min.toString().substring(min.toString().length-1).toInt()){
                    1 -> minText = "минута"
                    in (2..4) -> minText = "минуты"
                }
                if(min in (11..14)) minText = "минут"
                return("через $min $minText")
            }
            in 45*60*1000..75*60*1000 -> return("через час")
            in 75*60*1000+1..22*60*60*1000 -> {
                val hours: Int = (diff/1000/60/60).toInt()
                var hourText = "часов"
                when(hours.toString().substring(hours.toString().length-1).toInt()){
                    1 -> hourText = "час"
                    in (2..4) -> hourText = "часа"
                }
                if(hours in (11..14)) hourText = "часов"
                return("через $hours $hourText")
            }
            in 22*60*60*1000+1..26*60*60*1000 -> return("через день")
            in 26*60*60*1000+1..360*24*60*60*1000 -> {
                val days: Int = (diff/1000/60/60/24).toInt()
                var daysText = "дней"
                when(days.toString().substring(days.toString().length-1).toInt()){
                    1 -> daysText = "день"
                    in (2..4) -> daysText = "дня"
                }
                if(days in (11..14)) daysText = "дней"
                return("через $days $daysText")
            }
            else -> return("через год и более")
        }
    }

}

enum class TimeUnits { SECOND, MINUTE, HOUR, DAY }