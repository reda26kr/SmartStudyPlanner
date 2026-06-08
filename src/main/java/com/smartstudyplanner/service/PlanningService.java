/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartstudyplanner.service;

/**
 *
 * @author Aissaoui
 */
import com.smartstudyplanner.dao.TacheDAO;
import com.smartstudyplanner.model.Tache;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PlanningService {
    private ArrayList<Tache> taches;
    private TacheDAO tacheDAO;

    public PlanningService() {
    this.tacheDAO = new TacheDAO();
    this.taches = tacheDAO.recupererToutesLesTaches();
}

    public void ajouterTache(Tache tache) {
    if (tache != null) {
        tacheDAO.ajouterTache(tache);
        this.taches = tacheDAO.recupererToutesLesTaches();
        System.out.println("Tâche ajoutée avec succès.");
    } else {
        System.out.println("Erreur : tâche invalide.");
    }
}

    public void afficherToutesLesTaches() {
        if (taches.isEmpty()) {
            System.out.println("Aucune tâche enregistrée.");
            return;
        }

        for (int i = 0; i < taches.size(); i++) {
            System.out.println("----- Tâche " + (i + 1) + " -----");
            taches.get(i).afficherDetails();
            System.out.println();
        }
    }

    public void afficherTachesPrioritaires() {
        if (taches.isEmpty()) {
            System.out.println("Aucune tâche enregistrée.");
            return;
        }

        trierParPriorite();

        System.out.println("===== TÂCHES PAR PRIORITÉ =====");

        for (int i = 0; i < taches.size(); i++) {
            System.out.println((i + 1) + ". "
                    + taches.get(i).getTitre()
                    + " | Priorité : "
                    + taches.get(i).calculerPriorite());
        }
    }

    public Tache trouverTachePrioritaire() {
        if (taches.isEmpty()) {
            return null;
        }

        Tache prioritaire = taches.get(0);

        for (int i = 1; i < taches.size(); i++) {
            if (taches.get(i).calculerPriorite() > prioritaire.calculerPriorite()) {
                prioritaire = taches.get(i);
            }
        }

        return prioritaire;
    }

    public double calculerProgressionGlobale() {
        if (taches.isEmpty()) {
            return 0;
        }

        int somme = 0;

        for (int i = 0; i < taches.size(); i++) {
            somme += taches.get(i).getProgression();
        }

        return (double) somme / taches.size();
    }

    public int compterTachesTerminees() {
        int compteur = 0;

        for (int i = 0; i < taches.size(); i++) {
            if (taches.get(i).isTerminee()) {
                compteur++;
            }
        }

        return compteur;
    }

    public int compterTachesNonTerminees() {
        return taches.size() - compterTachesTerminees();
    }

    public void marquerTacheTerminee(int numero) {
    int index = numero - 1;

    if (index >= 0 && index < taches.size()) {
        Tache tache = taches.get(index);

        tacheDAO.marquerTacheTerminee(tache.getId());

        this.taches = tacheDAO.recupererToutesLesTaches();

        System.out.println("Tâche marquée comme terminée.");
    } else {
        System.out.println("Numéro de tâche invalide.");
    }
}

    public void trierParPriorite() {
        Collections.sort(taches, new Comparator<Tache>() {
            @Override
            public int compare(Tache t1, Tache t2) {
                return t2.calculerPriorite() - t1.calculerPriorite();
            }
        });
    }
    
    public void supprimerTache(int numero) {
    int index = numero - 1;

    if (index >= 0 && index < taches.size()) {
        Tache tache = taches.get(index);

        tacheDAO.supprimerTache(tache.getId());

        this.taches = tacheDAO.recupererToutesLesTaches();

        System.out.println("Tâche supprimée avec succès.");
    } else {
        System.out.println("Numéro de tâche invalide.");
    }
}

    public ArrayList<Tache> getTaches() {
        return taches;
    }
}