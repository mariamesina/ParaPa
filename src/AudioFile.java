import javax.sound.sampled.*;
import java.io.File;

public class AudioFile implements LineListener
{
    private File musicFile;
    private AudioInputStream ais;
    private AudioFormat format;
    private DataLine.Info info;
    private Clip clip;
    private FloatControl gainControl;

    public AudioFile(String path)
    {
        try {
            musicFile = new File(path);
            ais = AudioSystem.getAudioInputStream(musicFile);
            format = ais.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.addLineListener(this);
            clip.open(ais);
            gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("Can't load song");
            System.exit(1);
        }
    }

    public void Play(float volume)
    {
        gainControl.setValue(volume);
        clip.setFramePosition(0);
        clip.start();
    }

    public void Pause (){ clip.stop(); }

    public void Resume(){ clip.start(); }

    @Override
    public void update(LineEvent event)
    {
       if ((event.getType() == LineEvent.Type.STOP) && (clip.getMicrosecondLength() == clip.getMicrosecondPosition()))
       {
           Game.SetGameState(Game.GAME_STATES.INACTIVE);
           GUI.ShowPanel("ScorePanel");
       }
    }
}
