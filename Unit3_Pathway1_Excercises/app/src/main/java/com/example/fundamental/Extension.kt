fun main() {
    val number = 5
    println("$number squared = ${number.square()}")
    println("$number cubed = ${number.cube()}")

    val text = "Kotlin"
    println("First char of \"$text\" = ${text.firstChar}")
    println("Last char of \"$text\" = ${text.lastChar}")
}

//Mở rộng hàm
fun Int.square(): Int = this * this
fun Int.cube(): Int = this * this * this

//Mở rộng thuộc tính
val String.firstChar: Char
    get() = this[0]

val String.lastChar: Char
    get() {return this[this.length - 1]}
