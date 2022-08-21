package jdbc;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ExeSqlTemplate {
    public static void main(String[] args) {
        Connection c;
        Statement stmt;
        try {
            // 1. 获取连接
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + getFilePath("test.db"));
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            // 2. 执行SQL
            stmt = c.createStatement();
            // 3. 处理SQL执行结果
            ResultSet rs = stmt.executeQuery( "SELECT * FROM USER;" );
            while ( rs.next() ) {
                int id = rs.getInt("ID");
                String  name = rs.getString("NAME");
                int age  = rs.getInt("AGE");
                System.out.println( "ID = " + id );
                System.out.println( "NAME = " + name );
                System.out.println( "AGE = " + age );
                System.out.println();
            }
            // 关闭连接
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }

    private static String getFilePath(String fileName) {
        ClassLoader classLoader = ExeSqlTemplate.class.getClassLoader();
        URL url = classLoader.getResource(fileName);
        return url.getFile();
    }
}
