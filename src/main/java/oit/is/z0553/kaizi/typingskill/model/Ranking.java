package oit.is.z0553.kaizi.typingskill.model;

public class Ranking {
  int id;
  String name;
  String Rank;
  int score;

  public Ranking() {

  }

  public Ranking( String name2, int score2) {
    this.name = name2;
    this.score = score2;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public String getRank() {
    return Rank;
  }

  public void setRank(String name) {
    this.Rank = name;
  }
}
