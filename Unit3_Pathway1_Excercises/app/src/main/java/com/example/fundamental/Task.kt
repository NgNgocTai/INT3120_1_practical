
//Tasl1, task2
enum class Daypart {
    MORNING,
    AFTERNOON,
    EVENING,
}

data class Event(
    val title: String,
    val description: String? = null,
    val daypart: Daypart,
    val durationInMinutes: Int,
)

fun main() {
    //Task3
    val events = mutableListOf<Event>()

    events.add(
        Event(
            title = "Wake up",
            description = "Time to get up",
            daypart = Daypart.MORNING,
            durationInMinutes = 0
        )
    )

    val event2 = Event(title = "Eat breakfast", daypart = Daypart.MORNING, durationInMinutes = 15)
    val event3 = Event(title = "Learn about Kotlin", daypart = Daypart.AFTERNOON, durationInMinutes = 30)
    val event4 = Event(title = "Practice Compose", daypart = Daypart.AFTERNOON, durationInMinutes = 60)
    val event5 = Event(title = "Watch latest DevBytes video", daypart = Daypart.AFTERNOON, durationInMinutes = 10)
    val event6 = Event(title = "Check out latest Android Jetpack library", daypart = Daypart.EVENING, durationInMinutes = 45)

    events.addAll(listOf(event2, event3, event4, event5, event6))

    events.forEach { println(it) }

    //Task 4
    val quickEvents = events.filter { it.durationInMinutes < 60 }
    println("You have ${quickEvents.size} short events.")

    //Task5
    val groupedEvents = events.groupBy { it.daypart }
    groupedEvents.forEach { (daypart, events) ->
        println("$daypart: ${events.size} events")
    }

    //Task6
    println("Last event of the day: ${events.last().title}")

}
//Task7: extension property
val Event.durationOfEvent: String
    get() = if (this.durationInMinutes < 60) {
        "short"
    } else {
        "long"
    }