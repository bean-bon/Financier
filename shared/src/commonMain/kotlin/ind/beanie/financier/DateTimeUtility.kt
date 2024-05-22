package ind.beanie.financier

import kotlinx.datetime.*

object DateTimeUtility {

    fun currentLocalDateMillis(): Long {
        return Clock.System.now().toEpochMilliseconds()
    }

    fun standardDateTimeFormat(dateMillis: Long, timeMillis: Long): String {
        val instant = Instant.fromEpochMilliseconds(dateMillis)
        val tz = TimeZone.currentSystemDefault()
        val withTime = instant.plus(timeMillis, DateTimeUnit.MILLISECOND, tz)
        val date = withTime.toLocalDateTime(tz)
        val hour = date.hour.toString().padStart(2, '0')
        val minute = date.minute.toString().padStart(2, '0')
        return "${date.dayOfMonth}/${date.monthNumber}/${date.year}, ${hour}:${minute}"
    }

    fun standardDateTimeFormat(epochMillis: Long): String {
        val instant = Instant.fromEpochMilliseconds(epochMillis)
        val from = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        val hour = from.hour.toString().padStart(2, '0')
        val minute = from.minute.toString().padStart(2, '0')
        return "${from.dayOfMonth}/${from.monthNumber}/${from.year}, ${hour}:${minute}"
    }

    fun currentTime(): Time {
        val clock = Clock.System.now()
        val localDateTime = clock.toLocalDateTime(TimeZone.currentSystemDefault())
        return Time(localDateTime.hour, localDateTime.minute)
    }

}

data class Time(
    val hour: Int,
    val minute: Int
)




