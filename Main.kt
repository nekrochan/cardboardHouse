fun main() {
    build("дерево")("шифер")("кирпич")(arrayOf(3, 6, 1))(arrayOf(
        "трава",
        "мангал",
        "4 шезлонга",
        "2 дерева"
    ))
}

// 5. КАРРИРОВАНИЕ
fun build(mainPart: String): (String) -> (String) -> (Array<Int>) -> (Array<String>) -> Unit {
    return { roof: String ->
        { basement: String ->
            { arr: Array<Int> ->
                { surroundings: Array<String> ->
                    println("1. Клеим дом из картона. Имитируем материалы: " +
                            "основной объем - $mainPart, крыша - $roof, цоколь - $basement")

                    // 4. КОМПОЗИЦИЯ
                    // распаковка массива в переменные
                    val (levels, rooms, doors) = arr
                    fun countHoles(levels: Int, rooms: Int): Int { return levels * rooms }  // функция для вычисления количества прорезей
                    fun excludeDoors(holes: Int, doors: Int): Int { return holes - doors }  // окна - все прорези кроме дверей
                    fun compose(  // функция compose для объединения двух функций (описываем сигнатуру)
                        f: (Int, Int) -> Int,
                        g: (Int, Int) -> Int
                    ): (Int, Int, Int) -> Int = { levels, rooms, doors ->
                        val holes = g(levels, rooms)
                        f(holes, doors)
                    }
                    val windows = compose(::excludeDoors, ::countHoles)(levels, rooms, doors)
                    println("2. Вырезаем окна: $windows шт., дверей: $doors шт.")

                    // 1 и 2. АРГУМЕНТ, РЕЗУЛЬТАТ
                    paint { decorate()() }

                    // 3. ФУНКЦИЯ ВЫСШЕГО ПОРЯДКА
                    surroundings.forEach {
                        println("5. Добавляем окружение дома: $it")
                    }

                }
            }
        }
    }
}

// 1. функция как аргумент
fun paint(function: () -> Unit) {
    println("3. Наносим грунт и красим модель основными цветами")
    function()
}

// 2. возврат функции
fun decorate(): () -> Unit {
    return { println("4. Добавляем текстуры и тени") }
}
