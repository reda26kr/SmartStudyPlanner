/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartstudyplanner.dao;

/**
 *
 * @author Aissaoui
 */
import com.smartstudyplanner.model.Matiere;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class MatiereDAO {
    
    public boolean existeMatiereAvecNom(String nom) {
    String sql = "SELECT COUNT(*) FROM matieres WHERE LOWER(TRIM(nom)) = LOWER(TRIM(?))";

    try (Connection connection = DatabaseManager.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setString(1, nom);

        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                System.out.println("DEBUG existeMatiereAvecNom(" + nom + ") = " + count);
                return count > 0;
            }
        }

    } catch (SQLException e) {
        System.out.println("Erreur lors de la vérification de la matière : " + e.getMessage());
    }

    return false;
}

    public void ajouterMatiere(Matiere matiere) {
        String sql = "INSERT INTO matieres(nom, difficulte, objectif) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, matiere.getNom());
            statement.setInt(2, matiere.getDifficulte());
            statement.setString(3, matiere.getObjectif());

            statement.executeUpdate();

            System.out.println("Matière sauvegardée dans la base.");

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la matière : " + e.getMessage());
        }
    }

    public ArrayList<Matiere> recupererToutesLesMatieres() {
        ArrayList<Matiere> matieres = new ArrayList<>();

        String sql = "SELECT id, nom, difficulte, objectif FROM matieres";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                int difficulte = resultSet.getInt("difficulte");
                String objectif = resultSet.getString("objectif");

                Matiere matiere = new Matiere(id, nom, difficulte, objectif);
                matieres.add(matiere);
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des matières : " + e.getMessage());
        }

        return matieres;
    }

    public void supprimerMatiere(int id) {
    if (matiereADesTaches(id)) {
        System.out.println("Impossible de supprimer cette matière : elle possède des tâches associées.");
        return;
    }

    String sql = "DELETE FROM matieres WHERE id = ?";

    try (Connection connection = DatabaseManager.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, id);

        int lignesSupprimees = statement.executeUpdate();

        if (lignesSupprimees > 0) {
            System.out.println("Matière supprimée.");
        } else {
            System.out.println("Aucune matière trouvée avec cet ID.");
        }

    } catch (SQLException e) {
        System.out.println("Erreur lors de la suppression de la matière : " + e.getMessage());
    }
}
    
    public boolean matiereADesTaches(int matiereId) {
    String sql = "SELECT COUNT(*) FROM taches WHERE matiere_id = ?";

    try (Connection connection = DatabaseManager.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, matiereId);

        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        }

    } catch (SQLException e) {
        System.out.println("Erreur lors de la vérification des tâches liées : " + e.getMessage());
    }

    return false;
}

}