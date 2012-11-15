package com.github.norbo11.commands.cards;

import com.github.norbo11.commands.PluginCommand;
import com.github.norbo11.game.cards.CardsPlayer;
import com.github.norbo11.game.cards.CardsTable;
import com.github.norbo11.util.ErrorMessages;
import com.github.norbo11.util.NumberMethods;

public class CardsDetails extends PluginCommand
{
    public CardsDetails()
    {
        getAlises().add("details");
        getAlises().add("info");
        getAlises().add("d");

        setDescription("Gives specific details about a table. Allowed types: all, settings, players, general | other.");

        setArgumentString("(type) (table ID)");

        getPermissionNodes().add(PERMISSIONS_BASE_NODE + "cards");
        getPermissionNodes().add(PERMISSIONS_BASE_NODE + "cards." + getAlises().get(0));
    }

    CardsTable cardsTable;

    // Lists the specified details type of the specified table. If no table is specified, lists details of the table that the player is sitting on.
    @Override
    public boolean conditions()
    {
        // cards details 5
        if (getArgs().length == 2)
        {
            int tableID = NumberMethods.getInteger(getArgs()[1]);
            if (tableID != -99999)
            {
                cardsTable = CardsTable.getTable(tableID);
                if (cardsTable != null) return true;
                else
                {
                    ErrorMessages.notTable(getPlayer(), getArgs()[1]);
                }
            } else
            {
                ErrorMessages.invalidNumber(getPlayer(), getArgs()[1]);
            }
        }

        // cards details
        else if (getArgs().length == 1)
        {
            CardsPlayer cardsPlayer = CardsPlayer.getCardsPlayer(getPlayer().getName());
            if (cardsPlayer != null)
            {
                cardsTable = cardsPlayer.getTable();
                return true;
            } else
            {
                ErrorMessages.notSittingAtTable(getPlayer());
            }
        } else
        {
            showUsage();
        }

        return false;

    }

    @Override
    public void perform() throws Exception
    {
        cardsTable.displayDetails(getPlayer());
    }
}
