/*
 * Copyright 2025-2026 Abhimanyu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("MaxLineLength")

package com.makeappssimple.abhimanyu.core.kotlin

import java.io.Serializable

public data class Quadruple<out A, out B, out C, out D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
) : Serializable {
    override fun toString(): String {
        return "($first, $second, $third, $fourth)"
    }
}

public fun <T> Quadruple<T, T, T, T>.toList(): List<T> {
    return listOf(
        first,
        second,
        third,
        fourth
    )
}

public data class Quintuple<out A, out B, out C, out D, out E>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
) : Serializable {
    override fun toString(): String {
        return "($first, $second, $third, $fourth, $fifth)"
    }
}

public fun <T> Quintuple<T, T, T, T, T>.toList(): List<T> {
    return listOf(
        first,
        second,
        third,
        fourth,
        fifth
    )
}

public data class Sextuple<out A, out B, out C, out D, out E, out F>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
) : Serializable {
    override fun toString(): String {
        return "($first, $second, $third, $fourth, $fifth, $sixth)"
    }
}

public fun <T> Sextuple<T, T, T, T, T, T>.toList(): List<T> {
    return listOf(
        first,
        second,
        third,
        fourth,
        fifth,
        sixth
    )
}

public data class Septuple<out A, out B, out C, out D, out E, out F, out G>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G,
) : Serializable {
    override fun toString(): String {
        return "($first, $second, $third, $fourth, $fifth, $sixth, $seventh)"
    }
}

public fun <T> Septuple<T, T, T, T, T, T, T>.toList(): List<T> {
    return listOf(
        first,
        second,
        third,
        fourth,
        fifth,
        sixth,
        seventh
    )
}

public data class Octuple<out A, out B, out C, out D, out E, out F, out G, out H>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G,
    val eighth: H,
) : Serializable {
    override fun toString(): String {
        return "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth)"
    }
}

public fun <T> Octuple<T, T, T, T, T, T, T, T>.toList(): List<T> {
    return listOf(
        first,
        second,
        third,
        fourth,
        fifth,
        sixth,
        seventh,
        eighth
    )
}

public data class Nonuple<out A, out B, out C, out D, out E, out F, out G, out H, out I>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G,
    val eighth: H,
    val ninth: I,
) : Serializable {
    override fun toString(): String {
        return "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth)"
    }
}

public fun <T> Nonuple<T, T, T, T, T, T, T, T, T>.toList(): List<T> {
    return listOf(
        first,
        second,
        third,
        fourth,
        fifth,
        sixth,
        seventh,
        eighth,
        ninth
    )
}

public data class Decuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G,
    val eighth: H,
    val ninth: I,
    val tenth: J,
) : Serializable {
    override fun toString(): String {
        return "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth, $tenth)"
    }
}

public fun <T> Decuple<T, T, T, T, T, T, T, T, T, T>.toList(): List<T> {
    return listOf(
        first,
        second,
        third,
        fourth,
        fifth,
        sixth,
        seventh,
        eighth,
        ninth,
        tenth
    )
}

public data class Undecuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G,
    val eighth: H,
    val ninth: I,
    val tenth: J,
    val eleventh: K,
) : Serializable {
    override fun toString(): String {
        return "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth, $tenth, $eleventh)"
    }
}

public fun <T> Undecuple<T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> {
    return listOf(
        first,
        second,
        third,
        fourth,
        fifth,
        sixth,
        seventh,
        eighth,
        ninth,
        tenth,
        eleventh
    )
}

public data class Duodecuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G,
    val eighth: H,
    val ninth: I,
    val tenth: J,
    val eleventh: K,
    val twelfth: L,
) : Serializable {
    override fun toString(): String {
        return "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth, $tenth, $eleventh, $twelfth)"
    }
}

public fun <T> Duodecuple<T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> {
    return listOf(
        first,
        second,
        third,
        fourth,
        fifth,
        sixth,
        seventh,
        eighth,
        ninth,
        tenth,
        eleventh,
        twelfth
    )
}

public data class Tredecuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G,
    val eighth: H,
    val ninth: I,
    val tenth: J,
    val eleventh: K,
    val twelfth: L,
    val thirteenth: M,
) : Serializable {
    override fun toString(): String {
        return "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth, $tenth, $eleventh, $twelfth, $thirteenth)"
    }
}

public fun <T> Tredecuple<T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> {
    return listOf(
        first,
        second,
        third,
        fourth,
        fifth,
        sixth,
        seventh,
        eighth,
        ninth,
        tenth,
        eleventh,
        twelfth,
        thirteenth,
    )
}

public data class Quattuordecuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G,
    val eighth: H,
    val ninth: I,
    val tenth: J,
    val eleventh: K,
    val twelfth: L,
    val thirteenth: M,
    val fourteenth: N,
) : Serializable {
    override fun toString(): String {
        return "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth, $tenth, $eleventh, $twelfth, $thirteenth, $fourteenth)"
    }
}

public fun <T> Quattuordecuple<T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> {
    return listOf(
        first,
        second,
        third,
        fourth,
        fifth,
        sixth,
        seventh,
        eighth,
        ninth,
        tenth,
        eleventh,
        twelfth,
        thirteenth,
        fourteenth,
    )
}

public data class Quindecuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G,
    val eighth: H,
    val ninth: I,
    val tenth: J,
    val eleventh: K,
    val twelfth: L,
    val thirteenth: M,
    val fourteenth: N,
    val fifteenth: O,
) : Serializable {
    override fun toString(): String {
        return "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth, $tenth, $eleventh, $twelfth, $thirteenth, $fourteenth, $fifteenth)"
    }
}

public fun <T> Quindecuple<T, T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> {
    return listOf(
        first,
        second,
        third,
        fourth,
        fifth,
        sixth,
        seventh,
        eighth,
        ninth,
        tenth,
        eleventh,
        twelfth,
        thirteenth,
        fourteenth,
        fifteenth,
    )
}

public data class Sexdecuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G,
    val eighth: H,
    val ninth: I,
    val tenth: J,
    val eleventh: K,
    val twelfth: L,
    val thirteenth: M,
    val fourteenth: N,
    val fifteenth: O,
    val sixteenth: P,
) : Serializable {
    override fun toString(): String {
        return "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth, $tenth, $eleventh, $twelfth, $thirteenth, $fourteenth, $fifteenth, $sixteenth)"
    }
}

public fun <T> Sexdecuple<T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> {
    return listOf(
        first,
        second,
        third,
        fourth,
        fifth,
        sixth,
        seventh,
        eighth,
        ninth,
        tenth,
        eleventh,
        twelfth,
        thirteenth,
        fourteenth,
        fifteenth,
        sixteenth,
    )
}

public data class Septendecuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L, out M, out N, out O, out P, out Q>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G,
    val eighth: H,
    val ninth: I,
    val tenth: J,
    val eleventh: K,
    val twelfth: L,
    val thirteenth: M,
    val fourteenth: N,
    val fifteenth: O,
    val sixteenth: P,
    val seventeenth: Q,
) : Serializable {
    override fun toString(): String {
        return "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth, $tenth, $eleventh, $twelfth, $thirteenth, $fourteenth, $fifteenth, $sixteenth, $seventeenth)"
    }
}

public fun <T> Septendecuple<T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> {
    return listOf(
        first,
        second,
        third,
        fourth,
        fifth,
        sixth,
        seventh,
        eighth,
        ninth,
        tenth,
        eleventh,
        twelfth,
        thirteenth,
        fourteenth,
        fifteenth,
        sixteenth,
        seventeenth
    )
}