package com.fredrikkodar.Todoapplikation.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

//Hjälp-klass som innehåller en statisk metod som genererar ett RSA-nyckelpar.
public class KeyGeneratorUtility {

    public static KeyPair generateRsaKey() {

        KeyPair keyPair;

        try {
            //Skapar en KeyPaiGenerator instans för RSA-algoritmen.
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            //Initialiserar KeyPairGenerator med en nyckelstorlek på 2048 bitar.
            keyPairGenerator.initialize(2048);
            //Genererar ett nyckelpar.
            keyPair = keyPairGenerator.genKeyPair();
        } catch (Exception e) {
            //Om något går fel (t.ex. om "RSA" inte är en giltig algoritm), kastas ett IllegalStateException.
            throw new IllegalStateException();
        }
        //Returnerar det genererade nyckelparet.
        return keyPair;
    }
}
