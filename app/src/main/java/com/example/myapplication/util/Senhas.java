package com.example.myapplication.util;



import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Senhas {

    public static String hashSenha(String senha) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(senha.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return senha;
        }
    }

    public static boolean verificarSenha(String digitada, String hashSalvo) {
        return hashSenha(digitada).equals(hashSalvo);
    }
}
