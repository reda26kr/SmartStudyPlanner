/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.smartstudyplanner;

/**
 *
 * @author Aissaoui
 */

import com.smartstudyplanner.dao.DatabaseManager;
import com.smartstudyplanner.ui.ConsolMenu;

public class SmartStudyPlanner {

    public static void main(String[] args) {
        DatabaseManager.afficherCheminBase();
        DatabaseManager.initialiserBase();

        ConsolMenu menu = new ConsolMenu();
        menu.demarrer();
    }
}