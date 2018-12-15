package my.home;

import my.home.Gotha.GothaSystem;
import my.home.GothaData.DataSystem;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * プラグインのメインクラス
 * @author KaigunHAO(RyuyaHiguchi)
 *
 */
public class GothaPlugin extends JavaPlugin {

    private Config config;
    private DataSystem ds;

    @Override
    public void onEnable(){
         getLogger().info("GothaPluginが有効になりました。");

        PluginManager pluginManager = this.getServer().getPluginManager();

        config = new Config(this);
        ds = new DataSystem();

        if(getDataFolder().exists()){
            getDataFolder().mkdirs();
        }

        if(config.LoadConfigFlag())
        {
            System.out.print("configファイルを読み込みました。");
        }
    }

    //TODO : 現状、コマンド打たないとそのプレイヤーデータが作成されない。
    //TODO : なので、初回プレイヤーログイン時、データがあるかチェックして、なかった場合は作る処理を行う
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // 「/warn」コマンドを打った場合
        if (label.equalsIgnoreCase("gotha")) {
            // コンソールからのコマンド実行を無効
            if(!(sender instanceof Player)) {
                sender.sendMessage("※プレイヤーからコマンドを実行してください！");
                return false;
            }

            // 実行者をプレイヤー型にキャスト
            Player p = (Player)sender;

            // データシステムの起動
            ds.CheckFolder();
            ds.OnPlayerData(p);

            // サブコマンドがあるかどうか
            if (args.length == 1) {
                //----------------------------
                // PON☆コマンド（ガチャ実行）
                //----------------------------
                if (args[0].equals("pon")) {
                    // ガチャ起動
                    GothaSystem gothaSys = new GothaSystem(p,ds.GetTicketNum_Int(p));
                    // チケットデータの消費を反映させる
                    ds.SetTicketNum(p,gothaSys.GetUsedTicketNum());

                    return true;
                }
                //----------------------------
                // チケット枚数確認コマンド
                //----------------------------
                if(args[0].equals("tknum")) {
                    String tknum = ds.GetTicketNum(p);

                    p.sendMessage(ChatColor.DARK_AQUA + "あなたのチケット枚数は"
                            + ChatColor.GOLD + tknum + ChatColor.DARK_AQUA + "です!");

                    return true;
                }
                //----------------------------
                // adminCommand(チケットを+1枚Getコマンド)
                //----------------------------
                if(args[0].equals("plus")){
                    int tknum = ds.GetTicketNum_Int(p);
                    tknum = tknum + 1;
                    ds.SetTicketNum(p,tknum);
                    p.sendMessage(ChatColor.LIGHT_PURPLE+"チケットを1枚Getしました！ 所持チケット数:"
                            + ChatColor.GOLD + tknum + ChatColor.LIGHT_PURPLE +"枚");

                    return true;
                }
                else{
                    CommandErrMessage(p);
                    return false;
                }
            }else {
               CommandErrMessage(p);
               return false;
            }
        }
        return true;
    }

    // 正しくないコマンドを打った時のメッセージ
    private void CommandErrMessage(Player p){
        p.sendMessage(ChatColor.RED +"/gotha <pon / tknum>  を入力してください");
        p.sendMessage(ChatColor.GOLD +"<pon>  "+ ChatColor.DARK_BLUE + "・・・ガチャを回すことができます");
        p.sendMessage(ChatColor.GOLD +"<tknum>"+ ChatColor.DARK_BLUE + "・・・ガチャチケット枚数を確認できます");
    }

}
