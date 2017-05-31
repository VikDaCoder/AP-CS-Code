import java.util.*;
import java.io.*;
public class IntroToHashMaps
{
    public static void animalSounds() // Problems 1 - 4
    {
        HashMap<String,String> map = new HashMap<>();
        map.put("dog","woof");
        map.put("cat","meow");
        map.put("cow","moo");
        map.put("pig","oink");
        System.out.println(map);
        Scanner kb = new Scanner(System.in);
        System.out.print("What is your animal? >>> ");
        String animal = kb.nextLine(); 
        System.out.println(map.get(animal));
        System.out.println(map.size());
        System.out.print("What is your new animal? >>> ");
        animal = kb.nextLine();
        System.out.print("What is your animal sound? >>> ");
        String sound = kb.nextLine();
        map.put(animal,sound);
        System.out.println(map);
    }
    public static void mostWords() throws IOException// Problem 6
    {
        File file = new File("Tale of Two Cities.txt");
        Scanner in = new Scanner(file);
        HashMap<String,Integer> words = new HashMap<>();
        String most = "";
        int max = 0;
        while (in.hasNext()){
            String next = in.next();
            next = next.toLowerCase();
            if (words.containsKey(next)) words.put(next,words.get(next) + 1);
            else words.put(next,1);
            if (words.get(next) > max){
                most = next;
                max = words.get(next);
            } 
        }
        System.out.println(most + ", " + words.get(most) + " times");
    }
}