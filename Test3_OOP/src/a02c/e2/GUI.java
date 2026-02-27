package a02c.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Pair<Integer,Integer>> cells = new HashMap<>();
    ActionListener al;
    ActionListener al2;
    LogicImpl logic;
    
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70*size, 70*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        logic = new LogicImpl(size);

        al2 = e -> {
            if(end()){
                System.exit(0);
            }
            var jb = (JButton)e.getSource();
            logic.hitPoint(cells.get(jb));
            fillBlock(al2, al2);
        };
        
        al = e -> {
            var jb = (JButton)e.getSource();
            logic.firstHit(cells.get(jb));
            fillBlock(al, al2);
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
            	var pos = new Pair<>(j,i);
                final JButton jb = new JButton(" ");
                this.cells.put(jb, pos);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }
    
    private void fillBlock(ActionListener remove, ActionListener put){
        for(Map.Entry<JButton, Pair<Integer,Integer>> entry : cells.entrySet()){
            if(logic.getMap().get(entry.getValue())){
                entry.getKey().setText("*");
                if(!end()){
                    String vert = logic.isVertex(entry.getValue());
                    if(vert != ""){
                        entry.getKey().removeActionListener(remove);
                        entry.getKey().addActionListener(put);
                    }
                    else{
                        entry.getKey().removeActionListener(remove);
                    }
                }
                else{
                    entry.getKey().addActionListener(put);
                }
            }
            else{
                entry.getKey().setText("");
                entry.getKey().removeActionListener(remove);
            }
        }
    }

    public boolean end(){
        for(Map.Entry<JButton, Pair<Integer,Integer>> entry : cells.entrySet()){
            if(entry.getKey().getText().equals("*")){
                if(entry.getValue().getX() == 1 || entry.getValue().getY() == 1){
                    return true;
                }
            }
        }
        return false;
    }
}
