package com.codecool.database;


import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class RadioCharts {
    private final String DB_URL;
    private final String DB_USER;
    private final String DB_PASSWORD;

    public RadioCharts(String DB_URL, String DB_USER, String DB_PASSWORD) {
        this.DB_URL = DB_URL;
        this.DB_USER = DB_USER;
        this.DB_PASSWORD = DB_PASSWORD;
    }

    public String getMostPlayedSong() {
        String hit = "";
        List<Song> songs = new ArrayList<>();
        int mostTimesAired = 0;
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String SQL = "SELECT song, SUM(times_aired) as times_aired" +
                    " FROM music_broadcast" +
                    " GROUP BY song";
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(SQL);
            while (results.next()) {
                String title = results.getString("song");
                Integer timesAired = results.getInt("times_aired");
                songs.add(new Song(title, timesAired));
            }

            for (Song song : songs) {
                if (song.getTimesAired() > mostTimesAired) {
                    mostTimesAired = song.getTimesAired();
                }
            }

            for (Song song : songs) {
                if (song.getTimesAired() == mostTimesAired && hit.equals("")) {
                    hit = song.getTitle();
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return hit;
    }

    public String getMostActiveArtist() {
        String mostActiveArtist = "";

        Map<Artist, Integer> artistIntegerMap = new HashMap<>();
        List<Artist> songList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String SQL = "SELECT artist, COUNT (DISTINCT song) as number" +
                    " FROM music_broadcast" +
                    " GROUP BY artist";
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(SQL);
            while (results.next()) {
                String name = results.getString("artist");
                Integer number = results.getInt("number");
                artistIntegerMap.put(new Artist(name), number);
            }
            songList = artistIntegerMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .map(Map.Entry::getKey).collect(Collectors.toList());

            if (songList.size() != 0) {
                mostActiveArtist = songList.get(0).getName();
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return mostActiveArtist;
    }
}
