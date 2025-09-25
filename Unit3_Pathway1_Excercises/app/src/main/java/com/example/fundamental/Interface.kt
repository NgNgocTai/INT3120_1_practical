interface ProgressPrintable {
    val progressText: String
    fun printProgressBar()
}
class Quizz : ProgressPrintable {
    val total = 10
    val answered = 3

    // Override property
    override val progressText: String
        get() = "$answered of $total answered"

    // Override function
    override fun printProgressBar() {
        repeat(answered) { print("▓") }
        repeat(total - answered) { print("▒") }
        println()
        println(progressText)
    }
}
fun main() {
    val quiz = Quizz()
    quiz.printProgressBar()
}
