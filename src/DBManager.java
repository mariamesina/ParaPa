import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class DBManager {

    private static DBManager instance;

    private DBManager() {}

    public static DBManager GetInstance()
    {
        try {
            if (instance == null)
            {
                instance = new DBManager();
                Class.forName("org.sqlite.JDBC");
            }
        } catch (Exception e ) {
            e.printStackTrace();
            System.exit(1);
        }

        return instance;
    }

    public ArrayList<String> GetPlayers()
    {
        String operation = "SELECT Name FROM Players;";
        ArrayList<String> result = new ArrayList<>();
        try{
            Connection c = DriverManager.getConnection("jdbc:sqlite:data/Data.db");
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(operation);
            while (rs.next()){
                result.add(rs.getString(1));
            }
            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return result;
    }

    public ArrayList<String> GetLevels()
    {
        String operation = "SELECT Level FROM Players;";
        ArrayList<String> result = new ArrayList<>();
        try{
            Connection c = DriverManager.getConnection("jdbc:sqlite:data/Data.db");
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(operation);
            while (rs.next()){
                result.add(rs.getString(1));
            }
            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return result;
    }

    public void SavePlayer(String name)
    {
        String operation = "INSERT INTO Players (Name, Level, Experience, MaxScore, MaxCombo)"+
                "VALUES (\""+name+"\", 1, 0, 0, 0)";
        try{
            Connection c = DriverManager.getConnection("jdbc:sqlite:data/Data.db");
            c.setAutoCommit(false);
            Statement stmt = c.createStatement();
            stmt.executeUpdate(operation);
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> GetBackgrounds()
    {
        String operation = "SELECT Path FROM Backgrounds;";
        ArrayList<String> result = new ArrayList<>();
        try{
            Connection c = DriverManager.getConnection("jdbc:sqlite:data/Data.db");
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(operation);
            while (rs.next()){
                result.add(rs.getString(1));
            }
            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return result;
    }

    public void LoadPlayer(String name)
    {
        String operation = "SELECT Level, Experience, MaxScore, MaxCombo " +
                "FROM Players WHERE Name = \"" + name +"\"";
        try{
            Connection c = DriverManager.getConnection("jdbc:sqlite:data/Data.db");
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(operation);

            int level = rs.getInt(1);
            int exp = rs.getInt(2);
            int score = rs.getInt(3);
            int combo = rs.getInt(4);

            Player.GetInstance().InitPlayer(name, level, exp, score, combo);

            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> GetSongs()
    {
        String operation = "SELECT Path FROM Songs;";
        ArrayList<String> result = new ArrayList<>();
        try{
            Connection c = DriverManager.getConnection("jdbc:sqlite:data/Data.db");
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(operation);
            while (rs.next()){
                result.add(rs.getString(1));
                //System.out.println(result.get(result.size()-1));
            }
            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return result;
    }

    public ArrayList<String> GetSettings()
    {
        String operation = "SELECT * FROM Settings;";
        ArrayList<String> result = new ArrayList<>();
        try{
            Connection c = DriverManager.getConnection("jdbc:sqlite:data/Data.db");
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(operation);

            result.add(Integer.toString(rs.getInt(1)));
            result.add(Integer.toString(rs.getInt(2)));
            result.add(Integer.toString(rs.getInt(3)));
            result.add(rs.getString(4));
            result.add(rs.getString(5));

            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return result;
    }

    public void SaveSettings(ArrayList<String> settingsValues)
    {
        try{
            Connection c = DriverManager.getConnection("jdbc:sqlite:data/Data.db");
            Statement stmt = c.createStatement();

            String operation = "UPDATE Settings SET KeysNumber = " + Integer.parseInt(settingsValues.get(0))+";";
            stmt.executeUpdate(operation);

            operation = "UPDATE Settings SET Speed = " + Integer.parseInt(settingsValues.get(1))+";";
            stmt.executeUpdate(operation);

            operation = "UPDATE Settings SET Volume = " + Integer.parseInt(settingsValues.get(2))+";";
            stmt.executeUpdate(operation);

            operation = "UPDATE Settings SET Background = \"" + settingsValues.get(3) +"\";";
            stmt.executeUpdate(operation);

            operation = "UPDATE Settings SET Song = \"" + settingsValues.get(4) +"\";";
            stmt.executeUpdate(operation);

            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        Settings.GetInstance().UpdateSettings();
    }

    public void SaveStats()
    {
        try{
            Connection c = DriverManager.getConnection("jdbc:sqlite:data/Data.db");
            Statement stmt = c.createStatement();

            String operation = "UPDATE Players SET Level = " + Player.GetInstance().GetLevel() + " WHERE Name = \""+Player.GetInstance().GetName() + "\";";
            stmt.executeUpdate(operation);

            operation = "UPDATE Players SET Experience = " + Player.GetInstance().GetExp() + " WHERE Name = \""+Player.GetInstance().GetName() + "\";";
            stmt.executeUpdate(operation);

            operation = "UPDATE Players SET MaxScore = " + Player.GetInstance().GetMaxScore() + " WHERE Name = \""+Player.GetInstance().GetName() + "\";";
            stmt.executeUpdate(operation);

            operation = "UPDATE Players SET MaxCombo = " + Player.GetInstance().GetMaxCombo() + " WHERE Name = \""+Player.GetInstance().GetName() + "\";";
            stmt.executeUpdate(operation);

            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public Map<String, Integer> GetRankings()
    {
        Map<String, Integer> result = new LinkedHashMap<>();
        String operation = "SELECT Name, MaxScore FROM Players ORDER BY MaxScore DESC;";
        try{
            Connection c = DriverManager.getConnection("jdbc:sqlite:data/Data.db");
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(operation);
            while ((rs.next()) && (result.size()<5)){
                result.put(rs.getString(1), rs.getInt(2));
                //System.out.println(result);
            }
            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return result;
    }
}
