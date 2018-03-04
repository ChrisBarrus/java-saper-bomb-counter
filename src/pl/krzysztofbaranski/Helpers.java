package pl.krzysztofbaranski;

import java.util.Random;

public class Helpers {

    public static int getRandomNumber(int min, int max){

        Random generator = new Random();
        return generator.nextInt(max) + min;

    }
}
