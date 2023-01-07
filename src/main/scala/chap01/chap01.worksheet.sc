def multiplyBy3 = (number : Int) => number * 3
// def multiplyBy2 = (number : Int) => number * 2
val x = multiplyBy3 compose multiplyBy2
val multiplyBy2 = (number: Int) => number * 2 
//val multiplyBy2: Int => Int = Lambda$1328/0x0000000800549410@66863941
val multiplyBy6= multiplyBy3 compose multiplyBy2
val input = List(35, 1, 4, 25, 6, 30, 8, 15, 24)
input.map(multiplyBy2)
input.map((number : Int) => number * 2 )