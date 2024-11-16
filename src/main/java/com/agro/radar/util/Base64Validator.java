package com.agro.radar.util;

import java.util.Base64;

public class Base64Validator {
    public static void main(String[] args) {
        String jwtSecret = "YWJjZGVmZ2hpamtsbW5vcHFyc3R1dnd4eXoxMjM0NTY3ODkrL2FiY2RlZmdoaWprbG1ub3BxcnN0dXZ3eHl6";
        try {
            byte[] decodedKey = Base64.getDecoder().decode(jwtSecret);
            System.out.println("Decoded key length: " + decodedKey.length);
            System.out.println("Key is valid.");
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid Base64 key: " + e.getMessage());
        }
    }
}
