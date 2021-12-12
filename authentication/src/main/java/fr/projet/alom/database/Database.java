package fr.projet.alom.database;

import fr.projet.alom.entities.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Database {

    public static Database INSTANCE;

    private List<User> users;

    private Database() {
        this.users = new ArrayList<>();
    }

    public static Database getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Database();
        return INSTANCE;
    }

    public List<User> getUsers() {
        return users;
    }
}
