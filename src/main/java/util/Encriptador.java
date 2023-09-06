package util;

import java.security.MessageDigest;


public class Encriptador {
	
	public String password(String password) throws Exception{
       

        // Get an instance of the SHA-256 message digest algorithm.
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // Update the message digest with the input data.
        md.update(password.getBytes());

        // Calculate the hash value.
        byte[] hash = md.digest();

        // Convert the hash value to a hexadecimal string.
        String hashString = String.format("%064x", new java.math.BigInteger(1, hash));

       return  hashString;
    }

}
