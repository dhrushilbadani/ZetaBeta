# ZetaBeta
A program that calculates the value of the Riemann Zeta function (if the input is even) or the Dirichlet Beta function (if the input is even). Designed in Java.

The algorithm is based on results that I devised in a Math paper I had published earlier. (You can find them in the results-riemann-zeta.pdf file). The algorithm's time performance has been optimized using memoization and serialization. If the value at k has already been calculated, and we want the values from k, k+1, k+2...N-1, N, the algorithm's time complexity is O((N-k)^2) to calculate the values of the needed functions for these values, leading to an amortized linear-time performance.

##Usage:
<ul>
<li>Execute the program with the command: 
        ```
        java ZetaBeta N
        ```
where N is the user-provided input.</li>
<li>If N is not an integer that's greater than 1, the input will be deemed invalid.</li>
<li>Else, if N is odd, it'll display the value of Beta(N) and if N is odd, it'll display the value of Zeta(N).</li>
</ul>
