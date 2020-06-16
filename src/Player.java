public class Player {

    private String name;
    private int level;
    private int exp;
    private int playerMaxCombo;
    private int maxScore;

    private static int[] levelsExp = {300, 600, 1000, 2000};

    public int score;
    public int hit;
    public int miss;
    public int currentCombo;
    public int currentMaxCombo;

    private static Player instance;

    public static Player GetInstance()
    {
       if (instance == null)
           instance = new Player();
       return instance;
    }

    public void InitPlayer(String name, int lvl, int exp, int score, int combo)
    {
        this.name = name;
        level = lvl;
        this.exp = exp;
        maxScore = score;
        playerMaxCombo = combo;
    }

    public void SaveStats()
    {
        if (score>maxScore)
            maxScore = score;

        if (currentMaxCombo>playerMaxCombo)
            playerMaxCombo = currentMaxCombo;

        exp += score;
        //System.out.println(levelsExp.length);
        if (level<levelsExp.length)
            if (exp >= levelsExp[level])
            {
                exp = 0;
                level++;
            }

        //System.out.println(exp);

        DBManager.GetInstance().SaveStats();
    }

    public String GetName() { return name; }
    public int GetLevel() { return level; }
    public int GetExp() { return exp; }
    public int GetMaxScore() { return maxScore; }
    public int GetMaxCombo() { return playerMaxCombo; }

}
