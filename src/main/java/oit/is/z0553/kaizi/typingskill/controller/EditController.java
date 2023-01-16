package oit.is.z0553.kaizi.typingskill.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import oit.is.z0553.kaizi.typingskill.model.VocabMapper;
import oit.is.z0553.kaizi.typingskill.model.Vocab;
import oit.is.z0553.kaizi.typingskill.model.Room;
import oit.is.z0553.kaizi.typingskill.service.Vocabsync;

/**
 * /sample5へのリクエストを扱うクラス authenticateの設定をしていれば， /sample5へのアクセスはすべて認証が必要になる
 * 他のクラスと同じRequestMappingも書ける．ただし，特定のメソッドへのGETリクエストのURLは一意じゃないとだめ．
 */
@Controller
@RequestMapping("/edit")
public class EditController {

  @Autowired
  VocabMapper fMapper;

  @Autowired
  Vocabsync vocabsync;

  @Autowired
  private Room room;

  @GetMapping("/set")
  public String set(ModelMap model, Principal prin) {
    final ArrayList<Vocab> Vocabadd = vocabsync.syncShowVocabList();
    model.addAttribute("Vocabadd", Vocabadd);
    String loginUser = prin.getName();
    this.room.addUser(loginUser);
    model.addAttribute("room", this.room);
    return "multi.html";
  }

  @GetMapping("/delete")
  @Transactional
  public String delete(@RequestParam Integer id, ModelMap model) {
    final Vocab VocabDelete = this.vocabsync.syncDeleteVocabs(id);
    model.addAttribute("VocabDelete", VocabDelete);

    final ArrayList<Vocab> Vocabadd = vocabsync.syncShowVocabList();
    model.addAttribute("VocabAdd", Vocabadd);

    return "multi.html";

  }

  @PostMapping("/addvoc")
  @Transactional
  public String addvoc(@RequestParam String vocab, ModelMap model) {
    Vocab plus1 = new Vocab();
    plus1.setVocab(vocab);
    final Vocab addvoc = this.vocabsync.syncAddVocabs(plus1);
    model.addAttribute("AddVocab", addvoc);

    final ArrayList<Vocab> Vocabadd = vocabsync.syncShowVocabList();
    model.addAttribute("VocabAdd", Vocabadd);
    return "multi.html";
  }

  @PostMapping("/change1")
  @Transactional
  public String change1(@RequestParam Integer id, @RequestParam String into, ModelMap model) {
    Vocab before = fMapper.selectById(id);
    model.addAttribute("Before", before);
    final Vocab changevoc = this.vocabsync.syncChangeVocab(id, into);
    model.addAttribute("ChangeVocab", changevoc);

    final ArrayList<Vocab> Vocabadd = vocabsync.syncShowVocabList();
    model.addAttribute("VocabAdd", Vocabadd);
    return "multi.html";
  }

  @GetMapping("/sync")
  public SseEmitter sync() {
    final SseEmitter sseEmitter = new SseEmitter();
    this.vocabsync.asyncShowVocabList(sseEmitter);
    return sseEmitter;
  }

}
