import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class MenuPanel extends JPanel{

    private ArrayList<GameButton> buttons;
    private int selectedOption = 0;

    public MenuPanel()
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

        buttons = new ArrayList<>();
        buttons.add(new GameButton("New Game", 600, 50));
        buttons.add(new GameButton("Settings", 600, 200));
        buttons.add(new GameButton("Ranking", 600, 350));
        buttons.get(0).SetButtonState(GameButton.BUTTON_STATES.ACTIVE);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        g.drawImage(AssetsManager.background, 0, 0,null);
        g.drawImage(AssetsManager.MikuLukaBackground, 50, 0, null);

        for (GameButton button : buttons)
            button.Render(g);
    }

    public void HandleKey(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case 38: if (selectedOption>0) {
                buttons.get(selectedOption).SetButtonState(GameButton.BUTTON_STATES.INACTIVE);
                --selectedOption;
                buttons.get(selectedOption).SetButtonState(GameButton.BUTTON_STATES.ACTIVE);
                repaint();
            }
                break;
            case 40: if (selectedOption<2) {
                buttons.get(selectedOption).SetButtonState(GameButton.BUTTON_STATES.INACTIVE);
                ++selectedOption;
                buttons.get(selectedOption).SetButtonState(GameButton.BUTTON_STATES.ACTIVE);
                repaint();
            }
                break;
            case 10: switch (selectedOption)
                {
                    case 0: GUI.ShowPanel("SelectPlayerPanel");
                        break;
                    case 1: GUI.ShowPanel("SettingsPanel");
                        break;
                    case 2: GUI.ShowPanel("RankingPanel");
                        break;
                }
                break;
        }
    }
}