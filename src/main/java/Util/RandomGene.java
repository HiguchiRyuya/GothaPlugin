package Util;
import java.util.Random;
import java.util.Calendar;

/**
 * 独自の完全ランダム生成クラス
 * @author KaigunHAO(RyuyaHiguchi)
 *
 */
public class RandomGene {
    public RandomGene(){

    }
    public int GetRandomNum(int range){
        int timeData[] = {0,0,0,0};
        int seedNum =0;

        Calendar calendar = Calendar.getInstance();

        // 乱数のシード値を完全ランダムにする処理
        String timeStr =  calendar.getTime().toString();
        // 時間以外のデータを切り出し
        String[] str = timeStr.split(" ",0);
        // 時間の分類で切り出し
        String data[] = str[3].split(":",0);

        // データとして使えるようにINTに変換
        for (int i = 0;i < data.length;i++) {
            timeData[i] = Integer.parseInt(data[i]);
        }

        // ミリ秒を入れる
        timeData[3] = calendar.get(Calendar.MILLISECOND);

        // ランダムなシード値を計算
        seedNum = (timeData[0] * timeData[1] * timeData[2]  * timeData[3]) % range;

        Random random = new Random(seedNum);
        return random.nextInt(range);
    }
}
