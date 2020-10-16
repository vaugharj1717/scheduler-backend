package com.Security;

public class PasswordGenerator {
    public static String generatePassword(){
        StringBuilder passwordBuilder = new StringBuilder();
        int passwordLength = ((int) (Math.random() * 10)) + 10;
        for(int i = 0; i < passwordLength; i++){
            int letter = (int) (Math.random() * 26);
            boolean number = (Math.random() > .5);
            int rnumber = (int) ((Math.random() * 10));
            boolean uppercase = (Math.random() > .5);
            if (uppercase && !number) {
                passwordBuilder.append((char) ('a' + letter));
            } else if (!uppercase && !number){
                passwordBuilder.append((char) ('A' + letter));
            } else{
                passwordBuilder.append((char) ('0' + rnumber));
            }
        }
        return passwordBuilder.toString();
    }
}
