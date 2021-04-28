package me.vynto.core.commands;

import me.vynto.core.VyntoCore;
import me.vynto.core.base.Party;
import me.vynto.core.base.PartyManager;
import me.vynto.core.misc.Utils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class PartyCommand implements CommandExecutor {
    private VyntoCore plugin;
    public PartyCommand(VyntoCore instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = Utils.getPrefix();
        String partyPrefix = "&a[&bParty&a] ";
        PartyManager pManager = plugin.getPartyManager();

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.cc(prefix + "&cThe console cannot interact with parties."));
            return true;
        }
        Player player = (Player) sender;

        if (!Utils.hasPermission(sender, "party")) return true;

        Party party;
        UUID uuid = player.getUniqueId();

        if (label.equalsIgnoreCase("pc")) {
            if (args.length == 0) {
                player.sendMessage(Utils.cc(prefix + "&cUsage: &e/pc <message ...>"));
                return true;
            }

            if (!inParty(uuid)) {
                player.sendMessage(Utils.cc(prefix + "&cYou are not in a party"));
                return true;
            }

            party = pManager.getPlayerParty(uuid);
            party.sendMessage("&b" + ChatColor.stripColor(player.getDisplayName()) + ": &d" + StringUtils.join(args, " "));
            return true;
        }

        if (args.length == 0) {
            sendPartyHelp(player);
            return true;
        }

        switch (args[0]) {
            case "create":
                if (!Utils.hasPermission(sender, "party.create")) return true;

                if (inParty(uuid)) {
                    player.sendMessage(Utils.cc(prefix + "&cYou are already in a party"));
                    player.sendMessage(Utils.cc(prefix + "&cPlease leave this one with &e/party leave &cbefore creating another"));
                    return true;
                }
                party = new Party(uuid);
                party.addMember(uuid);
                pManager.addParty(party);

                player.sendMessage(Utils.cc(prefix + "&6You have successfully created a party. Invite someone with &d/party invite <player>"));
                break;
            case "invite":
                if (!inParty(uuid)) {
                    player.sendMessage(Utils.cc(prefix + "&cYou are not in a party"));
                    return true;
                }
                if (!isPartyHost(uuid)) {
                    player.sendMessage(Utils.cc(prefix + "&cOnly the party host can use this command"));
                    return true;
                }

                if (args.length == 2) {
                    Player invitee = Bukkit.getPlayer(args[1]);
                    if (invitee == null) {
                        player.sendMessage(Utils.cc(prefix + "&cAn invalid player was specified."));
                        return true;
                    }

                    party = pManager.getPlayerParty(uuid);
                    if (party.getInvites().contains(invitee.getUniqueId())) {
                        player.sendMessage(Utils.cc(prefix + "&cYou have already invited &e" + ChatColor.stripColor(invitee.getDisplayName()) + " &cto the party."));
                        return true;
                    }
                    if (party.getMembers().contains(invitee.getUniqueId())) {
                        player.sendMessage(Utils.cc(prefix + "&e" + ChatColor.stripColor(invitee.getDisplayName()) + "&c is already in the party."));
                        return true;
                    }

                    party.addInvitee(invitee.getUniqueId());

                    TextComponent message = new TextComponent(Utils.cc(prefix + "&6You have been invited to &d" + ChatColor.stripColor(player.getDisplayName()) + "'s &6party. &dCLICK HERE &6to join."));
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party accept " + player.getName()));
                    message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                            new ComponentBuilder("Click to join the party").create()
                    ));
                    invitee.spigot().sendMessage(message);

                    party.sendMessage("&d" + player.getName() + " &6has invited &d" + ChatColor.stripColor(invitee.getDisplayName()) + " &6to the party");
                }
                else {
                    player.sendMessage(Utils.cc(prefix + "&cUsage: &e/party invite <player>"));
                    return true;
                }
                break;
            case "invites":
                if (!inParty(uuid)) {
                    player.sendMessage(Utils.cc(prefix + "&cYou are not in a party"));
                    return true;
                }
                if (!isPartyHost(uuid)) {
                    player.sendMessage(Utils.cc(prefix + "&cOnly the party host can use this command"));
                    return true;
                }

                party = pManager.getPlayerParty(uuid);
                List<UUID> invites = party.getInvites();

                if (invites.size() == 0) {
                    player.sendMessage(Utils.cc(prefix + "&cThere are no pending invites for your party"));
                    return true;
                }

                player.sendMessage(Utils.cc("Pending Invites:"));
                for (UUID invite : invites) {
                    OfflinePlayer pInvitee = Bukkit.getOfflinePlayer(invite);
                    if (pInvitee.isOnline()) {
                        player.sendMessage(Utils.cc("&7- &6" + pInvitee.getPlayer().getDisplayName()));
                    }
                    else {
                        player.sendMessage(Utils.cc("&7- &6" + pInvitee.getName() + " &c(Offline)"));
                    }
                }
                break;
            case "members":
                if (!inParty(uuid)) {
                    player.sendMessage(Utils.cc(prefix + "&cYou are not in a party"));
                    return true;
                }

                party = pManager.getPlayerParty(uuid);
                List<UUID> members = party.getMembers();

                if (members.size() == 0) {
                    player.sendMessage(Utils.cc(prefix + "&cThere are no members in your party"));
                    return true;
                }

                player.sendMessage(Utils.cc("&6&lParty Members:"));
                for (UUID member : members) {
                    OfflinePlayer pMember = Bukkit.getOfflinePlayer(member);
                    if (pMember.isOnline()) {
                        player.sendMessage(Utils.cc("&7- &6" + pMember.getName()));
                    }
                    else {
                        player.sendMessage(Utils.cc("&7- &6" + pMember.getName() + " &c(Offline)"));
                    }
                }
                break;
            case "abandon":
                if (!inParty(uuid)) {
                    player.sendMessage(Utils.cc(prefix + "&cYou are not in a party"));
                    return true;
                }
                if (!isPartyHost(uuid)) {
                    player.sendMessage(Utils.cc(prefix + "&cOnly the party host can use this command"));
                    return true;
                }

                party = pManager.getPlayerParty(uuid);

                party.sendMessage("The host has ended the party");

                party.crashParty();
                pManager.deleteParty(party);

                player.sendMessage(Utils.cc(prefix + "&cThe party has successfully ended"));
                break;
            case "leave":
                if (!inParty(uuid)) {
                    player.sendMessage(Utils.cc(prefix + "&cYou are not in a party"));
                    return true;
                }
                if (isPartyHost(uuid)) {
                    player.sendMessage(Utils.cc(prefix + "&cYou cannot leave your party while you are the host"));
                    player.sendMessage(Utils.cc(prefix + "&cPlease promote another player with &e/party leave &cor disband the party with &e/party abandon"));
                    return true;
                }

                party = pManager.getPlayerParty(uuid);
                party.removeMember(uuid);

                player.sendMessage(Utils.cc("&6You have successfully left the party"));
                party.sendMessage("&d" + player.getName() + " &6has left the party.");
                break;
            case "accept":
                if (inParty(uuid)) {
                    player.sendMessage(Utils.cc(prefix + "&cYou are already in a party"));
                    player.sendMessage(Utils.cc(prefix + "&cPlease leave this one with &e/party leave &cbefore joining another"));
                    return true;
                }

                if (args.length == 2) {
                    Player pHost = Bukkit.getPlayer(args[1]);
                    if (pHost == null) {
                        player.sendMessage(Utils.cc(prefix + "&cThere is no pending party invite from that host"));
                        return true;
                    }

                    party = pManager.getPartyFromInvite(pHost.getUniqueId(), uuid);
                    if (party == null) {
                        player.sendMessage(Utils.cc(prefix + "&cThere is no pending party invite from that host"));
                        return true;
                    }

                    party.addMember(uuid);
                    party.removeInvitee(uuid);

                    player.sendMessage(Utils.cc(partyPrefix + "&6You have accepted &d" + pHost.getName() + "'s &6party invite"));
                    party.sendMessage("&d" + player.getName() + " &6has joined the party");
                }
                else {
                    player.sendMessage(Utils.cc(prefix + "&cUsage: &e/party accept <player>"));
                    return true;
                }
                break;
            case "decline":
                if (args.length == 2) {
                    Player pHost = Bukkit.getPlayer(args[1]);
                    if (pHost == null) {
                        player.sendMessage(Utils.cc(prefix + "&cThere is no pending party invite from that host"));
                        return true;
                    }

                    party = pManager.getPartyFromInvite(pHost.getUniqueId(), uuid);
                    if (party == null) {
                        player.sendMessage(Utils.cc(prefix + "&cThere is no pending party invite from that host"));
                        return true;
                    }

                    party.removeInvitee(uuid);
                    player.sendMessage(Utils.cc(partyPrefix + "&6You have rejected &d" + pHost.getName() + "'s &6party invite"));
                }
                else {
                    player.sendMessage(Utils.cc(prefix + "&cUsage: &e/party decline <player>"));
                    return true;
                }
                break;
            case "promote":
                if (!inParty(uuid)) {
                    player.sendMessage(Utils.cc(prefix + "&cYou are not in a party"));
                    return true;
                }
                if (!isPartyHost(uuid)) {
                    player.sendMessage(Utils.cc(prefix + "&cOnly the party host can use this command"));
                    return true;
                }

                party = pManager.getPlayerParty(uuid);

                if (args.length == 2) {
                    Player pMember = Bukkit.getPlayer(args[1]);
                    if (pMember == null) {
                        player.sendMessage(Utils.cc(prefix + "&cAn invalid player was specified."));
                        return true;
                    }
                    if (!party.getMembers().contains(pMember.getUniqueId())) {
                        player.sendMessage(Utils.cc(prefix + "&cThat player is not in the party."));
                        return true;
                    }
                    if (pMember.getUniqueId().equals(party.getHost())) {
                        player.sendMessage(Utils.cc(prefix + "&e" + pMember.getName() + "&c is already the party host."));
                        return true;
                    }

                    party.setHost(pMember.getUniqueId());

                    player.sendMessage(Utils.cc(prefix + "&6You are no longer the party host"));
                    pMember.sendMessage(Utils.cc(partyPrefix + "&d" + player.getName() + " &6has made you the party host"));
                    party.sendMessage(Utils.cc("&d" + pMember.getName() + " &6has been promoted to party host"));
                }
                else {
                    player.sendMessage(Utils.cc(prefix + "&cUsage: &e/party promote <player>"));
                    return true;
                }
                break;
            case "remove":
                if (!inParty(uuid)) {
                    player.sendMessage(Utils.cc(prefix + "&cYou are not in a party"));
                    return true;
                }
                if (!isPartyHost(uuid)) {
                    player.sendMessage(Utils.cc(prefix + "&cOnly the party host can use this command"));
                    return true;
                }

                party = pManager.getPlayerParty(uuid);

                if (args.length == 2) {
                    Player pMember = Bukkit.getPlayer(args[1]);
                    if (pMember == null) {
                        player.sendMessage(Utils.cc(prefix + "&cAn invalid player was specified."));
                        return true;
                    }
                    if (!party.getMembers().contains(pMember.getUniqueId())) {
                        player.sendMessage(Utils.cc(prefix + "&cThat player is not in the party."));
                        return true;
                    }
                    if (pMember.getUniqueId().equals(party.getHost())) {
                        player.sendMessage(Utils.cc(prefix + "&cYou cannot kick the host from the party"));
                        return true;
                    }

                    party.removeMember(pMember.getUniqueId());

                    pMember.sendMessage(Utils.cc(prefix + "&cYou have been kicked from the party"));
                    party.sendMessage("&d" + pMember.getName() + " &6has been kicked from the party.");
                }
                else {
                    player.sendMessage(Utils.cc(prefix + "&cUsage: &e/party remove <player>"));
                    return true;
                }
                break;
            default:
                sendPartyHelp(player);
        }
        return true;
    }

    private void sendPartyHelp(Player player) {
        player.sendMessage("");
        player.sendMessage(Utils.cc("&6&lParty Help"));
        player.sendMessage(Utils.cc("&6/party create: &dCreate a party"));
        player.sendMessage(Utils.cc("&6/party invite <player>: &dInvite a player to your party &c(host only)"));
        player.sendMessage(Utils.cc("&6/party invites: &dList party invites &c(host only)"));
        player.sendMessage(Utils.cc("&6/party members: &dList party members &c(host only)"));
        player.sendMessage(Utils.cc("&6/party abandon: &dAbandon your current party &c(host only)"));
        player.sendMessage(Utils.cc("&6/party leave: &dLeave your current party"));
        player.sendMessage(Utils.cc("&6/party accept <player>: &dAccept a party invite"));
        player.sendMessage(Utils.cc("&6/party decline <player>: &dDecline a party invite"));
        player.sendMessage(Utils.cc("&6/party promote <player>: &dPromote a player to party host &c(host only)"));
        player.sendMessage(Utils.cc("&6/party remove <player>: &dKick a player from your party &c(host only)"));
        player.sendMessage(Utils.cc("&6/pc <message>: &dSend a message in party chat"));
        player.sendMessage("");
    }

    private boolean inParty(UUID player) {
        return !(plugin.getPartyManager().getPlayerParty(player) == null);
    }

    private boolean isPartyHost(UUID player) {
        return plugin.getPartyManager().getPlayerParty(player).getHost().equals(player);
    }
}
