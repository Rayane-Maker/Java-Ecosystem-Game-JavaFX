package ArrayUtil;

import java.util.Random;

public class RandomOperations {
    public static Object[] shuffle(Object[] object){

        for (int i = 0; i < object.length; i++){
            Random rnd = new Random();
            int index = rnd.nextInt(object.length);
            Object temp = object[i];
            object[i] = object[index];
            object[index] = temp;
        }
        return object;
    }
}
