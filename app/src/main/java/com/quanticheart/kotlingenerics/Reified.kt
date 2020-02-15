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
 *  * Copyright(c) Developed by John Alves at 2020/2/15 at 4:43:28 for quantic heart studios
 *
 */

package com.quanticheart.kotlingenerics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.lang.reflect.ParameterizedType
import java.util.logging.Logger
import kotlin.reflect.KClass

class Reified : AppCompatActivity() {

    /**
     * https://www.baeldung.com/kotlin-generics
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Como no Java, os genéricos do Kotlin são apagados no tempo de execução. Ou seja,
         * uma instância de uma classe genérica não preserva seus parâmetros de tipo em tempo de execução .

        Por exemplo, se criarmos um conjunto <> e colocarmos algumas cadeias nele,
        em tempo de execução, poderemos vê-lo apenas como um conjunto .

        Vamos criar dois conjuntos com dois parâmetros de tipo diferentes:
         */

        val books: Set<String> = setOf("1984", "Brave new world")
        val primes: Set<Int> = setOf(2, 3, 11)

        books.iterator()

        /**
         * Em tempo de execução, as informações de tipo para Set <> e Set <Int>
         *     serão apagadas e nós os vemos como conjuntos simples . Portanto, mesmo
         *     que seja perfeitamente possível descobrir em tempo de execução que o valor
         *     é um conjunto , não podemos dizer se é um conjunto de cadeias, números inteiros
         *     ou outra coisa: essas informações foram apagadas.

        Então, como o compilador do Kotlin nos impede de adicionar uma Não-String a um Set <> ? Ou,
        quando obtemos um elemento de um Set <> , como ele sabe que o elemento é uma String ?

        A resposta é simples. O compilador é o responsável por apagar as informações de tipo,
        mas antes disso, ele realmente sabe que a variável books contém elementos String .
         */

        val c = getClassJava<Parametrized>()

    }

    /**
     * Portanto, toda vez que obtemos um elemento dele, o compilador o
     * converterá em uma String ou, quando adicionarmos um elemento, o compilador digitará check a entrada.
     */
    fun <T> Iterable<*>.filterIsInstance1() = filter { it is T }

    /**
     * A parte " it is T" , para cada elemento da coleção, verifica se o elemento
     * é uma instância do tipo T , mas como as informações do tipo foram apagadas
     * no tempo de execução, não podemos refletir sobre os parâmetros do tipo dessa maneira.

    Ou podemos?

    A regra de apagamento de tipo é verdadeira em geral, mas há um caso em que
    podemos evitar essa limitação: Funções embutidas . Os parâmetros de tipo das
    funções em linha podem ser reificados , para que possamos nos referir a esses parâmetros em tempo de execução.

    O corpo das funções embutidas está embutido. Ou seja, o compilador substitui o
    corpo diretamente em locais onde a função é chamada, em vez da invocação normal da função.

    Se declararmos a função anterior como embutida e marcarmos o parâmetro de tipo como reificado ,
    poderemos acessar informações de tipo genéricas em tempo de execução:
     */

    inline fun <reified T> Iterable<*>.filterIsInstance2() = filter { it is T }

    inline fun <reified T> getClassJava(): Class<T> = T::class.java
    fun <T> test(): T {
        return T
    }
}
