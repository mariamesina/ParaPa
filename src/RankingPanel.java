import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Map;

public class RankingPanel extends JPanel{

    private ArrayList<String> playersNames;
    private ArrayList<Integer> scores;
    private GameButton mainMenu;

    public RankingPanel()
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
        playersNames = new ArrayList<>();
        scores = new ArrayList<>();
        mainMenu = new GameButton("Main Menu", 370, 400);
        mainMenu.SetButtonState(GameButton.BUTTON_STATES.ACTIVE);
        UpdateRanking();
    }

    public void UpdateRanking()
    {
        playersNames.clear();
        scores.clear();
        for (Map.Entry<String, Integer> entry : DBManager.GetInstance().GetRankings().entrySet())
        {
            //System.out.println(entry);
            playersNames.add(entry.getKey());
            scores.add(entry.getValue());
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(AssetsManager.background, 0, 0, null);
        g.drawImage(AssetsManager.MikuLukaBackground, 50, 0, null);

        mainMenu.Render(g);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Times Roman", Font.BOLD, 26));
        for (int i=0; i<playersNames.size(); i++)
        {
            g.drawString(playersNames.get(i), 350, 25+i*75+30);
            g.drawString(Integer.toString(scores.get(i)), 550, 25+i*75+30);
        }

    }

    public void HandleKey(KeyEvent e)
    {
        if (e.getKeyCode()==27)
            GUI.ShowPanel("MenuPanel");
        else if (e.getKeyCode() == 10)
            GUI.ShowPanel("MenuPanel");
    }

}
