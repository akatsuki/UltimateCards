package com.github.norbo11.commands.cards;

import org.bukkit.ChatColor;

import com.github.norbo11.commands.PluginCommand;
import com.github.norbo11.game.blackjack.BlackjackTable;
import com.github.norbo11.game.cards.CardsTable;
import com.github.norbo11.game.poker.PokerTable;
import com.github.norbo11.util.Messages;

public class CardsTables extends PluginCommand
{
    public CardsTables()
    {
        getAlises().add("tables");
        getAlises().add("list");
        getAlises().add("l");

        setDescription("Lists all created tables.");

        setArgumentString("");

        getPermissionNodes().add(PERMISSIONS_BASE_NODE + "cards");
        getPermissionNodes().add(PERMISSIONS_BASE_NODE + "cards." + getAlises().get(0));
    }

    @Override
    public boolean conditions()
    {
        if (getArgs().length == 1)
        {
            return true;
        } else
        {
            showUsage();
        }
        return false;
    }

    @Override
    public void perform() throws Exception
    {
        Messages.sendMessage(getPlayer(), "List of currently created card tables:");
        
        for (CardsTable table : CardsTable.getTables()) // Goes through all tables and lists them. Displays the name in red if the table is closed, or green if its open.
        {
            String color = "";
            if (table.isOpen())
            {
                color = "&2";
            } else
            {
                color = "&4";
            }

            String type = "";
            if (table instanceof PokerTable)
            {
                type = "Poker";
            } else if (table instanceof BlackjackTable)
            {
                type = "Blackjack";
            }

            Messages.sendMessage(getPlayer(), color + "[" + table.getID() + "] " + table.getName() + " - " + type);
        }
        
        Messages.sendMessage(getPlayer(), ChatColor.GREEN + "GREEN = Open. &cRED = Closed.");
        Messages.sendMessage(getPlayer(), "Use " + "&6/cards sit [table ID] [buy-in] " + "&fto join a table.");
    }
}