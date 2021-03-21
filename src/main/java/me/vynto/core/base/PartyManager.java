package me.vynto.core.base;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PartyManager {
    public List<Party> parties = new ArrayList<>();

    public List<Party> getPartyList() {
        return parties;
    }

    public void addParty(Party party) {
        parties.add(party);
    }

    public void deleteParty(Party party) {
        parties.remove(party);
    }

    public Party getPlayerParty(UUID player) {
        for (Party party : parties) {
            if (party.getMembers().contains(player)) {
                return party;
            }
        }
        return null;
    }

    public Party getPartyFromInvite(UUID host, UUID invitee) {
        for (Party party : parties) {
            if (party.getHost().equals(host) && party.getInvites().contains(invitee)) {
                return party;
            }
        }
        return null;
    }
}
