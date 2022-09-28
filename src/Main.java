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

import oshi.SystemInfo;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        SystemInfo si = new SystemInfo();
        int realCores = si.getHardware().getProcessor().getPhysicalProcessorCount();
        int maxThreads = realCores + realCores/2;

        final long from = 1;
        final long to = 500 * 1000 * 1000;

        long[] executionTimeArray = new long[maxThreads];
        double[] speedUpFactorArray = new double[maxThreads];
        double[] parallelEfficiencyArray = new double[maxThreads];

        System.out.println("\nFinding primes from "+from+" to "+String.format("%,d", to)+"\n");

        for (int threads = 1; threads<= maxThreads;threads++){
            System.out.println("    "+threads + " thread(s):");
            SecuencialPrimesThread[] threadArray = new SecuencialPrimesThread[threads];
            long section = to/threads;

            long start = System.nanoTime();
            long primes = 0;

            for (int i = 0;i<threads;i++){

                long lowerLimit = from+section*i;
                long upperLimit = (i==threads-1) ? to : section*(i+1);
                threadArray[i] = new SecuencialPrimesThread(lowerLimit,upperLimit);
                threadArray[i].start();
            }

            for (int i = 0;i<threads;i++){
                try{
                    threadArray[i].join();
                    primes+=threadArray[i].getPrimes();
                }
                catch (Exception e){
                    System.out.println("Exception has been caught: " + e);
                }
            }
            long executionTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
            executionTimeArray[threads-1]=executionTime;
            double speedUpFactor = (double) executionTimeArray[0]/executionTime;
            speedUpFactorArray[threads-1]=speedUpFactor;
            double parallelEfficiency = speedUpFactor/threads;
            parallelEfficiencyArray[threads-1]=parallelEfficiency;

            System.out.println("        "+primes+" primes found in "+executionTime+" ms");
            System.out.println("        speedup factor: "+speedUpFactor);
            System.out.println("        parallel efficiency: "+parallelEfficiency);
        }
    }
}