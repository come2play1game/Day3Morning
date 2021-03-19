class TestK {


    var publicVal: Int = 1

    @PublishedApi
    internal var internalVal: Int = 1

}


fun main() {
//    suspendCoroutine<> {  }

    print(5, 1) { i, j -> i * j }
}


fun sum(x: Int, y: Int): Int {
    return 1
}

fun print(x: Int, y: Int, sum: (Int, Int) -> Int) {
    print(sum(x, y))
}

//fun print(x: Int, y: Int, sum: (Int) -> Int) {
//    print(sum(x))
//}