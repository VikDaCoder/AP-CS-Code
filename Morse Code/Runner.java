public class Runner
{
    public static void main(String[] args)
    {
        MorseCode m = new MorseCode();
        System.out.println(m.encode("hello world"));
        System.out.println(m.decode(".... . .-.. .-.. --- | .-- --- .-. .-.. -.."));
    }
}