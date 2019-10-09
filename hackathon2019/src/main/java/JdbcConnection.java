import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

        public class JdbcConnection {

            public static void main(String[] args) {

                // Connect to database
                String hostName = "jdbc:sqlserver://mysqlserveraugusta.database.windows.net";
                String dbName = "AugustaDB2019";
                String user = "augustauser@mysqlserveraugusta";
                String password = "hackathon@12345";
                String url = String.format("jdbc:sqlserver://mysqlserveraugusta.database.windows.net;database=AugustaDB2019;user=augustauser@mysqlserveraugusta;password=hackathon@12345;encrypt=true;"
                        + "hostNameInCertificate=*.database.windows.net;loginTimeout=30;", hostName, dbName, user, password);
                Connection connection = null;

                try {
                    connection = DriverManager.getConnection(url);
                    String schema = connection.getSchema();
                    System.out.println("Successful connection - Schema: " + schema);

                    System.out.println("Query data example:");
                    System.out.println("=========================================");

                    // Create and execute a SELECT SQL statement.
                    String selectSql = "SELECT TOP 20 pc.Name as CategoryName, p.name as ProductName "
                            + "FROM [SalesLT].[ProductCategory] pc "
                            + "JOIN [SalesLT].[Product] p ON pc.productcategoryid = p.productcategoryid";

                    try (Statement statement = connection.createStatement();
                         ResultSet resultSet = statement.executeQuery(selectSql)) {

                        // Print results from select statement
                        System.out.println("Top 20 categories:");
                        while (resultSet.next())
                        {
                            System.out.println(resultSet.getString(1) + " "
                                    + resultSet.getString(2));
                        }
                        connection.close();
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }