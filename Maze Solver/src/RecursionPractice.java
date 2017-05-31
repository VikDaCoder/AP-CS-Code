public class RecursionPractice {

	public static void main(String[] args) { // FOR TESTING
		System.out.println(isPrime(47));
		System.out.println(isPrime(45));
		System.out.println(isPalindrome("Hello"));
		System.out.println(isPalindrome("noxinnixon"));
		timesTwo(48);
		timesTwo(68);
		System.out.println(gcd(18, 12));
		System.out.println(gcd(48, 72));
		System.out.println(sumArray(new int[] {2, 3, 4, 5}));
		System.out.println(reverseString("Hello"));
		printReverse("Hello");
		sequence(16);
		System.out.println();
		printSquares(6);
		System.out.println();
		System.out.println(curlyString("Hello, {I am} Vik"));
	}

	public static boolean isPrime(int n) { // Problem 1
		return isPrime(n, 2);
	}
	public static boolean isPrime(int n, int c) { // Problem 1
		if (n <= c) return true;
		if (n % c == 0) return false;
		return isPrime(n, c + 1);
	}

	public static boolean isPalindrome(String s) { // Problem 2
		if (s.length() <= 1) return true;
		if (s.charAt(0) != s.charAt(s.length() - 1)) return false;
		return isPalindrome(s.substring(1, s.length() - 1));
	}


	public static void timesTwo(int n)  { // Problem 3 
		if (n % 2 == 1) {
			System.out.println(n);
			return;
		}
		System.out.print("2 * ");
		timesTwo(n/2);
	}

	public static int gcd(int a, int b) { // Euclid's Algorithm (Problem 4)
		if (b == 0) return a;
		else return gcd(b, a % b);
	}

	public static int sumArray(int[] nums) {return sumArray(nums, 0);} // Problem 6
	public static int sumArray(int[] nums, int i) {
		if (i >= nums.length) return 0;
		return nums[i] + sumArray(nums , i + 1);
	}

	public static String reverseString(String str) { // Problem 7
		if (str.length() == 0) return "";
		return str.substring(1) + str.charAt(0);
	}
	public static void printReverse(String str) { // Problem 7
		if (str.length() == 0) return;
		System.out.println(str.charAt(str.length() - 1));
		printReverse(str.substring(0, str.length() - 1));
	}

	public static void sequence(int n) { // 9
		if (n <= 0) {
			System.out.print(n + " ");
		}
		else{
			System.out.print(n + " ");
			sequence(n - 5);
			System.out.print(n + " ");
		}
		
	}
	
	public static void printSquares(int n) {
		if (n <= 1) {
			if (n != 0) {
			System.out.print(1 + " ");
			}
			return;
		}
		if (n % 2 == 0) {
			System.out.print((n-1) * (n-1) + " ");
			printSquares(n - 2);
			System.out.print((n) * (n) + " ");
		}
		if (n % 2 != 0) {
			System.out.print(n*n + " ");
			printSquares(n - 2);
			System.out.print((n - 1)*(n - 1) + " ");
		}
	}
	
	public static String curlyString(String s) {
		if (s.charAt(0) == '{' && s.charAt(s.length() - 1) == '}') return s;
		if (s.charAt(0) == '{') return curlyString(s.substring(0,s.length() - 1));
		if (s.charAt(s.length() - 1) == '}') return curlyString(s.substring(1));
		else return curlyString(s.substring(1,s.length() - 1));
	}
}