package simplecounters;

import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class SQLiteJDBC {

    private final Statement stmt;
    private final Connection con;


    public SQLiteJDBC() throws ClassNotFoundException, SQLException {

        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:counter.db");
        con.setAutoCommit(false);
        System.out.println("Opened database successfully");
        stmt = con.createStatement();

    }

    public Statement getStmt() {
        return stmt;
    }

    public DefaultTableModel readDB() {

        Vector<String> columnNames = new Vector<>();
        Vector<Vector<Object>> data = new Vector<>();
        
        try {
            ResultSet rs = null;
            
            rs = stmt.executeQuery( "SELECT\n" +
                    "p.[name] as Место,\n" +
                    "p.[address] as Адрес,\n" +
                    "td.[name] as Счетчик,\n" +
                    "d.[serial_number] as СерийныйНомер,\n" +
                    "c.[read_date] as Дата,\n" +
                    "c.[sum_total] as Показания\n" +
                    "\n" +
                    "FROM [Counter] c \n" +
                    "JOIN [Place] p ON c.[place_id] = p.ID\n" +
                    "JOIN [Device] d ON c.[device_id] = d.ID\n" +
                    "JOIN [Type_device] td ON td.[ID] = d.[type_device_id]\n" +
                    "\n" +
                    "ORDER BY p.[name], c.[read_date] DESC;" );
            
            ResultSetMetaData metaData = rs.getMetaData();

            // names of columns
            
            int columnCount = metaData.getColumnCount();
            for (int column = 1; column <= columnCount; column++) {
                columnNames.add(metaData.getColumnName(column));
            }

            // data of the table
            
            while (rs.next()) {
                Vector<Object> vector = new Vector<>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
                data.add(vector);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new DefaultTableModel(data, columnNames);
    }
}