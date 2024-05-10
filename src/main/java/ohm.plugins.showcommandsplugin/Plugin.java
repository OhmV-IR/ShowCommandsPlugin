package ohm.plugins.showcommandsplugin;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.event.server.RemoteServerCommandEvent;
import java.util.ArrayList;
import java.util.List;
public class Plugin extends JavaPlugin implements Listener {

  @Override
  public void onEnable() {
    Bukkit.getPluginManager().registerEvents(this, this);
  }

  @EventHandler
  public void onPlayerCommandSent(PlayerCommandPreprocessEvent event) {
    List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
    for(int i = 0; i < players.size(); i++){
      players.get(i).sendMessage(event.getPlayer().getName() + " executed the command " + event.getMessage());
    }
  }
  @EventHandler
  public void onServerCommandSent(ServerCommandEvent event){
    List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
    for(int i = 0; i < players.size(); i++){
      players.get(i).sendMessage("Server executed the command " + event.getCommand());
    }
  }
  @EventHandler
  public void onRemoteServerCommandSent(RemoteServerCommandEvent event){
    List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
    for(int i = 0; i < players.size(); i++){
      players.get(i).sendMessage("Server executed the command " + event.getCommand());
    }
  }
}