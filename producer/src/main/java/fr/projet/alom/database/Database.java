package fr.projet.alom.database;

import fr.projet.alom.entities.Channel;

import java.util.ArrayList;
import java.util.List;

public class Database {

    public static Database INSTANCE;

    private List<Channel> channels;

    private Database() {
        this.channels = new ArrayList<>();
    }

    public static Database getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Database();
        return INSTANCE;
    }

    public List<Channel> getChannels() {
        return channels;
    }

}
