import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AnagramSort sort = new AnagramSort();
        List<String> words = new ArrayList<>();
        words.add("bucket");
        words.add("rat");
        words.add("mango");
        words.add("tango");
        words.add("ogtan");
        words.add("tar");
        List<List<String>> groupOfGrams= new ArrayList<>();
        groupOfGrams = sort.groupAnagram(words);

        for(int i = 0; i < groupOfGrams.size(); i++){
            System.out.println(groupOfGrams.get(i));
        }
    }
}