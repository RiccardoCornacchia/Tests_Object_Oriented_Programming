package a06.e2;

import java.util.List;

import javax.swing.JButton;

public interface Logic {
    
    public void hitFire(List<JButton> list);

    public boolean finish();

    public int hasDuplicate(int column);
}
