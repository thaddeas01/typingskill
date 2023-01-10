package oit.is.z0553.kaizi.typingskill.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface VocabMapper {

  @Select("SELECT vocab from vocabulary where id = #{id}")
  String selectById(int id);

  @Insert("INSERT INTO ranking (userName,score) VALUES (#{userName},#{score});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertRank(Vocab vocab);

  @Select("SELECT ID, NAME FROM vocabulary")
  ArrayList<Vocab> selectAllVocab();

  @Delete("DELETE FROM vocabulary WHERE ID =#{id}")
  boolean deleteById(int id);
}
