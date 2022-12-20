package oit.is.z0553.kaizi.typingskill.controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;
import oit.is.z0553.kaizi.typingskill.model.VocabMapper;
import oit.is.z0553.kaizi.typingskill.model.Vocab;
import oit.is.z0553.kaizi.typingskill.model.RankingMapper;
import oit.is.z0553.kaizi.typingskill.model.Ranking;

import java.util.ArrayList;

import java.util.Random;

@Controller
public class SampleController {

  @Autowired
  RankingMapper rMapper;

  @Autowired
  private VocabMapper vocabmapper;

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
    String spell = vocabmapper.selectById(rad.nextInt(250));
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
    String username = prin.getName();
    if (prob.equals(answer)) {
      point++;
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

    String spell = vocabmapper.selectById(rad.nextInt(
        250));
    model.addAttribute("spell", spell);
    model.addAttribute("miss", mistake);
    model.addAttribute("score", point);
    return ("single.html");
  }

  @GetMapping("/timeout")
  public String timeout(ModelMap model, @RequestParam int miss, @RequestParam int score,
      Principal prin) {
    Random rad = new Random();
    int mistake = miss;
    int point = score;
    String username = prin.getName();
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
    String spell = vocabmapper.selectById(rad.nextInt(250));
    model.addAttribute("spell", spell);
    model.addAttribute("miss", mistake);
    model.addAttribute("score", score);
    return "single.html";
  }
}
