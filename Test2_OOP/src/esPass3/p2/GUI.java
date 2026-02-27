package esPass3.p2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Pair<Integer, Integer>> cells = new HashMap<>();
    private final LogicImpl logic;
    private int scoperte;
    private List<Pair<Integer,Integer>> disable = new ArrayList<>();//TIENE CONTO DELLE CELLE DISABILITATE
    private ActionListener al;
    
    public GUI(int width) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70*width, 70*width);
        logic = new LogicImpl(width);
        scoperte = 0;
        
        JPanel panel = new JPanel(new GridLayout(width,width));
        this.getContentPane().add(panel);
        
        al = e -> {
            var jb = (JButton)e.getSource();
            jb.removeActionListener(al);
            if(scoperte == 0){
                logic.close();
                for (Map.Entry<JButton, Pair<Integer, Integer>> entry : cells.entrySet()) {
                    if(!disable.contains(entry.getValue())){
                        entry.getKey().setText(" ");
                    }
                }
            }
            if(scoperte < 2){
                if(!logic.open(cells.get(jb))){
                    for (Map.Entry<Pair<Integer, Integer>, Integer> entry : logic.getMap().entrySet()) {
                        if(entry.getKey().equals(this.cells.get(jb))){
                            jb.setText(String.valueOf(entry.getValue()));
                        }
                    }
                    scoperte++;
                }
            }
            //CHECK PER DISABILITARE LE CASELLE UGUALI TROVATE
            if(scoperte == 2){
                List<Pair<Integer, Integer>> uguali = new ArrayList<>();
                for (Map.Entry<JButton, Pair<Integer, Integer>> entry : cells.entrySet()) {
                    if(entry.getKey().getText() != " " && !disable.contains(entry.getValue())){
                        entry.getKey().addActionListener(al);
                        uguali.add(entry.getValue());
                    }
                }
                if(logic.check(uguali.get(0), uguali.get(1))){
                    //disabilita i bottoni corrispondenti ai due pair
                    cells.entrySet().forEach(entry -> {
                        if((entry.getValue().equals(uguali.get(0)) || entry.getValue().equals(uguali.get(1)))){
                            entry.getKey().setEnabled(false);
                            disable.add(entry.getValue());
                        }
                    });
                }
                this.scoperte = 0;
            }
            if(logic.end(disable)){
                cells.entrySet().forEach(entry -> entry.getKey().setEnabled(false));
            }
            System.out.println(cells.get(jb));
        };
                
        for (int i=0; i<width; i++){
            for (int j=0; j<width; j++){
            	var pos = new Pair<>(i, j);
                final JButton jb = new JButton(" ");
                this.cells.put(jb, pos);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }

        this.setLocationByPlatform(true);
        this.setVisible(true);
    }
}
