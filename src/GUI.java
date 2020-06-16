import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI {

    private static JFrame frame;
    private static JPanel container;
    private static CardLayout cl;

    private static MenuPanel menuPanel;
    private static GamePanel gamePanel;
    private static PausePanel pausePanel;
    private static ScorePanel scorePanel;
    private static SelectPlayerPanel selectPlayerPanel;
    private static SettingsPanel settingsPanel;
    private static RankingPanel rankingPanel;

    public GUI() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame = new JFrame("ParaPa");
                frame.setPreferredSize(new Dimension(Launcher.WIDTH, Launcher.HEIGHT));
                frame.setMaximumSize(new Dimension(Launcher.WIDTH, Launcher.HEIGHT));
                frame.setMinimumSize(new Dimension(Launcher.WIDTH, Launcher.HEIGHT));

                frame.setLocationRelativeTo(null);
                frame.setResizable(false);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.requestFocusInWindow();
                frame.addWindowListener(new WindowHandler());

                cl = new CardLayout();

                container = new JPanel();
                container.setLayout(cl);
                frame.add(container);

                menuPanel = new MenuPanel();
                //gamePanel = new GamePanel();
                //pausePanel = new PausePanel();
                //scorePanel = new ScorePanel();
                //selectPlayerPanel = new SelectPlayerPanel();
                //settingsPanel = new SettingsPanel();

                container.add(menuPanel, "MenuPanel");
                //container.add(gamePanel, "GamePanel");
                //container.add(pausePanel, "PausePanel");
                //container.add(scorePanel, "ScorePanel");
                //container.add(selectPlayerPanel, "SelectPlayerPanel");
                //container.add(settingsPanel, "SettingsPanel");

                cl.show(container, "MenuPanel");

                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    public static void ShowPanel(String _name)
    {
        switch (_name)
        {
            case "MenuPanel":
                cl.show(container, _name);
                container.repaint();
                container.revalidate();
                menuPanel.requestFocusInWindow();
                break;
            case "GamePanel": if (gamePanel == null) {
                    gamePanel = new GamePanel();
                    container.add(gamePanel, "GamePanel");
                }

                cl.show(container, _name);
                container.repaint();
                container.revalidate();
                gamePanel.requestFocusInWindow();
                break;
            case "PausePanel": if (pausePanel == null) {
                    pausePanel = new PausePanel();
                    container.add(pausePanel, "PausePanel");
                }

                cl.show(container, _name);
                container.repaint();
                container.revalidate();
                pausePanel.requestFocusInWindow();
                break;
            case "ScorePanel": if (scorePanel ==null){
                    scorePanel = new ScorePanel();
                    container.add(scorePanel, "ScorePanel");
                }
                cl.show(container, _name);
                container.repaint();
                container.revalidate();
                scorePanel.requestFocusInWindow();
                break;
            case "SelectPlayerPanel":
                if (selectPlayerPanel == null)
                {
                    selectPlayerPanel = new SelectPlayerPanel();
                    container.add(selectPlayerPanel, "SelectPlayerPanel");
                }

                cl.show(container, _name);
                container.repaint();
                container.revalidate();
                selectPlayerPanel.requestFocusInWindow();
                selectPlayerPanel.UpdatePlayers();
                break;
            case "SettingsPanel": if (settingsPanel == null) {
                    settingsPanel = new SettingsPanel();
                    container.add(settingsPanel, "SettingsPanel");
                }
                settingsPanel.UpdateSettings();
                cl.show(container, _name);
                container.repaint();
                container.revalidate();
                settingsPanel.requestFocusInWindow();
                break;

            case "RankingPanel": if (rankingPanel == null){
                    rankingPanel = new RankingPanel();
                    container.add(rankingPanel, "RankingPanel");
                }

                rankingPanel.UpdateRanking();
                cl.show(container, _name);
                container.repaint();
                container.revalidate();
                rankingPanel.requestFocusInWindow();
                break;
        }

        //cl.show(container, _name);
        //container.repaint();
        //container.revalidate();
    }

    public static void ExitGUI()
    {
        EventQueue.invokeLater(()->{frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));});
    }
}

class WindowHandler extends WindowAdapter {
    public void windowClosing(WindowEvent e) {
        Game.SetGameState(Game.GAME_STATES.INACTIVE);
        super.windowClosing(e);
    }
}
