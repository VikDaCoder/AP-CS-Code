import java.util.*;
import java.io.*;
public class MorseCode
{
    private final String alphabet = "abcdefghijklmnopqrstuvwxyz0123456789 ";
    private final String[] morse = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--..", ".----", "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----.", "-----", "|"};
    private HashMap<String, Character> toText;
    private HashMap<Character, String> toCode;
    public MorseCode()
    {
        toText = new HashMap<>();
        toCode = new HashMap<>();
        for(int i = 0; i < morse.length; i++) toCode.put(alphabet.charAt(i),morse[i]);
        for(int i = 0; i < morse.length; i++) toText.put(morse[i],alphabet.charAt(i));
    }
    public String encode(String s)
    {
        s = s.toLowerCase();
        char[] c = s.toCharArray();
        String ans = "";
        for (int i = 0; i < c.length; i++) ans += toCode.get(c[i]) + " ";
        return ans.trim();
    }
    public String decode(String s)
    {
        Scanner in = new Scanner(s);
        String ans = "";
        while (in.hasNext()) ans += "" + toText.get(in.next());
        return ans;
    }
}