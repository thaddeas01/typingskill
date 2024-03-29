package oit.is.z0553.kaizi.typingskill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;
import oit.is.z0553.kaizi.typingskill.model.VocabMapper;
import oit.is.z0553.kaizi.typingskill.model.Vocab;
import oit.is.z0553.kaizi.typingskill.model.RankingMapper;
import oit.is.z0553.kaizi.typingskill.model.Ranking;
import oit.is.z0553.kaizi.typingskill.service.Vocabsync;

import java.util.ArrayList;

import java.util.Random;

@Controller
public class SampleController {

  @Autowired
  RankingMapper rMapper;

  @Autowired
  private VocabMapper vocabmapper;

  @Autowired
  Vocabsync vocabsync;

  @GetMapping("/start")
  public String sample(ModelMap model, Principal prin) {
    String loginUser = prin.getName();
    model.addAttribute("user", loginUser);
    return "start.html";
  }

  @GetMapping("/single")
  public String single(ModelMap model) {

    Random rad = new Random();
    int mistake = 0;
    int point = 0;
    int maxdata = vocabmapper.selectMaxId();
    String spell = vocabmapper.selectRadId(rad.nextInt(maxdata));
    model.addAttribute("spell", spell);
    model.addAttribute("miss", mistake);
    model.addAttribute("score", point);
    return "single.html";
  }

  @PostMapping("/hantei")
  public String hantei(ModelMap model, @RequestParam String prob, @RequestParam int miss, @RequestParam int score,
      @RequestParam String answer, Principal prin) {
    Random rad = new Random();
    int mistake = miss;
    int point = score;
    int maxdata = vocabmapper.selectMaxId();
    String username = prin.getName();
    String x = "";
    String spell = vocabmapper.selectRadId(rad.nextInt(
        maxdata));
    if (prob.equals(answer)) {
      point++;
      if(score>15){
        for(int j=0;j<5;j++){
          for(int i=0; i<spell.length();i++){
            if(rad.nextBoolean()){
              char ch = spell.charAt(i);
              if (Character.isUpperCase(ch))
                x += Character.toLowerCase(ch);
              else
                x += Character.toUpperCase(ch);
            }
          }
        }
      model.addAttribute("spell", x);
      model.addAttribute("miss", mistake);
      model.addAttribute("score", point);
      return "single.html";
      }
        else if(score>10){
          for(int j=0;j<3;j++){
            for(int i=0; i<spell.length();i++){
              if(rad.nextBoolean()){
                char ch = spell.charAt(i);
                if (Character.isUpperCase(ch))
                  x += Character.toLowerCase(ch);
                else
                  x += Character.toUpperCase(ch);
              }
            }
        }
        model.addAttribute("spell", x);
        model.addAttribute("miss", mistake);
        model.addAttribute("score", point);
        return "single.html";
      }
      else if(score > 5){
        for(int j=0;j<1;j++){
          for(int i=0; i<spell.length();i++){
            if(rad.nextBoolean()){
              char ch = spell.charAt(i);
              if (Character.isUpperCase(ch))
                x += Character.toLowerCase(ch);
              else
                x += Character.toUpperCase(ch);
            }
          }
      }
      model.addAttribute("spell", x);
      model.addAttribute("miss", mistake);
      model.addAttribute("score", point);
      return "single.html";
    }
    } else {
      mistake++;
      if (mistake >= 3) {
        Ranking addrank = new Ranking();
        addrank.setName(username);
        addrank.setScore(point);
        model.addAttribute("user", username);
        model.addAttribute("score", point);
        try {
          rMapper.insertRank(addrank);
        } catch (RuntimeException e) {
          System.out.println(e.getMessage());
        }
        ArrayList<Ranking> rank = rMapper.selectAllRanking();
        model.addAttribute("rank", rank);
        return "score.html";
      }
    }
    model.addAttribute("spell", spell);
    model.addAttribute("miss", mistake);
    model.addAttribute("score", point);
    return "single.html";
  }

  @GetMapping("/timeout")
  public String timeout(ModelMap model, @RequestParam int miss, @RequestParam int score,
      Principal prin) {
    Random rad = new Random();
    int mistake = miss;
    int point = score;
    String username = prin.getName();
    String x = "";
    int maxdata = vocabmapper.selectMaxId();
    String spell = vocabmapper.selectRadId(rad.nextInt(maxdata));
    mistake++;
    if (mistake >= 3) {
      Ranking addrank = new Ranking();
      addrank.setName(username);
      addrank.setScore(point);
      model.addAttribute("user", username);
      model.addAttribute("score", point);
      try {
        rMapper.insertRank(addrank);
      } catch (RuntimeException e) {
        System.out.println(e.getMessage());
      }
      ArrayList<Ranking> rank = rMapper.selectAllRanking();
      model.addAttribute("rank", rank);
      return "score.html";
    }
    if(score>15){
      for(int j=0;j<5;j++){
        for(int i=0; i<spell.length();i++){
          if(rad.nextBoolean()){
            char ch = spell.charAt(i);
            if (Character.isUpperCase(ch))
              x += Character.toLowerCase(ch);
            else
              x += Character.toUpperCase(ch);
          }
        }
      }
    model.addAttribute("spell", x);
    model.addAttribute("miss", mistake);
    model.addAttribute("score", point);
    return "single.html";
    }
      else if(score>10){
        for(int j=0;j<3;j++){
          for(int i=0; i<spell.length();i++){
            if(rad.nextBoolean()){
              char ch = spell.charAt(i);
              if (Character.isUpperCase(ch))
                x += Character.toLowerCase(ch);
              else
                x += Character.toUpperCase(ch);
            }
          }
      }
      model.addAttribute("spell", x);
      model.addAttribute("miss", mistake);
      model.addAttribute("score", point);
      return "single.html";
    }
    else if(score > 5){
      for(int j=0;j<1;j++){
        for(int i=0; i<spell.length();i++){
          if(rad.nextBoolean()){
            char ch = spell.charAt(i);
            if (Character.isUpperCase(ch))
              x += Character.toLowerCase(ch);
            else
              x += Character.toUpperCase(ch);
          }
        }
    }
    model.addAttribute("spell", x);
    model.addAttribute("miss", mistake);
    model.addAttribute("score", point);
    return "single.html";
  }
  model.addAttribute("spell", spell);
  model.addAttribute("miss", mistake);
  model.addAttribute("score", point);
  return "single.html";
  }

}
