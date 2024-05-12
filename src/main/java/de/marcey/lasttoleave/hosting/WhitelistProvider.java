package de.marcey.lasttoleave.hosting;

import de.marcey.eventsystemapi.EventSystemAPI;
import de.marcey.eventsystemapi.utils.Server;
import de.marcey.eventsystemapi.utils.WhitelistType;
import de.marcey.eventsystemapi.utils.sql.SQLServers;

public class WhitelistProvider {

    private static Server server = EventSystemAPI.getServerObject();
    private static SQLServers servers = EventSystemAPI.getServers();

    public static WhitelistType switchToNextWhitelist(){
        if(server.getWhitelistType().equals(WhitelistType.WHITELIST_ON)){
            WhitelistType toSet = WhitelistType.WHITELIST_OFF;
            server.setWhitelistType(toSet);
            servers.set(server);
            return toSet;
        } else {
            WhitelistType toSet = WhitelistType.WHITELIST_ON;
            server.setWhitelistType(toSet);
            servers.set(server);
            return toSet;
        }
    }

    public static WhitelistType getWhitelistType(){
        return server.getWhitelistType();
    }

}
