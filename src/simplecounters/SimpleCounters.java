/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplecounters;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author ap
 */
public class SimpleCounters {

    /**
     * @param args the command line arguments
     */
    private static SQLiteJDBC jdbc;

    public static void main(String[] args) {
        connectToDatabase();
        createFrame();
    }

    public static void connectToDatabase() {

        try {
            jdbc = new SQLiteJDBC();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SimpleCounters.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void createFrame() {

        JFrame frame = new JFrame("Показания счетчиков");
        frame.setSize(600, 400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();

        JTable myTable = new JTable(jdbc.readDB());

        panel.add(new JScrollPane(myTable));
        frame.getContentPane().add(panel);
        frame.pack();

    }

}
