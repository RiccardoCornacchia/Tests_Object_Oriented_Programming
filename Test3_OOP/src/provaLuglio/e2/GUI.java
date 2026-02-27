package provaLuglio.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private Map<JButton, Pair<Integer, Integer>> cells = new HashMap<>();
    private LogicImpl logic;
    private ActionListener al;
    private ActionListener al2;
    private ActionListener al3;
    
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(80*size, 80*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        logic = new LogicImpl(size);

        al3 = e -> {
            System.exit(0);
        };

        al2 = e -> {
            var jb = (JButton)e.getSource();
            var center = logic.nextHit(cells.get(jb));
            cells.forEach((but, pos) -> {
                if(logic.getMap().get(pos)){
                    but.setText("*");
                }
                else{
                    but.setText("");
                    but.removeActionListener(al2);
                    if(Math.abs(pos.getX()-center.getX()) <= 1){
                        but.addActionListener(al2);
                    }
                }
                if(pos.equals(center)){
                    but.addActionListener(al3);
                }
            });
        };

        al = e -> {
            var jb = (JButton)e.getSource();
            logic.hit(cells.get(jb));
        	cells.forEach((but, pos) -> {
                if(logic.getMap().get(pos)){
                    but.setText("*");
                    but.removeActionListener(al);
                }
                else{
                    but.removeActionListener(al);
                    if(Math.abs(pos.getX()-cells.get(jb).getX()) <= 1){
                        but.addActionListener(al2);
                    }
                }
            });
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
            	var pos = new Pair<>(j,i);
                final JButton jb = new JButton();
                this.cells.put(jb, pos);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }
    
}
