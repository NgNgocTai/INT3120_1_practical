enum class Difficulty(val points: Int) {
    EASY(100),
    MEDIUM(200),
    HARD(300)
}

data class Question<T>(
    val questionText: String,
    val answer: T,
    val difficulty: Difficulty

)

class Quiz {
    val question1 = Question<String>("Quoth the raven ___", "nevermore", Difficulty.MEDIUM)
    val question2 = Question<Boolean>("The sky is green. True or false", false, Difficulty.EASY)
    val question3 = Question<Int>("How many days are there between full moons?", 28, Difficulty.HARD)

    companion object StudentProgress {
        var total: Int = 10
        var answered: Int = 3
    }
}

fun main() {
    val question1 = Question<String>(
        "Quoth the raven ___",
        "nevermore",
        Difficulty.MEDIUM
    )

    val question2 = Question<Boolean>(
        "The sky is green. True or false",
        false,
        Difficulty.EASY
    )

    val question3 = Question<Int>(
        "How many days are there between full moons?",
        28,
        Difficulty.HARD
    )

    println(question1.toString()) // in địa chỉ nếu không phải data class

    println("${question1.questionText} [${question1.difficulty}, points=${question1.difficulty.points}]")
    println("${question2.questionText} [${question2.difficulty}, points=${question2.difficulty.points}]")
    println("${question3.questionText} [${question3.difficulty}, points=${question3.difficulty.points}]")

    //Singleton
    println("${Quiz.answered} of ${Quiz.total} answered.")

}
