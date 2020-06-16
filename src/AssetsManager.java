import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class AssetsManager {
    public static BufferedImage inactiveButton = LoadImage("Button_Inactive.png");
    public static BufferedImage activeButton = LoadImage("Button_Active.png");
    public static BufferedImage background = LoadImage(Settings.GetInstance().GetBackgroundPath());
    public static BufferedImage MikuLukaBackground = LoadImage("Miku_Luka2.png");

    public static BufferedImage leftBlue = LoadImage("Left_Blue.png");
    public static BufferedImage upBlue = LoadImage("Up_Blue.png");
    public static BufferedImage rightBlue = LoadImage("Right_Blue.png");
    public static BufferedImage downBlue = LoadImage("Down_Blue.png");

    public static BufferedImage leftGreen = LoadImage("Left_Green.png");
    public static BufferedImage upGreen = LoadImage("Up_Green.png");
    public static BufferedImage rightGreen = LoadImage("Right_Green.png");
    public static BufferedImage downGreen = LoadImage("Down_Green.png");

    public static BufferedImage leftRed = LoadImage("Left_Red.png");
    public static BufferedImage upRed = LoadImage("Up_Red.png");
    public static BufferedImage rightRed = LoadImage("Right_Red.png");
    public static BufferedImage downRed = LoadImage("Down_Red.png");


    public static BufferedImage arrowUp = LoadImage("Arrow_Up.png");
    public static BufferedImage arrowDown = LoadImage("Arrow_Down.png");
    public static BufferedImage arrowLeft = LoadImage("Arrow_Left.png");
    public static BufferedImage arrowRight = LoadImage("Arrow_Right.png");

    public static BufferedImage indicator = LoadImage("Blue_Indicator.png");
    public static BufferedImage textSeparator = LoadImage("Text_Divider.png");

    public static AudioFile song = LoadAudioFile("res/"+Settings.GetInstance().GetSongPath());

    public static BufferedImage LoadImage(String path)
    {
        try
        {
            return ImageIO.read(AssetsManager.class.getResource(path));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("Can't load image");
            System.exit(1);
        }
        return null;
    }

    public static AudioFile LoadAudioFile(String path)
    {
        return new AudioFile(path);
    }

    public static void SetBackground(String path)
    {
        background = LoadImage(path);
    }
    public static void SetSong(String path) { song = LoadAudioFile("res/"+path); }
}
