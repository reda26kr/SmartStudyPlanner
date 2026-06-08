/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartstudyplanner.service;

/**
 *
 * @author Aissaoui
 */

import com.smartstudyplanner.model.Examen;
import com.smartstudyplanner.model.Tache;
import com.smartstudyplanner.model.TacheRevision;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ExportService {

    public void exporterTachesCSV(PlanningService planning, String nomFichier) {
        ArrayList<Tache> taches = planning.getTaches();

        if (taches.isEmpty()) {
            System.out.println("Aucune tâche à exporter.");
            return;
        }

        try (FileWriter writer = new FileWriter(nomFichier)) {

            writer.write("ID;Type;Titre;Matiere;Difficulte;Progression;Terminee;Priorite;DureeEstimee;JoursRestants;Coefficient\n");

            for (int i = 0; i < taches.size(); i++) {
                Tache tache = taches.get(i);

                String type = "";
                String dureeEstimee = "";
                String joursRestants = "";
                String coefficient = "";

                if (tache instanceof TacheRevision) {
                    TacheRevision revision = (TacheRevision) tache;
                    type = "REVISION";
                    dureeEstimee = String.valueOf(revision.getDureeEstimee());
                } else if (tache instanceof Examen) {
                    Examen examen = (Examen) tache;
                    type = "EXAMEN";
                    joursRestants = String.valueOf(examen.getJoursRestants());
                    coefficient = String.valueOf(examen.getCoefficient());
                }

                writer.write(
                        tache.getId() + ";"
                        + type + ";"
                        + nettoyerTexte(tache.getTitre()) + ";"
                        + nettoyerTexte(tache.getMatiere().getNom()) + ";"
                        + tache.getMatiere().getDifficulte() + ";"
                        + tache.getProgression() + ";"
                        + tache.isTerminee() + ";"
                        + tache.calculerPriorite() + ";"
                        + dureeEstimee + ";"
                        + joursRestants + ";"
                        + coefficient + "\n"
                );
            }

            System.out.println("Export CSV réussi : " + nomFichier);

        } catch (IOException e) {
            System.out.println("Erreur lors de l'export CSV : " + e.getMessage());
        }
    }

    private String nettoyerTexte(String texte) {
        if (texte == null) {
            return "";
        }

        return texte.replace(";", ",").replace("\n", " ").replace("\r", " ");
    }
}
