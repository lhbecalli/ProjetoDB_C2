package controller;

public class SqlUtil {

    // Monta um literal de texto para concatenar na query. Duplica as aspas simples.
    public static String texto(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return "NULL";
        }
        return "'" + valor.trim().replace("'", "''") + "'";
    }

    public static String numero(Integer valor) {
        if (valor == null) {
            return "NULL";
        }
        return String.valueOf(valor);
    }
}
