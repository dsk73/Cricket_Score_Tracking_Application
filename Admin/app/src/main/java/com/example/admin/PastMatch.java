package com.example.admin;

public class PastMatch {
    String Category,Innings,TeamA,TeamB,TeamALogo,TeamBLogo,TeamAScore,TeamBScore;
    int MatchNo;

    public PastMatch(String category, String innings, String teamA, String teamB, String teamALogo, String teamBLogo, String teamAScore, String teamBScore, int matchNo) {
        Category = category;
        Innings = innings;
        TeamA = teamA;
        TeamB = teamB;
        TeamALogo = teamALogo;
        TeamBLogo = teamBLogo;
        TeamAScore = teamAScore;
        TeamBScore = teamBScore;
        MatchNo = matchNo;
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

    public String getTeamAScore() {
        return TeamAScore;
    }

    public String getTeamBScore() {
        return TeamBScore;
    }

    public int getMatchNo() {
        return MatchNo;
    }
}
