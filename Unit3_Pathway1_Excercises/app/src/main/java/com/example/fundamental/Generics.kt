class Box<T>(val item: T)

fun main() {
    val stringBox = Box("Hello Kotlin")
    val intBox = Box(123)
    val booleanBox = Box(true)

    println(stringBox.item)
    println(intBox.item)
    println(booleanBox.item)
}
