import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class SettingsPanel extends JPanel{

    private ArrayList<String> settings;
    private ArrayList<String> settingsValues;
    private ArrayList<String> backgrounds;
    private ArrayList<String> songs;
    private int backgroundsIndex;
    private int songsIndex;

    private GameObject indicator;
    private ArrayList<GameObject> arrowsLeft;
    private ArrayList<GameObject> arrowsRight;
    private GameButton save;
    private int selectedOption;

    public SettingsPanel()
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

        settings = new ArrayList<>();
        settings.add("Keys Number");
        settings.add("Speed");
        settings.add("Volume");
        settings.add("Background");
        settings.add("Song");

        arrowsLeft = new ArrayList<>();
        for (int i=0; i<settings.size(); i++)
            arrowsLeft.add(new GameObject(AssetsManager.arrowLeft, 300, 25+75*i));

        arrowsRight = new ArrayList<>();
        for (int i=0; i<settings.size(); i++)
            arrowsRight.add(new GameObject(AssetsManager.arrowRight, 700, 25+75*i));

        indicator = new GameObject(AssetsManager.indicator, 350, 25+selectedOption*75);
        save = new GameButton("Save", 370, 25+75*settings.size());
        save.SetButtonState(GameButton.BUTTON_STATES.ACTIVE);
        UpdateSettings();
    }

    public void UpdateSettings()
    {
        backgrounds = DBManager.GetInstance().GetBackgrounds();
        songs = DBManager.GetInstance().GetSongs();
        settingsValues = DBManager.GetInstance().GetSettings();
        backgroundsIndex = backgrounds.indexOf(settingsValues.get(3));
        songsIndex = songs.indexOf(settingsValues.get(4));
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        g.drawImage(AssetsManager.background, 0, 0,null);
        g.drawImage(AssetsManager.MikuLukaBackground, 50, 0, null);

        indicator.Render(g);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Times Roman", Font.BOLD, 26));
        for (int i=0; i<settings.size(); i++)
        {
            g.drawString(settings.get(i), 100, 25+i*75+30);
            g.drawString(settingsValues.get(i), 400, 25+i*75+30);
        }

        for (int i=0; i<settings.size(); i++)
        {
            arrowsRight.get(i).Render(g);
            arrowsLeft.get(i).Render(g);
        }

        save.Render(g);
    }

    public void HandleKey(KeyEvent e) {
        //System.out.println("Background index: "+backgroundsIndex);
        switch (e.getKeyCode()) {
            case 38:
                if (selectedOption > 0) {
                    selectedOption--;
                    indicator.SetPosition(350, 25 + selectedOption * 75);
                    repaint();
                }
                break;

            case 40: if (selectedOption < settings.size()-1){
                    ++selectedOption;
                    indicator.SetPosition(350, 25 + selectedOption * 75);
                    repaint();
                }
                break;

            case 37: switch (selectedOption){
                case 0: if (Integer.parseInt(settingsValues.get(0))>3)
                        {
                            settingsValues.set(0, Integer.toString(Integer.parseInt(settingsValues.get(0))-1));
                            repaint();
                        }
                        break;
                case 1: if (settingsValues.get(1).equals("3"))
                        {
                            settingsValues.set(1, Integer.toString(Integer.parseInt(settingsValues.get(1))-1));
                            repaint();
                        }
                        break;
                case 2: if (Integer.parseInt(settingsValues.get(2))>-5)
                        {
                            settingsValues.set(2, Integer.toString(Integer.parseInt(settingsValues.get(2)) - 1));
                            repaint();
                        }
                        break;
                case 3: if (backgroundsIndex>0)
                        {
                            backgroundsIndex--;
                            settingsValues.set(3, backgrounds.get(backgroundsIndex));
                            repaint();
                        }
                        break;
                case 4: if (songsIndex>0)
                        {
                            songsIndex--;
                            settingsValues.set(4, songs.get(songsIndex));
                            repaint();
                        }
                        break;
            }
            break;

            case 39: switch (selectedOption){
                case 0: if (Integer.parseInt(settingsValues.get(0))<10)
                        {
                            settingsValues.set(0, Integer.toString(Integer.parseInt(settingsValues.get(0))+1));
                            repaint();
                        }
                        break;
                case 1: if (settingsValues.get(1).equals("2"))
                        {
                            settingsValues.set(1, Integer.toString(Integer.parseInt(settingsValues.get(1))+1));
                            repaint();
                        }
                    break;
                case 2: if (Integer.parseInt(settingsValues.get(2))<5)
                        {
                            settingsValues.set(2, Integer.toString(Integer.parseInt(settingsValues.get(2)) + 1));
                            repaint();
                        }
                    break;
                case 3: if (backgroundsIndex<backgrounds.size()-1)
                        {
                            backgroundsIndex++;
                            settingsValues.set(3, backgrounds.get(backgroundsIndex));
                            repaint();
                        }
                    break;
                case 4: if (songsIndex<songs.size()-1)
                        {
                            songsIndex++;
                            settingsValues.set(4, songs.get(songsIndex));
                            repaint();
                        }
                    break;
            }
                break;

            case 27: GUI.ShowPanel("MenuPanel");
                break;

            case 10:
                DBManager.GetInstance().SaveSettings(settingsValues);
                //repaint();
                GUI.ShowPanel("MenuPanel");
                break;
        }
    }
}
