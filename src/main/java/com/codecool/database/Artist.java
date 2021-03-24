package com.codecool.database;

import java.util.*;

public class Artist {
    private final String name;
    private final Set<String> songTitles = new HashSet<>();


    public Artist(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getAmountOfSongs() {
        return songTitles.size();
    }

    public void addSongTitle(String songTitle) {
        songTitles.add(songTitle);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return name.equals(artist.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
