package reactorplugin;

import io.anuke.arc.*;
import io.anuke.arc.util.*;
import io.anuke.mindustry.*;
import io.anuke.mindustry.content.*;
import io.anuke.mindustry.entities.type.*;
import io.anuke.mindustry.game.EventType.*;
import io.anuke.mindustry.game.Team;
import io.anuke.mindustry.gen.*;
import io.anuke.mindustry.net.Packets;
import io.anuke.mindustry.plugin.Plugin;
import io.anuke.mindustry.world.Tile;

import java.lang.Math;

public class AntiNukePlugin extends Plugin {
    private final float radius = 17;
    private boolean kickPlayer = false;

    //register event handlers and create variables in the constructor
    public AntiNukePlugin() {
        //listen for a block selection event
        Events.on(BuildSelectEvent.class, event -> {
            try {
                if (!event.breaking && event.builder.buildRequest().block == Blocks.thoriumReactor && event.builder instanceof Player) {
                    //send a message to everyone saying that this player has begun building a reactor
                    Tile coreTile = ((Player) event.builder).getClosestCore().getTile();
                    if (coreTile == null) {
                        return;
                    }

                    if (Math.sqrt(Math.pow(coreTile.x - event.tile.x, 2) + Math.pow(coreTile.y - event.tile.y, 2)) < (float) radius) {
                        Call.beginBreak(event.builder.getTeam(), event.tile.x, event.tile.y);
                        Call.onDeconstructFinish(event.tile, Blocks.thoriumReactor);
                        Log.info("Player {0} tried to nuke the core...", ((Player) event.builder).name);
                        if (kickPlayer){
                            //kick
                            Call.sendMessage(((Player) event.builder).name + "[green] kicked because he tried to nuke the core.");
                            Call.onKick(((Player) event.builder).con, Packets.KickReason.kick);

                        } else {
                            //print msg
                            ((Player) event.builder).sendMessage("[scarlet] TOO CLOSE TO THE CORE, STOP");
                        }
                        return;
                    }
                }
            } catch (Exception e) {
                Log.err("Nuke spam detected...");
            }
        });
    }

    //register commands that run on the server
    @Override
    public void registerServerCommands(CommandHandler handler){
        handler.register("antinuke-kick", "<on/off>", "Kicks a player that tries to build a reactor next to the core.", args -> {
            if ("on".equals(args[0])){
                kickPlayer = true;
                Log.info("Anti nuke kick enabled...");
            } else {
                kickPlayer = false;
                Log.info("Anti nuke kicking disabled...");
            }
        });
    }
}
