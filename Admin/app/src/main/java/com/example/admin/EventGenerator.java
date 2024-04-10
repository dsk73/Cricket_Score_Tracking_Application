package com.example.admin;

public class EventGenerator {
    String Category,Innings,TeamA,TeamB,TeamALogo,TeamBLogo;
    int MatchNo;

    public EventGenerator(String Category, String Innings, String TeamA, String TeamB, String TeamALogo, String TeamBLogo, int MatchNo) {
        this.Category = Category;
        this.Innings = Innings;
        this.TeamA = TeamA;
        this.TeamB = TeamB;
        this.TeamALogo = TeamALogo;
        this.TeamBLogo = TeamBLogo;
        this.MatchNo = MatchNo;
    }

    public String getCategory() {
        return Category;
    }

    public String getInnings() {
        return Innings;
    }

    public String getTeamA() {
        return TeamA;
    }

    public String getTeamB() {
        return TeamB;
    }

    public String getTeamALogo() {
        return TeamALogo;
    }

    public String getTeamBLogo() {
        return TeamBLogo;
    }

    public int getMatchNo() {
        return MatchNo;
    }
}
