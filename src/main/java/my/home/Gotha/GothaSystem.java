package my.home.Gotha;

import Util.RandomGene;
import org.bukkit.entity.Player;

/**
 * ガチャシステムクラス
 * @author KaigunHAO(RyuyaHiguchi)
 *
 */
public class GothaSystem {

    private int ticketNum = 0;  // 現在のチケット枚数
    private int usedTkNum = 0;  // 使用した後のチケット枚数
    private Player player;      // 使用者の情報データ

    public GothaSystem(Player p,int tknum){

        // 格納
        player = p;
        ticketNum = tknum;

        // チケットを消費させる
        if(isUse()) {
            // ランダムの値を取得するオブジェクト
            RandomGene r = new RandomGene();
            // 0~99の値を取得して相手番号に直す
            int itemNum = r.GetRandomNum(100) + 1;
            // アイテムをゲットしたことをプレイヤーに促すメッセージ
            p.sendMessage("装飾ブロック No." + r.GetRandomNum(100) + "をGetしました。");
        }else {
            // チケットが足りないことをプレイヤーに促すメッセージ
            player.sendMessage("チケットが足りません。ガチャを回せませんでした。");
        }
    }

    // チケットを使用できたかの判定
    private boolean isUse(){
        // 所持しているチケットを１枚使用する処理
        if(ticketNum > 0){
            usedTkNum = ticketNum - 1;
            return true;
        }
        return false;
    }

    // 使った結果を返す処理
    public int GetUsedTicketNum(){
        return usedTkNum;
    }

}
