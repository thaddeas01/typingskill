package oit.is.z0553.kaizi.typingskill.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface VocabMapper {

  @Select("SELECT vocab from vocabulary where id = #{id}")
  String selectById(int id);

}
