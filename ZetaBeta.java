import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.math.BigDecimal;
public class ZetaBeta implements Serializable {
    //ArrayList<RatPoly> polynomials;
    RatPoly currPolynomial;
    ArrayList<BigRational> vals;
    int calculated;

    public ZetaBeta() {
        calculated = 1;
        //polynomials = new ArrayList<RatPoly>();
        vals = new ArrayList<BigRational>();
        BigRational[] c = new BigRational[2];
        c[0] = new BigRational(1, 1);
        c[1] = new BigRational(-1, 1);
        int[] p = new int[2];
        p[0] = 1;
        p[1] = 0;
        currPolynomial =  new RatPoly(c, p, 1);
        //polynomials.add(0, new RatPoly(c, p, 1));
        vals.add(0, new BigRational(1, 6));
    }

    //calculates all Zeta and Beta polynomials and values upto n.
    public void calcUptoN(int n) {
        BigRational v;
        BigDecimal d;
        int percent = 0, currPercent = 0;
        if (n <= calculated) {
            System.out.println("Already calculated");
        }
        for (int m = calculated; m <= n; m += 1) {
            if (m % 2 == 0) {
                currPolynomial = currPolynomial.integrate2();
                v = currPolynomial.evalAt0();
                d = new BigDecimal(Math.pow(2, m+3) - 2);
                v = v.divides(new BigRational(d.toBigInteger()));
            }
            else {
                currPolynomial = currPolynomial.integrate();
                v = currPolynomial.evalAtC();
                d = new BigDecimal(Math.pow(2, m + 3));
                v = v.divides(new BigRational(d.toBigInteger()));
            }
            //polynomials.add(m, p);
            vals.add(m, v);
            currPercent = (int) (((m - calculated) * 100)/ (n - calculated));
            if (currPercent > percent) {
                System.out.println((int) percent+"% done...");
                percent = currPercent;
            }
        }
        calculated = Math.max(calculated, n);
    }

    public BigRational getVal(int n) {
        calcUptoN(n-2);
        System.out.println("Completed calculations. The result is:");
        System.out.println("\u03C0"+"^"+n+" "+vals.get(n-2));
        return vals.get(n-2);
    }

    public BigRational zeta(int n) {
        if (n == 2) {
            System.out.println("\u03C0"+"^2 1/6");
            return new BigRational(1, 6);
        } else if (n > 2 && n % 2 == 0) {
            return getVal(n);
        } else {
            System.out.println("Invalid");
            return new BigRational(0);
        }
    }

    public BigRational beta(int n) {
        if (n == 1) {
            System.out.println("\u03C0 1/4");
            return new BigRational(1, 4);
        } else if (n >= 3 && n % 2 == 1) {
            return getVal(n);
        } else {
            System.out.println("Invalid");
            return new BigRational(0);
        }
    }

    private static void saveState(ZetaBeta zb) {
        if (zb == null) {
            return;
        }
        try {
            File zbFile = new File("zb.ser");
            FileOutputStream fileOut = new FileOutputStream(zbFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(zb);
        } catch (IOException e) {
            System.out.println(e+" while saving the state of the program.");
        }
    }

    private static ZetaBeta loadState() {
        ZetaBeta zb = new ZetaBeta();
        File zbFile = new File("zb.ser");
        String msg="";
        if (zbFile.exists()) {
            try {
                FileInputStream fileIn = new FileInputStream(zbFile);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                zb = (ZetaBeta) objectIn.readObject();
            } catch (IOException e) {
                msg = "IOException while loading the saved state of the program.";
                System.out.println(e+msg);
            } catch (ClassNotFoundException e) {
                msg = "ClassNotFoundException while loading state.";
                System.out.println(e+msg);
            }
        }
        return zb;
    }

    public static void main(String[] args) {
        ZetaBeta zb = ZetaBeta.loadState();
        double d = Double.parseDouble(args[0]);
        int n = (int) Math.floor(d);
        if (n == d && !Double.isInfinite(d) && n > 1) {
            if (n % 2 == 0) { //1 corresponds to zeta
                zb.zeta(n);
            } else { //everything else corresponds to beta
                zb.beta(n);
            }
        } else {
            System.out.println("Invalid input.");
        }
        ZetaBeta.saveState(zb);
    }

}