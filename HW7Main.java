/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw7;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author poytr1
 */
public class HW7Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        StringBuffer sb = new StringBuffer();
        File file = new File("src/hw7/863-0.txt");
        FileReader fileReader = new FileReader(file);
        BufferedReader br = new BufferedReader(fileReader);
        while (br.readLine() != null) {
            sb.append(br.readLine());
        }
        String s = new String();
        s = sb.toString(); 
        
        //here to remove all non-text characters
        s = s.replaceAll("\\.", "");
        s = s.replaceAll(",", "");
        s = s.replaceAll("\"", "");
        s = s.replaceAll("\'", "");
        s = s.replaceAll("!", "");
        s = s.replaceAll(":", "");
        s = s.replaceAll("\\?", "");
        s = s.replaceAll("-", "");
        
        String[] strings = s.split(" ");
        Stemmer stemmer = new Stemmer();
        for(int i = 0; i < strings.length; i ++) {
            for (int j = 0; j < strings[i].length(); j ++)
                stemmer.add(strings[i].charAt(j));
            stemmer.stem();
            strings[i] = stemmer.toString();
        }
        
        HashMap<String,Integer> hashMap = new HashMap<String,Integer>();
        for(String str:strings) {
            if (hashMap.containsKey(str))
                hashMap.put(str, hashMap.get(str) + 1);
            else  
                hashMap.put(str, 1);
        }
        List<HashMap.Entry<String, Integer>> list = new ArrayList<HashMap.Entry<String, Integer>>(hashMap.entrySet()); 
        Collections.sort(list, new Comparator<HashMap.Entry<String, Integer>>() { 
            @Override  
            public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {   
                return o2.getValue().compareTo(o1.getValue());  
            }  
        });  
        file = new File("src/WordCount.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file , true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        ArrayList<Stem> stems = new ArrayList<Stem>();
        for (HashMap.Entry<String, Integer> mapping : list) {  
            printWriter.println(mapping.getKey() + ":" + mapping.getValue());
            Stem s1 = new Stem();
            s1.key = mapping.getKey();
            s1.value = mapping.getValue();
            stems.add(s1);
        }  
        printWriter.flush();
        fileWriter.flush();
        printWriter.close();
        fileWriter.close();
        file = new File("src/WordCount.dat");
        if(!file.exists()) 
            file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        for(int i = 0; i < stems.size(); i ++)
            oos.writeObject(stems.get(i));
        oos.close();
        try {
            HW7BinaryRead hwbr = new HW7BinaryRead(stems.size());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HW7Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
     }  
 }
    
class Stem implements Serializable{
    public String key;
    public int value;    
}
