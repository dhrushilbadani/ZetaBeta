import java.io.Serializable;
public class RatPoly implements Serializable {
    public final static RatPoly ZERO = new RatPoly(BigRational.ZERO, 0);

    BigRational[] coef;   // coefficients
    int[] cpow;
    int deg;              // degree of polynomial

    public RatPoly(BigRational[] c, int[] p, int d) {
        coef = c;
        cpow = p;
        deg = d;
    }

    public RatPoly(BigRational a, int b) {
        coef = new BigRational[b+1];
        cpow = new int[b+1];
        for (int i = 0; i < b; i++)
            coef[i] = BigRational.ZERO;
        coef[b] = a;
        deg = degree();
    }

    // k * c^(powOfC) * x^(powOfD)
    public RatPoly(BigRational k, int powOfC, int powOfX) {
        coef = new BigRational[powOfX + 1];
        for (int i = 0; i < powOfX; i++) {
            coef[i] = BigRational.ZERO;
            cpow[i] = 0;
        }
        coef[powOfX] = k;
        cpow[powOfX] = powOfC;
        deg = degree();
    }


    // return the degree of this polynomial (0 for the zero polynomial)
    public int degree() {
        int d = 0;
        for (int i = 0; i < coef.length; i++)
            if (coef[i].compareTo(BigRational.ZERO) != 0) d = i;
        return d;
    }

    // return this(c)
    public BigRational evalAtC() {
        BigRational val = new BigRational(0);
        for (int i = 0; i < coef.length; i++) {
            val = val.plus(coef[i]);
        }
        return val;
    }

    // return this(0)
    public BigRational evalAt0() {
        return coef[0];
    }


    // return antiderivative, which is also the definite integral from 0 to x
    public RatPoly integrate() {
        RatPoly integral = new RatPoly(BigRational.ZERO, deg + 1);
        for (int i = 0; i <= deg; i++) {
            integral.coef[i+1] = coef[i].divides(new BigRational(i + 1));
            integral.cpow[i+1] = cpow[i];
        }
        integral.deg = integral.degree();
        return integral;
    }

    // return the definite integral from x to c
    public RatPoly integrate2() {
        RatPoly integral = new RatPoly(BigRational.ZERO, deg + 1);
        BigRational constantTerm = BigRational.ZERO;
        for (int i = 0; i <= deg; i++) {
            integral.coef[i+1] = coef[i].divides(new BigRational(-i - 1));
            integral.cpow[i+1] = cpow[i];
            constantTerm = constantTerm.plus(integral.coef[i+1]);
        }
        integral.coef[0] = constantTerm.negate();
        integral.cpow[0] = deg + 1;
        integral.deg = integral.degree();
        return integral;
    }
}
