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
 *  * Copyright(c) Developed by John Alves at 2020/2/15 at 3:47:24 for quantic heart studios
 *
 */

package com.quanticheart.kotlingenerics

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class In : AppCompatActivity() {

    /**
     * https://www.baeldung.com/kotlin-generics
     * https://stackoverflow.com/questions/44298702/what-is-out-keyword-in-kotlin
     * https://medium.com/@elye.project/in-and-out-type-variant-of-kotlin-587e4fa2944c
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val consumer1: Consumer<Burger> =
            Everybody() // Burger extends a fastfood, extends a food, or Everybody Eat all foods
        val consumer2: Consumer<FastFood> =
            ModernPeople() // Burger extends a fastfood or ModernPeople Eat Fastfoods and Burger only
        val consumer3: Consumer<Burger> =
            American() // Burger require in American class, or American eat burger only

        /**
         * Or
         */

        val c = ParameterizedConsumer<Burger>()
        Log.w("Burger for consume?", (c is ParameterizedConsumer<Food>).toString()) // No

        val c2 = ParameterizedConsumer<FastFood>()
        Log.w("FastFood for consume?", (c2 is ParameterizedConsumer<Food>).toString()) // No

        val c3 = ParameterizedConsumer<Food>()
        Log.w("Food for consume?", (c3 is ParameterizedConsumer<Food>).toString()) // Yes

        val c4 = ParameterizedConsumer<Food>()
        Log.w("c4 for consume?", (c4 is ParameterizedConsumer<Burger>).toString()) // i have explosion burger

        /**
         * Fill
         */

        val objects: Array<Any?> = arrayOfNulls(1)

        fill(objects, 1)
    }

    /**
     * Às vezes, temos um significado situação oposta que nós temos
     * uma referência do tipo T e queremos ser capaz de atribuir-lo para o subtipo de T .

    Podemos usar a palavra-chave in no tipo genérico se desejar atribuí-la
    à referência de seu subtipo. A palavra-chave in pode ser usada apenas no
    tipo de parâmetro que é consumido, não produzido :
     */

    class ParameterizedConsumer<in T> {
        fun toString(value: T): String {
            return value.toString()
        }
    }

    open class Food
    open class FastFood : Food()
    class Burger : FastFood()

    interface Consumer<in T> {
        fun consume(item: T)
    }

    class Everybody : Consumer<Food> {
        override fun consume(item: Food) {
            println("Eat food")
        }
    }

    class ModernPeople : Consumer<FastFood> {
        override fun consume(item: FastFood) {
            println("Eat fast food")
        }
    }

    class American : Consumer<Burger> {
        override fun consume(item: Burger) {
            println("Eat burger")
        }
    }

    /**
     * Digamos que temos a seguinte situação - temos uma matriz de Qualquer tipo que seja um
     * supertipo de Int e queremos adicionar um elemento Int a essa matriz. Precisamos usar a
     * palavra-chave in como um tipo de matriz de destino para permitir que o compilador saiba
     * que podemos copiar o valor Int para essa matriz :
     */

    fun fill(dest: Array<in Int>, value: Int) {
        dest[0] = value
    }
}
