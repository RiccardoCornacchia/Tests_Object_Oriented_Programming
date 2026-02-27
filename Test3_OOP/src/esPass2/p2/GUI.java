package esPass2.p2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Pair<Integer, Integer>> cells = new HashMap<>();
    private LogicImpl logic;
    private boolean end = false;
    
    public GUI(int width, int height) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70*width, 70*height);
        this.logic = new LogicImpl(height, width);
        
        JPanel panel = new JPanel(new GridLayout(height, width));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            var jb = (JButton)e.getSource();
            jb.setText("*");
            logic.hit(cells.get(jb));
            for(Map.Entry<JButton, Pair<Integer, Integer>> entry : cells.entrySet()){
                if(logic.getList().contains(entry.getValue())){
                    if(entry.getKey().getText().equals("o")){
                        this.end = true;
                    }
                    entry.getKey().setText("*");
                }
            }
            System.out.println(cells.get(jb));
            if(end){
                System.exit(0);
            }
        };
                
        for (int i=0; i<height; i++){
            for (int j=0; j<width; j++){
            	var pos = new Pair<>(i, j);
                final JButton jb;
                if(pos.equals(logic.getRand())){
                    jb = new JButton("o");
                }
                else{
                    jb = new JButton(" ");
                    jb.addActionListener(al);
                }
                this.cells.put(jb, pos);
                panel.add(jb);
            }
        }

        this.setVisible(true);
    }
    
}
