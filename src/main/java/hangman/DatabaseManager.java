package hangman;

import org.postgresql.core.ResultHandler;

import java.sql.*;

// Use JDBC to connect to your database and run queries

public class DatabaseManager {
    private Connection c = null;
    public DatabaseManager(){
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "4421283517");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void show_scoreboard(){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM userinfo ORDER BY n_win DESC;";
            stmt = c.prepareStatement(query);
            rs = stmt.executeQuery();
            System.out.println("username, number of wins");
            int i = 1;
            while (rs.next()) {
                String username = rs.getString("username");
                int n_win = rs.getInt("n_win");
                System.out.println(i + " - " + username + " " + n_win);
                i++;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }
    public void show_game_records(String username){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM gameinfo where username = ?;";
            stmt = c.prepareStatement(query);
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            System.out.println("word - wrong guesses - time - win");
            int i = 1;
            while (rs.next()) {
                int game_id = rs.getInt("game_id");
                String word = rs.getString("word");
                String wrong_guesses = rs.getString("wrong_guesses");
                int time = rs.getInt("time");
                int win = rs.getInt("win");

                System.out.println(i + " - " + word + " - " + wrong_guesses + " - " + time + "s - " + win);
                i++;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }


    public void register(String username, String password, String name){
        try {
            Statement stmt = c.createStatement();
            stmt.executeUpdate("insert into UserInfo (name, username, password, n_win) values ('" + name + "','" + username + "','" + password + "',0);");
            stmt.close();
            c.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void add_win(String username){
        int n_win = 0;
        PreparedStatement stmt = null;
        try {
            String sql = "UPDATE userinfo SET n_win = n_win + 1 WHERE username = ?";
            stmt = c.prepareStatement(sql);
            stmt.setString(1, username);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                c.commit();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    public String login(String username, String password) {
        String userFullName = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM UserInfo WHERE username = ?";
            stmt = c.prepareStatement(query);
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                if (password.equals(storedPassword)) {
                    userFullName = rs.getString("name");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return userFullName;
    }
    public void record_game(String username, int game_id, String word, String wrong_guesses, int time, int win){
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            stmt.executeUpdate("insert into GameInfo (game_id, username, word, wrong_guesses, time, win) " +
                    "values (" + game_id + ",'" + username + "','" + word + "','" + wrong_guesses + "'," + time + "," + win + ");");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (c != null) c.commit();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void close(){
        try {
            c.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        DatabaseManager d = new DatabaseManager();
        //d.record_game("mobin", 1, "word", "abc", 100, 1);
        System.out.println(d.login("mamad", "1234"));
        d.close();
    }
}