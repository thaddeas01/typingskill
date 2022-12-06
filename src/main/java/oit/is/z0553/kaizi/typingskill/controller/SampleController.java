package oit.is.z0553.kaizi.typingskill.controller;

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

import java.util.Random;

@Controller
public class SampleController {

  @Autowired
  private VocabMapper vocabmapper;

  @GetMapping("/start")
  public String sample(ModelMap model, Principal prin) {
    String loginUser = prin.getName();
    model.addAttribute("user", loginUser);
    return "start.html";
  }

  @GetMapping("/score")
  public String score() {
    return "score.html";
  }

  @GetMapping("/single")
  public String single(ModelMap model) {

    Random rad = new Random();
    int mistake = 0;
    String spell = vocabmapper.selectById(rad.nextInt(250));
    model.addAttribute("spell", spell);
    model.addAttribute("miss", mistake);
    return "single.html";
  }

  @PostMapping("/hantei")
  public String hantei(ModelMap model, @RequestParam String prob, @RequestParam int miss) {
    Random rad = new Random();
    int mistake = miss;
    mistake++;
    String spell = vocabmapper.selectById(rad.nextInt(250));
    model.addAttribute("spell", spell);
    model.addAttribute("miss", mistake);
    return ("single.html");
  }
}
