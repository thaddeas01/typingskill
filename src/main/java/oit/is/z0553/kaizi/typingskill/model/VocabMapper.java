package oit.is.z0553.kaizi.typingskill.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface VocabMapper {

  @Select("SELECT ID, vocab from vocabulary where id = #{id}")
  Vocab selectById(int id);

  @Select("SELECT count(ID) from vocabulary")
  Integer selectMaxId();

  @Select("select vocab from (select vocab, ROW_NUMBER() over () as rn from vocabulary) as tbl where tbl.rn = #{id}")
  String selectRadId(int id);

  @Insert("INSERT INTO ranking (userName,score) VALUES (#{userName},#{score});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertRank(Vocab vocab);

  @Insert("INSERT INTO vocabulary (vocab) VALUES(#{Vocab})")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void addVocabs(Vocab vocab);

  @Select("SELECT ID, vocab FROM vocabulary")
  ArrayList<Vocab> selectAllVocab();

  @Delete("DELETE FROM vocabulary WHERE ID =#{id}")
  boolean deleteById(int id);
}
