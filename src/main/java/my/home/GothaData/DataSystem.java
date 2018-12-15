package my.home.GothaData;

import org.bukkit.entity.Player;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * データの受け渡しや作成をリアルタイムで行うクラス（パイプのようなもの）
 * @author KaigunHAO(RyuyaHiguchi)
 *
 */
public class DataSystem {

    public static File pluginDir = new File("plugins//GothaPlugin");
    public static File dataDir = new File(pluginDir, "Data");
    public File playerYMLData = new File(dataDir, "PlayerData.yml");

    private Map<String, String> playersData = new HashMap<String, String>();

    public DataSystem() {
        CheckFolder();
    }

    public void OnPlayerData(Player player) {

        // playerDataファイルがあるかチェック
        if (!playerYMLData.exists()) {
            try {
                playerYMLData.createNewFile();

                // 最初だけヘッダを書き込み
                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(playerYMLData, true));
                    bw.write("PlayerUUID,TicketNum\n");
                } catch (IOException e) {

                }
            } catch (IOException e) {
                System.out.println("PlayerDataのエラー");
                return;
            }
        }

        // プレイヤーデータの読み込み
        playersData = LoadData();
        System.out.println(1);

        if(playersData.get(player.getUniqueId().toString()) == null){
            // プレイヤーデータがない場合の処理
            NewCreateData(player);
            System.out.println(player.getName() + "を作成しました");
        }

        // UUIDのチェック
        for (String uuid : playersData.keySet()) {
            if (player.getUniqueId().toString().equals(uuid)) {
                // プレイヤーデータがある場合の処理
                SaveData();
            } else if (uuid == null) {
                // プレイヤーデータがない場合の処理
                NewCreateData(player);
                System.out.println(player.getName() + "を作成しました");
            }
            System.out.println(2);
        }

    }



    public void CheckFolder() {
        System.out.println("PlayerDataのフォルダをチェックしています。");

        if (!pluginDir.exists()) {
            pluginDir.mkdirs();

        }

        if (!dataDir.exists()) {
            dataDir.mkdirs();
            System.out.println("Dataフォルダを生成しました。( ../plugins/GothaPlugin/Data )");

        }

    }

    // 新しいデータを作成
    private void NewCreateData(Player player) {
        // 初期データの格納
        playersData.put(player.getUniqueId().toString(), Integer.toString(0));
        // 書き込み
        SaveData();
    }

    // プレイヤーデータをテキストデータに保存する
    public void SaveData()
    {
        try{

            BufferedWriter bw = new BufferedWriter(new FileWriter(playerYMLData, false));
            try{
                for (String uuid:playersData.keySet()) {
                    String tknum = playersData.get(uuid);
                    bw.write(uuid + "," + tknum + "\n");
                }
            }finally {
                bw.close();
            }
        }catch (IOException e){
        }
    }

    // テキストデータをプレイヤーデータに読み込む
    public Map<String,String> LoadData(){

        Map<String,String> data = new HashMap<String, String>();
        try {
            /**
             * Dataファイルのリード
             */
            BufferedReader br = new BufferedReader(new FileReader(playerYMLData));

            try {
                while (true) {
                    // 行の切り出し
                    String line = br.readLine();
                    // それ以上ない場合はループを抜ける
                    if (line == null) break;
                    // データを区切る
                    String[] lineData = line.split(",", 0);
                    System.out.println("lineData[0]" + lineData[0]);
                    System.out.println("lineData[1]" + lineData[1]);
                    // データ格納(UUID,チケット数)
                    data.put(lineData[0], lineData[1]);
                }
            }finally {
                br.close();
            }
        }catch (IOException e){

        }
        return data;
    }

    // 指定のプレイヤーのチケットをSETする
    public void SetTicketNum(Player player,int tkNum) {
        playersData.put(player.getUniqueId().toString(),Integer.toString(tkNum));
        SaveData();
    }

    // 指定のプレイヤーのチケット枚数をGET(STR)する
    public String GetTicketNum(Player player){
        SaveData();
        playersData = LoadData();
        return playersData.get(player.getUniqueId().toString());
    }

    // 指定のプレイヤーのチケット枚数をGET(INT)する
    public int GetTicketNum_Int(Player player){
        SaveData();
        playersData = LoadData();

        int tknum = Integer.parseInt(playersData.get(player.getUniqueId().toString()));
        return tknum;
    }
}
