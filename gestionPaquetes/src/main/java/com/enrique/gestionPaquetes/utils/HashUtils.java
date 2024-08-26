package com.enrique.gestionPaquetes.utils;

import com.enrique.gestionPaquetes.entity.Pack;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HashUtils {

    public static String calculateHash(Pack pack) throws NoSuchAlgorithmException {
        String data =pack.getId()+ pack.getName() + pack.getStatus();  // Incluir los campos relevantes
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hashBytes);
    }
}
