package fr.auth;

import javax.xml.bind.DatatypeConverter;
/**
 * Decode la base 64 et recupère les données
 * authent a refaire:
 * -mot de passe hacher en MD5
 * -le cookie stock Base64(login:pass(hacher))
 * @author asvevi
 *
 */
public class BasicAuth {
    public static String[] decode(String auth) {

        byte[] decodedBytes = DatatypeConverter.parseBase64Binary(extractBase64(auth));

        if (decodedBytes == null || decodedBytes.length == 0) {
            return null;
        }

        return new String(decodedBytes).split(":", 2);
    }

    private static String extractBase64(String auth) {
        return auth.replaceFirst("[B|b]asic ", "");
    }
}
