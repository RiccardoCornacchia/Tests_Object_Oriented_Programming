package provaSuGiu;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private final Map<JButton, Pair<Integer,Integer>> cells = new HashMap<>();
    private LogicImpl logic;
    
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70 * size, 70 * size);
        this.logic = new LogicImpl(size);
    
        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(panel);

        ActionListener al = e -> {
            JButton jb = (JButton)e.getSource();
            var position = cells.get(jb);
            int count = 0;
            for(Map.Entry<JButton, Pair<Integer,Integer>> entry : cells.entrySet()){
                if(entry.getKey().getText().equals("^") || entry.getKey().getText().equals("v")){
                    count++;
                }
            }
            if(count == 5 && jb.getText().equals(" ")){
                cells.keySet().forEach(b -> b.setText(" "));
                logic.go();
                cells.entrySet().forEach(en -> {
                    logic.getMap().entrySet().forEach(ent -> {
                        if(ent.getKey().equals(en.getValue())){
                            if(ent.getValue() == 1){
                                en.getKey().setText("^");
                            }
                            else if(ent.getValue() == 2){
                                en.getKey().setText("v");
                            }
                        }
                    });
                });
            }
            else{
                int val = logic.hit(position);
                if(val == 0){
                    jb.setText(" ");
                }
                if(val == 1){
                    jb.setText("^");
                }
                if(val == 2){
                    jb.setText("v");
                }
            }
        };

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final JButton jb = new JButton(" ");
                this.cells.put(jb, new Pair<>(i, j));
                jb.addActionListener(al);
                panel.add(jb);
            }
        }

        this.setVisible(true);
    }
}
