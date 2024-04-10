package com.coepzest.zest23;

public class CurrentEventLiveScoreModel {
    String Category,MatchNo,Innings,TeamAScore,TeamBScore,TeamA,TeamB,TeamALogo,TeamBLogo;

    public CurrentEventLiveScoreModel(String Category,String MatchNo,String Innings,String TeamAScore,String TeamBScore,String TeamA,String TeamB,String TeamALogo,String TeamBLogo) {
        this.Category=Category;
        this.MatchNo=MatchNo;
        this.Innings=Innings;
        this.TeamAScore=TeamAScore;
        this.TeamBScore=TeamBScore;
        this.TeamA=TeamA;
        this.TeamB=TeamB;
        this.TeamALogo=TeamALogo;
        this.TeamBLogo=TeamBLogo;

    }

}
