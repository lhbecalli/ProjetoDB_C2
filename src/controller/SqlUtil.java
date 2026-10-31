package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SqlUtil {
    private static final DateTimeFormatter FORMATO_SQL = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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

    // Monta um literal de data (formato aceito pelo PostgreSQL) para concatenar na query.
    public static String data(LocalDate valor) {
        if (valor == null) {
            return "NULL";
        }
        return "'" + valor.format(FORMATO_SQL) + "'";
    }
}
