/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw7;
import java.io.*;
/**
 *
 * @author poytr1
 */
public class HW7BinaryRead {
    public HW7BinaryRead(int size) throws FileNotFoundException, IOException, ClassNotFoundException {
    File file = new File("src/WordCount.dat");
    FileInputStream fis = new FileInputStream(file);
    BufferedInputStream bis = new BufferedInputStream(fis);
    ObjectInputStream ois = new ObjectInputStream(bis);
    Stem newStem = new Stem();
    for(int i = 0; i < size; i ++) {
        newStem = (Stem)ois.readObject();
        System.out.println(newStem.key + ":" + newStem.value);     
    }
    }   
}