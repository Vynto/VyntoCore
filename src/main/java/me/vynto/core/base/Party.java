package me.vynto.core.base;

import me.vynto.core.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Party {
    private UUID host;
    private List<UUID> members = new ArrayList<>();
    private List<UUID> invites = new ArrayList<>();

    public Party(UUID host) {
        this.host = host;
    }

    public UUID getHost() {
        return host;
    }

    public void setHost(UUID host) {
        this.host = host;
    }

    public List<UUID> getMembers() {
        return members;
    }

    public List<UUID> getInvites() {
        return invites;
    }

    public void addMember(UUID player) {
        members.add(player);
    }

    public void removeMember(UUID player) {
        members.remove(player);
    }

    public void addInvitee(UUID player) {
        invites.add(player);
    }

    public void removeInvitee(UUID player) {
        invites.remove(player);
    }

    public void crashParty() {
        members.clear();
        invites.clear();
    }

    public void sendMessage(String message) {
        String partyPrefix = "&a[&bParty&a] &7";

        for (UUID pMember : members) {
            Player member = Bukkit.getPlayer(pMember);
            if (member != null) member.sendMessage(Utils.cc(partyPrefix + message));
        }
    }
}
