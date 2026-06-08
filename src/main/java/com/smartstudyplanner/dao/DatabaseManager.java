/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartstudyplanner.dao;

/**
 *
 * @author Aissaoui
 */
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:smart_study_planner.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
    
    public static void afficherCheminBase() {
    File file = new File("smart_study_planner.db");
    System.out.println("Base SQLite utilisée : " + file.getAbsolutePath());
}

    public static void initialiserBase() {
        creerTableMatieres();
        creerTableTaches();
        creerTableSessions();
    }

    private static void creerTableMatieres() {
        String sql = """
                CREATE TABLE IF NOT EXISTS matieres (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nom TEXT NOT NULL,
                    difficulte INTEGER NOT NULL,
                    objectif TEXT NOT NULL
                );
                """;

        executerRequete(sql);
    }

    private static void creerTableTaches() {
        String sql = """
                CREATE TABLE IF NOT EXISTS taches (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    titre TEXT NOT NULL,
                    matiere_id INTEGER NOT NULL,
                    type TEXT NOT NULL,
                    progression INTEGER NOT NULL,
                    terminee INTEGER NOT NULL,
                    duree_estimee INTEGER,
                    jours_restants INTEGER,
                    coefficient INTEGER,
                    FOREIGN KEY (matiere_id) REFERENCES matieres(id)
                );
                """;

        executerRequete(sql);
    }

    private static void creerTableSessions() {
        String sql = """
                CREATE TABLE IF NOT EXISTS sessions (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    tache_id INTEGER NOT NULL,
                    duree_minutes INTEGER NOT NULL,
                    commentaire TEXT,
                    date_session TEXT NOT NULL,
                    FOREIGN KEY (tache_id) REFERENCES taches(id)
                );
                """;

        executerRequete(sql);
    }

    private static void executerRequete(String sql) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(sql);

        } catch (SQLException e) {
            System.out.println("Erreur SQLite : " + e.getMessage());
        }
    }
}
