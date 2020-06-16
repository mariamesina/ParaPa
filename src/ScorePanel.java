import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class ScorePanel extends JPanel{

    private GameButton save;
    private ArrayList<String> stats;

    public ScorePanel()
    {
        setPreferredSize(new Dimension(Launcher.WIDTH, Launcher.HEIGHT));
        setFocusable(true);
        setVisible(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                HandleKey(e);
            }
        });

        stats = new ArrayList<>();
        stats.add("Level");
        stats.add("Experience");
        stats.add("Score");
        stats.add("Hit rate");
        stats.add("MaxCombo");

        save = new GameButton("Save", 370, 400);
        save.SetButtonState(GameButton.BUTTON_STATES.ACTIVE);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        g.drawImage(AssetsManager.background, 0, 0,null);
        g.drawImage(AssetsManager.MikuLukaBackground, 50, 0, null);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Times Roman", Font.BOLD, 26));
        for (int i=0; i<stats.size(); i++)
            g.drawString(stats.get(i), 100, 25+i*75+30);

        g.drawString(Integer.toString(Player.GetInstance().GetLevel()), 400, 25+30);
        g.drawString(Integer.toString(Player.GetInstance().GetExp()), 400, 25+75+30);
        g.drawString(Integer.toString(Player.GetInstance().score), 400, 25+2*75+30);
        g.drawString((Player.GetInstance().hit) + "/"+(Player.GetInstance().hit+Player.GetInstance().miss), 400, 25+3*75+30);
        g.drawString(Integer.toString(Player.GetInstance().currentMaxCombo), 400, 25+4*75+30);

        save.Render(g);
    }

    public void HandleKey(KeyEvent e)
    {
        if (e.getKeyCode() == 10)
        {
            Player.GetInstance().SaveStats();
            GUI.ShowPanel("MenuPanel");
        }
    }
}
