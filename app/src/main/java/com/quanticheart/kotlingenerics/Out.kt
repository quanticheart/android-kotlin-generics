/*
 *
 *  *                                     /@
 *  *                      __        __   /\/
 *  *                     /==\      /  \_/\/
 *  *                   /======\    \/\__ \__
 *  *                 /==/\  /\==\    /\_|__ \
 *  *              /==/    ||    \=\ / / / /_/
 *  *            /=/    /\ || /\   \=\/ /
 *  *         /===/   /   \||/   \   \===\
 *  *       /===/   /_________________ \===\
 *  *    /====/   / |                /  \====\
 *  *  /====/   /   |  _________    /      \===\
 *  *  /==/   /     | /   /  \ / / /         /===/
 *  * |===| /       |/   /____/ / /         /===/
 *  *  \==\             /\   / / /          /===/
 *  *  \===\__    \    /  \ / / /   /      /===/   ____                    __  _         __  __                __
 *  *    \==\ \    \\ /____/   /_\ //     /===/   / __ \__  ______  ____ _/ /_(_)____   / / / /__  ____ ______/ /_
 *  *    \===\ \   \\\\\\\/   ///////     /===/  / / / / / / / __ \/ __ `/ __/ / ___/  / /_/ / _ \/ __ `/ ___/ __/
 *  *      \==\/     \\\\/ / //////       /==/  / /_/ / /_/ / / / / /_/ / /_/ / /__   / __  /  __/ /_/ / /  / /_
 *  *      \==\     _ \\/ / /////        |==/   \___\_\__,_/_/ /_/\__,_/\__/_/\___/  /_/ /_/\___/\__,_/_/   \__/
 *  *        \==\  / \ / / ///          /===/
 *  *        \==\ /   / / /________/    /==/
 *  *          \==\  /               | /==/
 *  *          \=\  /________________|/=/
 *  *            \==\     _____     /==/
 *  *           / \===\   \   /   /===/
 *  *          / / /\===\  \_/  /===/
 *  *         / / /   \====\ /====/
 *  *        / / /      \===|===/
 *  *        |/_/         \===/
 *  *                       =
 *  *
 *  * Copyright(c) Developed by John Alves at 2020/2/15 at 2:59:13 for quantic heart studios
 *
 */

package com.quanticheart.kotlingenerics

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class Out : AppCompatActivity() {

    /**
     * https://www.baeldung.com/kotlin-generics
     * https://stackoverflow.com/questions/44298702/what-is-out-keyword-in-kotlin
     * https://medium.com/@elye.project/in-and-out-type-variant-of-kotlin-587e4fa2944c
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val production1: Production<In.Food> = FoodStore()
        val production2: Production<In.FastFood> = FastFoodStore()
        val production3: Production<In.Burger> = InOutBurger()
        val production4: Production<In.Burger> = BurgerKing()
        val production5: Production<In.Food> = Restaurant()

        /**
         * Or
         */

        val c = ParameterizedProducer(In.Burger())
        Log.w("Burger is food?", (c is ParameterizedProducer<In.Food>).toString())

        val c2 = ParameterizedProducer(In.Food())
        Log.w("Food is a Burger?", (c2 is ParameterizedProducer<In.Burger>).toString()) // No

        val c3 = ParameterizedProducer(1L)
        Log.w("Burger is food?", (c3 is ParameterizedProducer<Number>).toString())

        val c4 = ParameterizedProducer("BOOM!!")
        Log.w(
            "Burger is food?",
            (c4 is ParameterizedProducer<Number>).toString()
        ) // Fail, c4 is a Text, no a Number, Terrorists wins

        /**
         * Copy
         */

        val ints: Array<Int> = arrayOf(1, 2, 3)
        val any: Array<Any?> = arrayOfNulls(3)

        copy(ints, any)

        /**
         * or
         */

        val ints2: Array<String> = arrayOf("c", "c2")
        val any2: Array<Any?> = arrayOfNulls(2)

        copy(ints2, any2) // Error
    }

    /**
     * Digamos que queremos criar uma classe de produtores que produzirá um resultado de
     * algum tipo T. Às vezes; queremos atribuir esse valor produzido a uma referência que é de um supertipo do tipo T.

    Para conseguir isso usando o Kotlin, precisamos usar a palavra -
    chave out no tipo genérico. Isso significa que podemos atribuir essa
    referência a qualquer um de seus supertipos. O valor de saída pode ser
    produzido apenas pela classe fornecida, mas não consumido :
     */
    class ParameterizedProducer<out T>(private val value: T) {
        fun get(): T {
            return value
        }
    }

    open class Food
    open class FastFood : In.Food()
    class Burger : In.FastFood()

    interface Production<out T> {
        fun produce(): T
    }

    class FoodStore : Production<In.Food> {
        override fun produce(): In.Food {
            println("Produce food")
            return In.Food()
        }
    }

    class FastFoodStore : Production<In.FastFood> {
        override fun produce(): In.FastFood {
            println("Produce fast food")
            return In.FastFood()
        }
    }

    class InOutBurger : Production<In.Burger> {
        override fun produce(): In.Burger {
            println("Produce burger")
            return In.Burger()
        }
    }

    class BurgerKing : Production<In.Burger> {
        override fun produce(): In.Burger {
            println("Produce burger")
            return In.Burger()
        }
    }

    class Restaurant : Production<In.Food> {
        override fun produce(): In.Food {
            println("Produce burger")
            return In.Food()
        }
    }

    /**
     * Digamos que temos uma matriz de algum tipo e queremos copiar toda a matriz
     * na matriz de Qualquer tipo. É uma operação válida, mas para permitir que o
     * compilador compile nosso código, precisamos anotar o parâmetro de entrada com a palavra - chave out .

    Isso permite que o compilador saiba que o argumento de
    entrada pode ser de qualquer tipo que seja um subtipo do Any :
     */

    fun copy(from: Array<out Any>, to: Array<Any?>) {
        assert(from.size == to.size)
        for (i in from.indices)
            to[i] = from[i]
    }
}
