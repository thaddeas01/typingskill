package oit.is.z0553.kaizi.typingskill.service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z0553.kaizi.typingskill.model.VocabMapper;
import oit.is.z0553.kaizi.typingskill.model.Vocab;


@Service
public class Vocabsync {
  boolean dbUpdated = false;

  private final Logger logger = LoggerFactory.getLogger(Vocabsync.class);

  @Autowired
  VocabMapper vMapper;

  @Transactional
  public Vocab syncDeleteVocabs(int id) {
    Vocab vocab = vMapper.selectById(id);

    // 削除
    vMapper.deleteById(id);

    // 非同期でDB更新したことを共有する際に利用する
    this.dbUpdated = true;

    return vocab;
  }
  
  @Transactional
  public String syncAddVocabs(String vocab){
    vMapper.addVocabs(vocab);

    this.dbUpdated = true;

    return vocab;
  }

  public ArrayList<Vocab> syncShowVocabList() {
    return vMapper.selectAllVocab();
  }


  @Async
  public void asyncShowVocabList(SseEmitter emitter) {
    dbUpdated = true;
    try {
      while (true) {// 無限ループ
        // DBが更新されていなければ0.5s休み
        if (false == dbUpdated) {
          TimeUnit.MILLISECONDS.sleep(500);
          continue;
        }
        // DBが更新されていれば更新後のフルーツリストを取得してsendし，1s休み，dbUpdatedをfalseにする
        ArrayList<Vocab> vocablist = this.syncShowVocabList();
        emitter.send(vocablist);
        TimeUnit.MILLISECONDS.sleep(1000);
        dbUpdated = false;
      }
    } catch (Exception e) {
      // 例外の名前とメッセージだけ表示する
      logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
    } finally {
      emitter.complete();
    }
    System.out.println("asyncShowVocabList complete");
  }

}
