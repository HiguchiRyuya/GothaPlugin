package my.home;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

/**
 * 設定をまとめて取り扱う構造体
 * @author KaigunHAO(RyuyaHiguchi)
 *
 */

public class Config {

    private final Plugin plugin;
    private FileConfiguration config = null;

    private String mainTitleMessage;


    public Config(Plugin plugin) {
        this.plugin = plugin;
        // ロードする
        load();
    }

    /**
     * 設定をロードする
     */
    public void load(){
        // 設定ファイルの保存
        plugin.saveDefaultConfig();
        if(config != null){
            plugin.reloadConfig();
        }
        config = plugin.getConfig();

        if (!config.contains("GothaConfig")) { // 存在チェック
            plugin.getLogger().info("config.ymlにGothaConfigがありません。");
        }

        mainTitleMessage = config.getString("WarnSetting.TitleMessage.Main");

    }

    /**
     * ゲッター
     */
    public String getMainTitleMessage(){
        return mainTitleMessage;
    }

    /**
     * ロードしたときに返す関数
     */
    public boolean LoadConfigFlag(){
        return true;
    }
}