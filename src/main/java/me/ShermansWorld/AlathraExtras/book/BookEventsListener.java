package me.ShermansWorld.AlathraExtras.book;

import com.github.alathra.siegeengines.libs.colorparser.ColorParser;

import io.lumine.mythic.bukkit.utils.time.Time;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class BookEventsListener implements Listener {
    // TODO Add UUID to Book as Some kind data
    @EventHandler
    public void onPlayerEditBook(PlayerEditBookEvent e) {
        if(e.isSigning()){
            BookMeta bookMeta = e.getPreviousBookMeta();
            List<Component> list = new java.util.ArrayList<>(Collections.emptyList());
            list.add(ColorParser.of("<white>Signed by %s</white>".formatted(e.getPlayer().getName())).build());
            list.add(ColorParser.of("<white>" + Date.from(Instant.now()).toString() + "</white>").build());
            bookMeta.lore(list);
            e.setNewBookMeta(bookMeta);
        }
    }
}
