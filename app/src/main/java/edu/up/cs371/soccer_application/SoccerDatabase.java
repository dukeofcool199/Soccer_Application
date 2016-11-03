package edu.up.cs371.soccer_application;

import android.util.Log;

import edu.up.cs371.soccer_application.soccerPlayer.SoccerPlayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Soccer player database -- presently, all dummied up
 * 
 * @author *** put your name here ***
 * @version *** put date of completion here ***
 *
 */
public class SoccerDatabase implements SoccerDB
{
    Hashtable<String,SoccerPlayer> table = new Hashtable<String ,SoccerPlayer>();
    /**
     * add a player
     *
     * @see SoccerDB#addPlayer(String, String, int, String)
     */
    @Override
	public boolean addPlayer(String firstName, String lastName,
			int uniformNumber, String teamName)
    {
        String name = firstName+"##"+lastName;

        if(table.get(name) != null)
        {
            return false;
        }
        else
        {
            table.put(name,new SoccerPlayer(firstName,lastName,uniformNumber,teamName));
            return true;
        }
	}

    /**
     * remove a player
     *
     * @see SoccerDB#removePlayer(String, String)
     */
    @Override
    public boolean removePlayer(String firstName, String lastName)
    {
        String name = firstName+"##"+lastName;

        SoccerPlayer player = (SoccerPlayer)table.get(name);
        if(player != null)
        {
            table.remove(name);
            return true;

        }
        else
        {
            return false;
        }
    }

    /**
     * look up a player
     *
     * @see SoccerDB#getPlayer(String, String)
     */
    @Override
	public SoccerPlayer getPlayer(String firstName, String lastName)
    {
      String name = firstName+"##"+lastName;

            SoccerPlayer player = (SoccerPlayer) table.get(name);
            if(player != null)
            {
                return player;
            }
            else
            {
                return null;
            }
    }

    /**
     * increment a player's goals
     *
     * @see SoccerDB#bumpGoals(String, String)
     */
    @Override
    public boolean bumpGoals(String firstName, String lastName)
    {
        String name = firstName+"##"+lastName;

        SoccerPlayer player = (SoccerPlayer) table.get(name);
        if(player != null)
        {
            player.bumpGoals();
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * increment a player's assists
     *
     * @see SoccerDB#bumpAssists(String, String)
     */
    @Override
    public boolean bumpAssists(String firstName, String lastName) {
        return false;
    }

    /**
     * increment a player's shots
     *
     * @see SoccerDB#bumpShots(String, String)
     */
    @Override
    public boolean bumpShots(String firstName, String lastName) {
        return false;
    }

    /**
     * increment a player's saves
     *
     * @see SoccerDB#bumpSaves(String, String)
     */
    @Override
    public boolean bumpSaves(String firstName, String lastName) {
        return false;
    }

    /**
     * increment a player's fouls
     *
     * @see SoccerDB#bumpFouls(String, String)
     */
    @Override
    public boolean bumpFouls(String firstName, String lastName) {
        return false;
    }

    /**
     * increment a player's yellow cards
     *
     * @see SoccerDB#bumpYellowCards(String, String)
     */
    @Override
    public boolean bumpYellowCards(String firstName, String lastName) {
        return false;
    }

    /**
     * increment a player's red cards
     *
     * @see SoccerDB#bumpRedCards(String, String)
     */
    @Override
    public boolean bumpRedCards(String firstName, String lastName) {
        return false;
    }

    /**
     * tells the number of players on a given team
     *
     * @see SoccerDB#numPlayers(String)
     */
    @Override
    // report number of players on a given team (or all players, if null)
	public int numPlayers(String teamName)
    {
        int counter = 0;
        Iterator it = table.entrySet().iterator();
        String word;
        SoccerPlayer player;
        if(teamName == null)
        {
            return table.size();
        }
        else
        {
            for(int i = 0; i < table.size(); i++)
            {
                Map.Entry pair = (Map.Entry)it.next();
                player = (SoccerPlayer) table.get(pair.getKey());
                if(player.getTeamName().equals(teamName))
                {
                    counter++;
                }
            }
            return counter;
        }
    }

    /**
     * gives the nth player on a the given team
     *
     * @see SoccerDB#playerNum(int, String)
     */
	// get the nTH player
	@Override
    public SoccerPlayer playerNum(int idx, String teamName)
    {
        Set set = table.keySet();
        String[] playerNames = table.keySet().toArray(new String[table.size()]);
        int counter = 0;
        Iterator it = table.entrySet().iterator();
        SoccerPlayer player;
//        if(teamName == null)
//        {
//            while(it.hasNext())
//            {
//                Map.Entry pair = (Map.Entry)it.next();
//                player = (SoccerPlayer) table.get(pair.getKey());
//                if(counter == idx)
//                {
//                    return player;
//                }
//                counter++;
//            }
//        }
//        else
//        {
            for(int i = 0; i < set.size(); i++)
            {
                player = (SoccerPlayer) table.get(playerNames[i]);
                if(teamName == null || player.getTeamName().equals(teamName))
                {
                    if(counter == idx)
                    {
                        return player;
                    }
                    counter++;

                }
            }
       // }
        return null;
    }

    /**
     * reads database data from a file
     *
     * @see SoccerDB#readData(java.io.File)
     */
	// read data from file
    @Override
	public boolean readData(File file) {
        return file.exists();
	}

    /**
     * write database data to a file
     *
     * @see SoccerDB#writeData(java.io.File)
     */
	// write data to file
    @Override
	public boolean writeData(File file)
    {
        SoccerPlayer player;
        String[] playerNames = table.keySet().toArray(new String[table.size()]);
        try
        {
            PrintWriter writer = new PrintWriter(file);
            for(int i = 0; i < table.size(); i++) {
                player = table.get(playerNames[i]);
                writer.println(logString(player.getFirstName()));
                writer.println(logString(player.getLastName()));
                writer.println(logString(player.getTeamName()));
                writer.println(logString("" + player.getUniform()));
                writer.println(logString(""+player.getGoals()));
                writer.println(logString("" + player.getShots()));
                writer.println(logString("" + player.getAssists()));
                writer.println(logString("" + player.getSaves()));
                writer.println(logString("" + player.getFouls()));
                writer.println(logString("" + player.getYellowCards()));
                writer.println(logString("" + player.getRedCards()));
                writer.close();
            }
            return true;
        }
        catch (FileNotFoundException e)
        {
            return false;
        }
	}


    /**
     * helper method that logcat-logs a string, and then returns the string.
     * @param s the string to log
     * @return the string s, unchanged
     */
    private String logString(String s) {
        Log.i("write string", s);
        return s;
    }

    /**
     * returns the list of team names in the database
     *
     * @see edu.up.cs371.soccer_application.SoccerDB#getTeams()
     */
	// return list of teams
    @Override
	public HashSet<String> getTeams() {
        return new HashSet<String>();
	}

}
