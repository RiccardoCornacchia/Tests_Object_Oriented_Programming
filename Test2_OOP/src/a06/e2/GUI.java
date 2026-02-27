package a06.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    
    private final List<JButton> cells = new ArrayList<>();
    //private final Map<Pair<Integer,Integer>, Integer> map = new HashMap<>();
    //int fireTimes = 0;
    //int sames;
    
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        JPanel main = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(main);
        main.add(BorderLayout.CENTER, panel);
        JButton fire = new JButton("Fire");
        main.add(BorderLayout.SOUTH, fire);

        Random random = new Random();
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                int randVal = 1 + random.nextInt(2);
                final JButton jb = new JButton(String.valueOf(randVal));
                this.cells.add(jb);
                panel.add(jb);
                //Pair<Integer, Integer> pair = new Pair<Integer,Integer>(i, j);
                //map.put(pair, randVal);
            }
        }
        Logic logic = new LogicImpl(size, cells);
        
        fire.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                logic.hitFire(cells);
                if(logic.finish()){
                    fire.setEnabled(false);
                }
            }
        });
        /*
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent e){
        	    var button = (JButton)e.getSource();
        	    var position = cells.indexOf(button);
                button.setText(""+position);
            }
        };*/
        this.setVisible(true);
    }
}
