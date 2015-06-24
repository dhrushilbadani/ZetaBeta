# ZetaBeta
A program that calculates the value of the Riemann Zeta function (if the input is even) or the Dirichlet Beta function (if the input is even). Designed in Java.

The algorithm is based on results that I devised in a Math paper I had published earlier. (You can find them in the results-riemann-zeta.pdf file). The algorithm's time performance has been optimized using memoization and serialization. If the value at k has already been calculated, and we want the value at N, the algorithm's time complexity is O((N-k)^2) to calculate the value of the needed function for the first N values.

USAGE:

1. Execute the program with the command: "java ZetaBeta N" where N is the user-provided input.

2. If N is not an integer that's greater than 1, the input will be deemed invalid.

3. Else, if N is odd, it'll display the value of Beta(N) and if N is odd, it'll display the value of Zeta(N).