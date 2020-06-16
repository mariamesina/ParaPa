import java.util.ArrayList;

public class Settings {

    private int keysNumber;
    private int speed;
    private int volume;
    private String backgroundPath;
    private String songPath;

    private static Settings instance;

    private Settings()
    {
        ArrayList<String> settings = DBManager.GetInstance().GetSettings();
        keysNumber = Integer.parseInt(settings.get(0));
        speed = Integer.parseInt(settings.get(1));
        volume = Integer.parseInt(settings.get(2));
        backgroundPath = settings.get(3);
        songPath = settings.get(4);
        //System.out.println(backgroundPath);
        //System.out.println(songPath);
    }

    public void UpdateSettings()
    {
        ArrayList<String> settings = DBManager.GetInstance().GetSettings();
        keysNumber = Integer.parseInt(settings.get(0));
        speed = Integer.parseInt(settings.get(1));
        volume = Integer.parseInt(settings.get(2));
        backgroundPath = settings.get(3);
        songPath = settings.get(4);
        AssetsManager.SetBackground(backgroundPath);
        AssetsManager.SetSong(songPath);
    }

    public static Settings GetInstance()
    {
        if (instance == null)
            instance = new Settings();
        return instance;
    }

    public int GetKeysNumber() { return keysNumber; }
    public int GetSpeed() { return speed; }
    public int GetVolume() { return volume; }
    public String GetBackgroundPath() { return backgroundPath; }
    public String GetSongPath() {return songPath; }
}
