package conexion;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexao {
    // Arquivo com host, banco, usuario e senha. Fica fora do Git (.gitignore).
    private static final String ARQUIVO_CONFIG = "config.local.properties";

    private static Properties config;

    public static Connection getConexao() throws SQLException {
        Properties p = carregarConfig();
        String url = "jdbc:postgresql://" + p.getProperty("db.host") + ":" + p.getProperty("db.porta", "5432")
                + "/" + p.getProperty("db.nome");
        return DriverManager.getConnection(url, p.getProperty("db.usuario"), p.getProperty("db.senha"));
    }

    // Le o arquivo so na primeira chamada.
    private static synchronized Properties carregarConfig() {
        if (config == null) {
            Properties p = new Properties();
            try (InputStream in = new FileInputStream(ARQUIVO_CONFIG)) {
                p.load(in);
            } catch (IOException e) {
                throw new IllegalStateException("Nao foi possivel ler " + ARQUIVO_CONFIG
                        + ". Copie o config.exemplo.properties e preencha os dados do banco.", e);
            }
            config = p;
        }
        return config;
    }
}
