package com.codecool.database;

import java.util.Objects;

public class Song {
    private final String title;
    private Integer timesAired;

    public Song(String title, Integer timesAired) {
        this.title = title;
        this.timesAired = timesAired;
    }

    public void addAirTime(Integer airTime) {
        this.timesAired += airTime;
    }

    public String getTitle() {
        return title;
    }

    public Integer getTimesAired() {
        return timesAired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return title.equals(song.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
