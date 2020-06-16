public class Launcher {

    public static int WIDTH;
    public static int HEIGHT;
    public String title;

    private Launcher(String _title, int _width, int _height)
    {
        title = _title;
        WIDTH = _width;
        HEIGHT = _height;
        GUI gui = new GUI();
    }

    public static void main(String[] args)
    {

        Launcher launcher = new Launcher("ParaPa", 960, 540);
    }
}
