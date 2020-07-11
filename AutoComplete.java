import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;
import java.util.StringTokenizer;
import java.util.Set;


class AutoComplete{
//    //DRIVER FUNCTION
//    public static void main(String[] args) {    
//       //follow lasy loading where 1st alphabet related word list will load when 1st character is identified\
//       //best case, omega(1)
//       //worst case O(file's words) case where all words have same 1st character
//       AutoComplete com=new AutoComplete();
//       Scanner in=new Scanner(System.in);
//       String pattern=in.next().toLowerCase();
//      Vector<String> vec= com.getCompleteWords(pattern);    //utility function call for getting words
//      for(String v:vec){
//          System.out.println(v);
//      }
//    }
    
    private HashMap<Character,HashSet<String>> map=new HashMap<>();
    AutoComplete(){
        for(Character i='a';i<='z';i++){    //constant O(26) time complexity
            map.put(i, null);   //O(1), initilizing hashmap
        }
     
    }
    protected Vector<String> getCompleteWords(String pattern){
        Vector<String> output=new Vector();
        Character key=pattern.charAt(0);    //getting 1st character for hashmap's key
        if(map.containsKey(key)){
            HashSet<String> words=map.get(key); //getting wordlist from hash map
            if(words==null){    //if key is 1st called then we will load all words for key
                try{
                loadWords(key);
                }catch(Exception e){
                    return new Vector<String>();
                }
            }
        words=map.get(key); //againg refrsh wordset from map (access all words of key from hashmap)
                for(String word:words){         // O(WORDS), iterate over all words
                    //checking word and if matched then store in output vector
                    if(word.toLowerCase().startsWith(pattern))// checking of full pattern
                    output.add(word);//insert word in output Vector
                }
            
        }
        
        return output;
    }
    private void loadWords(Character key) throws Exception{
        //open file and load word with key.
        map.put(key, new HashSet<>());
        String file=new String(Files.readAllBytes(Paths.get("optautocomplete.txt")));// fetch all file text
        StringTokenizer tk=new StringTokenizer(file,"(~~)");// break all start character's word seperately
        for(Character i='a';i<key;i++){//moving to desired key, complexity O(26)
           tk.nextToken();
        }
       tk=new StringTokenizer(tk.nextToken(),"?");//breaking word's start alphabet from all its words. 
       tk.nextToken();//removing word's start alphabet (used for identifier)
       String data=tk.nextToken();//getting all words
       tk=new StringTokenizer(data,"\n");//seperate all words
       while(tk.hasMoreTokens()){//for storing all words
           map.get(key).add(tk.nextToken());//put words in hashmap
       }
    }
    protected void showDictionary(){// utility function for display current status of hashmap
        Set<Character> s=map.keySet();
        for(Character c:s){
            System.out.println(c+" ");
            for(String d:map.get(c))
                System.out.println(d);
        }
    }
}