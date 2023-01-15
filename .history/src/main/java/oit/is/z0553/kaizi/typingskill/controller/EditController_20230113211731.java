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
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z0553.kaizi.typingskill.model.VocabMapper;
import oit.is.z0553.kaizi.typingskill.model.Vocab;
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

  @GetMapping("/set")
  public String set(ModelMap model) {
    final ArrayList<Vocab> Vocabadd = vocabsync.syncShowVocabList();
    model.addAttribute("Vocabadd", Vocabadd);
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
public String addvoc(@RequestParam String vocab, ModelMap model){
  final String addvoc = this.vocabsync.syncAddVocabs(vocab);
  model.addAttribute("AddVocab", addvoc);

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
