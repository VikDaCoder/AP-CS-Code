
public class RecursionProblems {

	public static void main(String[] args) {
		RecursionProblems test = new RecursionProblems();
		System.out.println(test.fibnum(8));
		System.out.println(test.pattern(16));
		System.out.println(test.numA("aaHELLOa"));
		System.out.println(test.numOdd(1234567));
		System.out.println(test.sumDigits(1234567));
		System.out.println(test.num7(1077171));
	}
	
	public int fibnum(int x) { // Problem 7
		if (x <= 1) return 0;
		if (x == 2) return 1;
		return fibnum(x - 1) + fibnum(x - 2);
	}

	public String pattern(int x) { // Problem 8
		if (x <= 0) return "" + x;
		return "" + x + ", " + pattern(x - 5);
	}
	
	public int numA(String s) { // Problem 9
		if (s.length() == 0) return 0;
		if (s.charAt(0) == 'a') return 1 + numA(s.substring(1));
		return numA(s.substring(1));
	}
	
	public int numOdd(int x) { // Problem 10
		if (x <= 0) return 0;
		if (x % 2 == 1) return 1 + numOdd(x / 10);
		return numOdd(x / 10);
	}
	public int sumDigits(int x) { // Problem 11
		if (x <= 0) return 0;
		return x % 10 + sumDigits(x / 10);
	}
	
	public int num7(int x) {
		if (x == 0) return 0;
		if (x % 100 == 77) return 14 + num7(x / 100);
		if (x % 10 == 7) return 1 + num7(x / 100);
		return num7(x / 10);
	}
	
}
