package com.ogogon.matreshka.demo.swingset2

internal class Permuter(n: Int) {
    /**
     * The size of the permutation.
     */
    private val modulus: Int

    /**
     * Nonnegative integer less than n that is relatively prime to m.
     */
    private var multiplier: Int = 0

    /**
     * Pseudorandom nonnegative integer less than n.
     */
    private val addend = 22

    /**
     * Returns the integer to which this permuter maps the specified integer.
     * The specified integer must be between 0 and n-1, and the returned
     * integer will be as well.
     */
    fun map(i: Int): Int {
        return (multiplier * i + addend) % modulus
    }

    companion object {
        /**
         * Calculate GCD of a and b, which are assumed to be non-negative.
         */
        private fun gcd(a: Int, b: Int): Int {
            var a = a
            var b = b
            while (b != 0) {
                val tmp = a % b
                a = b
                b = tmp
            }
            return a
        }

        /**
         * Simple test.  Takes modulus on command line and prints out permutation.
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val modulus = args[0].toInt()
            val p = Permuter(modulus)
            for (i in 0 until modulus) {
                print(p.map(i).toString() + " ")
            }
            println()
        }
    }

    init {
        require(n >= 0)
        modulus = n
        if (n != 1) {
            // Initialize the multiplier and offset
            multiplier = Math.sqrt(n.toDouble()).toInt()
            while (gcd(multiplier, n) != 1) {
                if (++multiplier == n) {
                    multiplier = 1
                }
            }
        }


    }
}