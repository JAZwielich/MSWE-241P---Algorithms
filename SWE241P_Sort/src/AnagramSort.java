import java.util.ArrayList;
import java.util.List;

public class AnagramSort {

    List sortList;
    int size;

    /**
     * Group Anagram method that takes a list of string and creates a list of lists of strings that groups words if they are
     * an anagram of each other
     * @param stringsList - a list of strings. Cannot contain null slots or symbols
     * @return - a list of words with all the anagrams grouped together
     */
    public List<List<String>> groupAnagram(List<String> stringsList){
        if (stringsList.size() == 0){
            throw new RuntimeException("Cannot group anagram an empty list");
        }
        List<List<String>> listWords = new ArrayList<>();
        List<String> sortedList = new ArrayList<>();
        //First we create a list that just sorts all the words in the list into alphabetical order
        //While it also shares the same index points as the original list
        for (int i = 0; i < stringsList.size(); i++){
            String word = stringsList.get(i);
            String sortedWord = sortString(word);
            sortedList.add( i,sortedWord);
        }
        //Now we check the existing list for any repeated words. If there are we take those two index points from the original
        //List and we add the to the internal list in the list
        for(int i = 0; i < sortedList.size(); i++){
            List<Integer> allWordIndexes = new ArrayList<>();
            List<String> tempStrings = new ArrayList<>();
            for (int j = 0; j < stringsList.size(); j++){
                //check if index j matches index i
                if(sortedList.get(j) == null || sortedList.get(i) == null){
                    throw new RuntimeException("Empty string is in list");
                } else if (!sortedList.get(j).matches("^[a-zA-Z0-9]*$") || !sortedList.get(i).matches("^[a-zA-Z0-9]*$")){
                    throw new RuntimeException("found illegal characters in list to be sorted");
                }
                if (sortedList.get(j).equals(sortedList.get(i))){
                    allWordIndexes.add(j);
                }
            }
            //Add all the words we found at those indexs
            for (int j = 0; j < allWordIndexes.size(); j++){
                tempStrings.add(stringsList.get(allWordIndexes.get(j)));
            }
            //If we don't already have this group of words. Add it
            if (!listWords.contains(tempStrings)){
                listWords.add(tempStrings);
            }
        }
        return listWords;
    }

    /**
     * sort string method that checks if a string is null. Then breaks the string up into chars and sends it to the
     * quicksort method where is gets sorted to alphabetical order. Returns a sorted string.
     * @param str - Unsorted string
     * @return - Sorted String
     */
    public String sortString(String str){
        if (str.length() == 0 || str == null){
            return null;
        }
        //Breaks our string into a bunch of chars
        char letters[] = str.toCharArray();
        quickSort(letters, 0, letters.length - 1);
        String word = new String(letters);
        return word;
    }

    /**
     * recursive quick sort method. breaks the sort into smaller parts and sorts it alphabetically that way
     * @param str - list of chars originally from a string of words
     * @param beg - index point at beginning
     * @param end - index point at end
     */
    public void quickSort (char str[],int beg, int end){
        //This is where our recursive call can break out
        if (beg < end){
            //Sets the pivot point
            int pivot = partition(str, beg, end);
            //Before our pivot point
            quickSort(str, beg, pivot - 1);
            //After our pivot point
            quickSort(str, pivot + 1, end);
        }
    }

    /**
     * This is part of the quicksort method. This is where we add our pivot point for all the recursive recalls.
     * @param str - part of the char array
     * @param begin - before the pivot point
     * @param end - after the pivot point
     * @return - the new pivot point
     */
    private int partition(char str[], int begin,int end) {
        int pivot = str[end];
        int count = begin - 1;
        //loops through our begining elements then to our end elements
        for (int i = begin; i < end; i++) {
            if (str[i] <= pivot) {
                count++;
                char temp = str[count];
                //Sorts all elements to either the right or the left of the pivot point depending on
                str[count] = str[i];
                str[i] = temp;
            }
        }
        char temp = str[count + 1];
        str[count + 1] = str[end];
        str[end] = temp;
        //All elements are either greater than on the right and less on the right we now return this pivot point
        return count + 1;
    }
}
