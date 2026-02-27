package a02b.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    
    private Map<JButton, Pair<Integer, Integer>> cells = new HashMap<>();
    private Logic logics;
    
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        logics = new LogicImpl(size);
        
        JPanel main = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(main);
        main.add(BorderLayout.CENTER, panel);
        JButton restart = new JButton("Check > Restart");
        main.add(BorderLayout.SOUTH, restart);
        
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent e){
        	    var button = (JButton)e.getSource();
                var pos = cells.get(button);
        	    button.setText(logics.hit(pos.getX(), pos.getY()) ? "*" : " ");
                System.out.println(pos);
            }
        };

        restart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(cells.keySet().stream().anyMatch(b -> !b.isEnabled())){
                    cells.keySet().forEach(b -> b.setEnabled(true));
                    cells.keySet().forEach(b -> b.setText(" "));
                    logics.clean();
                }
                else if(logics.check()){//manca da verificare le diagonali da questo senso: /  -> FATTO NELL'else if DOPO
                    List<Pair<Integer,Integer>> list = logics.toDisable();
                    for(int i = 1; i < size; i++){
                        if(list.get(0).getX()+i == list.get(1).getX() && list.get(0).getY()+i == list.get(1).getY()){ //capisco se scende verso destra
                            while(list.get(0).getX() > 0 && list.get(0).getY() > 0){ //risalgo alla prima cella della diagonale
                                int xi = list.get(0).getX() - 1;
                                int yi = list.get(0).getY() - 1;
                                list.set(0, new Pair<>(xi,yi));
                            }
                            int xi = list.get(0).getX();
                            int yi = list.get(0).getY();
                            while(xi<size && yi<size){
                                for(Map.Entry<JButton, Pair<Integer, Integer>> entry : cells.entrySet()){
                                    if(entry.getValue().getX() == xi && entry.getValue().getY() == yi){
                                        entry.getKey().setEnabled(false);
                                    }
                                }
                                xi++;
                                yi++;
                            }
                            break;
                        }
                        if(list.get(0).getX()+i == list.get(1).getX() && list.get(0).getY()-i == list.get(1).getY()){
                            System.out.println("si");
                            while(list.get(0).getX() > 0 && list.get(0).getY() <= size){ //risalgo alla prima cella della diagonale
                                int xp = list.get(0).getX() - 1;
                                int yp = list.get(0).getY() + 1;
                                list.set(0, new Pair<>(xp,yp));
                            }
                            int xp = list.get(0).getX();
                            int yp = list.get(0).getY();
                            while(xp<size && yp>=0){
                                for(Map.Entry<JButton, Pair<Integer, Integer>> entry : cells.entrySet()){
                                    if(entry.getValue().getX() == xp && entry.getValue().getY() == yp){
                                        entry.getKey().setEnabled(false);
                                    }
                                }
                                xp++;
                                yp--;
                            }
                            break;
                        }
                    }
                }
            }
            
        });
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                Pair<Integer,Integer> pair = new Pair<>(i,j);
                final JButton jb = new JButton(" ");
                this.cells.put(jb, pair);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }    
}
