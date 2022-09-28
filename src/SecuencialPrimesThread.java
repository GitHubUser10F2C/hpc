/*
 * Copyright (c) 2022 Diego Urrutia-Astorga.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 *
 */

public class SecuencialPrimesThread extends Thread{

    private final long from;
    private final long to;
    private long primes;
    private long executionTime;

    SecuencialPrimesThread(long from, long to){
        this.from = from;
        this.to = to;
        this.primes = 0;
    }

    /**
     * Function to test primality.
     *
     * @param n to prime test.
     * @return true is n is prime.
     */
    public static boolean isPrime(final long n) {

        if (n <= 0) {
            throw new IllegalArgumentException("Error in n: Can't process negative numbers");
        }

        if (n == 1) {
            return false;
        }

        if (n == 2) {
            return true;
        }

        if (n % 2 == 0) {
            return false;
        }

        long max = (long) Math.sqrt(n);
        for (long i = 3; i <= max; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void run() {
        for (long k = from; k <= to; k++) {
            if (isPrime(k)) {
                primes++;
            }
        }
    }

    public long getPrimes() {
        return primes;
    }
}
