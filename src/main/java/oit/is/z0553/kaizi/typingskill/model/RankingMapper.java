package oit.is.z0553.kaizi.typingskill.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RankingMapper {

  @Select("SELECT ID, NAME, SCORE FROM RANKING order by SCORE desc limit 10")
  ArrayList<Ranking> selectAllRanking();

  @Select("SELECT vocab from vocabulary where id = #{id}")
  String selectById(int id);

  @Insert("INSERT INTO ranking (name,score) VALUES (#{name},#{score});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertRank(Ranking addrank);

  @Select("SELECT count(id) from ranking where name = #{name}")
  Integer getTimeplayed(String name);

  @Select("SELECT max(score) from ranking where name = #{name}")
  Integer getHighscore(String name);
}
