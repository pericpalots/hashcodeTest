import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 
 */

/**
 * @author jcosta
 *
 */
public class HashCodeTest {

	/** Number of iterations to calculate. */
	private static int MAXITERS = 10000000;
	/** Maximum number of tests (maximum length 2^MAX) */
	private static int MAX = 12;
	/** Array of powers of 2 */
	private static int power[];
	/** Array of Strings */
	private static String strings[];

	private final static String letters ="abcdefghijklmnopqrstuvwxyz";

    private static String ms(long nanoseconds) {
    	return "" + nanoseconds/1000.0 + " us";
    }

    private static void initPower() {
    	power = new int[MAX];
    	int p = 1;
    	for (int i = 0; i < MAX; i++) {
    		power[i] = p;
    		p <<= 1;
    		System.out.println(i +" -> " + power[i]);
    	}
    	
    }
    
    private static void initStrings() {
    	strings = new String[MAX];
		for (int i = 0; i < MAX; i ++) {
			int length = power[i];
			StringBuilder s = new StringBuilder(length);
			for(int j = 0; j < length; j++) {
				char c = letters.charAt((int)(Math.random()*100)%letters.length());
				s.append(c);
			}
			strings[i] = s.toString();
		}
    
    }
    private static void checkHashCodeTime() {
		System.out.println("Length\t Iterations\t Time HASHCODETIME(keylength)");
		for (int j = 0; j < MAX; j++) {
			int length = power[j];
			String test = strings[j];
			int dummy = 0;
			long startTime = System.nanoTime();
			for (int i = 0; i < MAXITERS; i++) {
				dummy += test.hashCode();
			}
			long stopTime = System.nanoTime();
			long elapsedTime = stopTime - startTime;
			System.out.println("" + length + "\t"+ MAXITERS + "\t " + ms(elapsedTime) + " " + dummy);
		}
    }

    private static void checkEqualsTime() {
		System.out.println("Length\t Iterations\t Time EQUALS(keylength)");
		for (int j = 0; j < MAX; j++) {
			int length = power[j];
			String test = strings[j];
			String orig = test.substring(0, test.length()-1) + "A"; //Equal but the last char to avoid optimization!
			boolean dummy = true;
			long startTime = System.nanoTime();
			for (int i = 0; i < MAXITERS; i++) {
				dummy &= test.equals(orig);
			}
			long stopTime = System.nanoTime();
			long elapsedTime = stopTime - startTime;
			System.out.println("" + length + "\t"+ MAXITERS + "\t " + ms(elapsedTime) + " " + dummy);
		}
    }
    private static void checkEqualsTimeSameString() {
		System.out.println("Length\t Iterations\t Time EQUALS(keylength) (using same String)");
		for (int j = 0; j < MAX; j++) {
			int length = power[j];
			String test = strings[j];
			String orig = new String(test);
			boolean dummy = true;
			long startTime = System.nanoTime();
			for (int i = 0; i < MAXITERS; i++) {
				dummy &= test.equals(orig);
			}
			long stopTime = System.nanoTime();
			long elapsedTime = stopTime - startTime;
			System.out.println("" + length + "\t"+ MAXITERS + "\t " + ms(elapsedTime) + " " + dummy);
		}
    }
    
    private static void checkArrayAccessTime() {
		System.out.println("Length\t Iterations\t Time ACCESSING A POSITION IN AN ARRAY");
		for (int j = 0; j < MAX; j++) {
			int length = power[j];
			Integer [] s = new Integer[MAX];
			for(int i = 0; i < MAX; i++) {
				s[i] = power[i];
			}
			int dummy = 0;
			long startTime = System.nanoTime();
			for (int i = 0; i < MAXITERS; i++) {
				dummy += s[j];
			}
			long stopTime = System.nanoTime();
			long elapsedTime = stopTime - startTime;
			System.out.println("" + length + "\t"+ MAXITERS + "\t " + ms(elapsedTime) + " " + dummy);
		}
    }

    private static void checkGetHashMapTime() {
		System.out.println("Length\t Iterations\t Time USING A MAP");
		for (int j = 0; j < MAX; j++) {
			int length = power[j];
			String test = strings[j];
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put(test, new Integer(666));
			int dummy = 0;
			long startTime = System.nanoTime();
			for (int i = 0; i < MAXITERS; i++) {
				dummy += map.get(test);
			}
			long stopTime = System.nanoTime();
			long elapsedTime = stopTime - startTime;
			System.out.println("" + length + "\t"+ MAXITERS + "\t " + ms(elapsedTime) + " " + dummy);
		}
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String a = "abcde";
		String b = "abcde";
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		System.out.println("a == b " + (a == b));
		System.out.println("a.hashCode() == b.hashCode() "+ (a.hashCode() == b.hashCode()));
		System.out.println("a.equals(b) " + (a.equals(b)));
		String c = new String (a);
		System.out.println("c = new String(a)");
		System.out.println("c == a " + (c == a));
		System.out.println("c.hashCode() == a.hashCode() "+ (c.hashCode() == a.hashCode()));
		initPower();
		initStrings();
		checkHashCodeTime();
		checkEqualsTime();
		checkGetHashMapTime();
		checkEqualsTimeSameString();
		checkArrayAccessTime();
	}

}
