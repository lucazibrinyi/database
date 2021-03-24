package com.codecool.database;

import cccr.CCCRTestExecutionListener;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({CCCRTestExecutionListener.class})
class RadioChartsTest {

    private static final String DB_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    private RadioCharts radioCharts;

    @BeforeEach
    void init() {
        radioCharts = new RadioCharts(DB_URL, DB_USER, DB_PASSWORD);
        initTables();
    }

    @Test
    void getMostPlayedSong_emptyDatabase_emptyString() {
        assertEquals("", radioCharts.getMostPlayedSong());
    }

    @Test
    void getMostActiveArtist_emptyDatabase_emptyString() {
        assertEquals("", radioCharts.getMostActiveArtist());
    }

    @Test
    void getMostPlayedSong_everySongOnce_firstSong() {
        createDummyData();
        assertEquals("Let Me Down Easy", radioCharts.getMostPlayedSong());
    }

    @Test
    void getMostActiveArtist_everyArtistOnce_firstArtist() {
        createDummyData();
        assertEquals("Chris Isaak", radioCharts.getMostActiveArtist());
    }

    @Test
    void getMostActiveArtist_oneSongTwice_firstArtist() {
        createDummyData();
        addExistingSong();
        assertEquals("Chris Isaak", radioCharts.getMostActiveArtist());
    }

    @Test
    void getMostPlayedSong_oneSongTwiceInDb_twicePlayedSong() {
        createDummyData();
        addExistingSong();
        assertEquals("Easy", radioCharts.getMostPlayedSong());
    }

    @Test
    void getMostPlayedSong_oneSongTwicePlayed_twicePlayedSong() {
        createDummyData();
        addNewSongNewArtistMostPlayed();
        assertEquals("You", radioCharts.getMostPlayedSong());
    }

    @Test
    void getMostActiveArtist_oneArtistTwice_artistWithTwoSongs() {
        createDummyData();
        addNewSongExistingArtist();
        assertEquals("Son Lux", radioCharts.getMostActiveArtist());
    }

    @AfterEach
    void destruct() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "DROP TABLE IF EXISTS music_broadcast";
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void initTables() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "CREATE TABLE IF NOT EXISTS music_broadcast (" +
                    "artist VARCHAR(255), " +
                    "song VARCHAR(255), " +
                    "times_aired INT" +
                    ");";
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void createDummyData() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO music_broadcast VALUES ('Chris Isaak', 'Let Me Down Easy', 1)";
            Statement statement = connection.createStatement();
            statement.execute(query);

            query = "INSERT INTO music_broadcast VALUES ('Son Lux', 'Easy', 1)";
            statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    void addExistingSong() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO music_broadcast VALUES ('Son Lux', 'Easy', 1)";
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void addNewSongExistingArtist() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO music_broadcast VALUES ('Son Lux', 'Ransom', 1)";
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void addNewSongNewArtistMostPlayed() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO music_broadcast VALUES ('Radiohead', 'You', 2)";
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
