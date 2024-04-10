package com.example.admin;

public class LeaderBoard {
    String Team;
    int Position,Points;

    public String getTeam() {
        return Team;
    }

    public void setTeam(String Team) {
        this.Team = Team;
    }

    public int getPosition() {
        return Position;
    }

    public void setPosition(int Position) {
        this.Position = Position;
    }

    public int getPoints() {
        return Points;
    }

    public void setPoints(int Points) {
        this.Points = Points;
    }

    public LeaderBoard(String Team, int Position, int Points) {
        this.Team = Team;
        this.Position = Position;
        this.Points = Points;
    }
}
