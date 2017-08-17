//-*- mode:java; encoding:utf-8 -*-
// vim:set fileencoding=utf-8:
//http://ateraimemo.com/Swing/DragRowsAnotherTable.html

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import javax.activation.*;
import javax.swing.*;
import javax.swing.table.*;

public final class MainPanel extends JPanel {

    private final TransferHandler handler = new TableRowTransferHandler();
    private final String[] columnNames = {"String", "Integer", "Boolean"};
    private final Object[][] data = {
        {"AAA", 12, true}, {"aaa", 1, false},
        {"BBB", 13, true}, {"bbb", 2, false},
        {"CCC", 15, true}, {"ccc", 3, false},
        {"DDD", 17, true}, {"ddd", 4, false},
        {"EEE", 18, true}, {"eee", 5, false},
        {"FFF", 19, true}, {"fff", 6, false},
        {"GGG", 92, true}, {"ggg", 0, false}
    };

    private JTable makeDnDTable() {
        JTable table = new JTable(new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int column) {
                // ArrayIndexOutOfBoundsException: 0 >= 0
                // Bug ID: JDK-6967479 JTable sorter fires even if the model is empty
                // http://bugs.java.com/view_bug.do?bug_id=6967479
                //return getValueAt(0, column).getClass();
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return Number.class;
                    case 2:
                        return Boolean.class;
                    default:
                        return super.getColumnClass(column);
                }
            }
        });
        table.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setTransferHandler(handler);
        table.setDropMode(DropMode.INSERT_ROWS);
        table.setDragEnabled(true);
        table.setFillsViewportHeight(true);
        //table.setAutoCreateRowSorter(true); //XXX

        //Disable row Cut, Copy, Paste
        ActionMap map = table.getActionMap();
        Action dummy = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* Dummy action */ }
        };
        map.put(TransferHandler.getCutAction().getValue(Action.NAME), dummy);
        map.put(TransferHandler.getCopyAction().getValue(Action.NAME), dummy);
        map.put(TransferHandler.getPasteAction().getValue(Action.NAME), dummy);
        return table;
    }

    public MainPanel() {
        super(new BorderLayout());
        JPanel p = new JPanel(new GridLayout(2, 1));
        p.add(new JScrollPane(makeDnDTable()));
        p.add(new JScrollPane(makeDnDTable()));
        p.setBorder(BorderFactory.createTitledBorder("Drag & Drop JTable"));
        add(p);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setPreferredSize(new Dimension(320, 240));
    }

    public static void main(String... args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public static void createAndShowGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        JFrame frame = new JFrame("DragRowsAnotherTable");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(new MainPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}