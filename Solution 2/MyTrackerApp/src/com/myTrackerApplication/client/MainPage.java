package com.myTrackerApplication.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

import java.util.HashMap;

/**
 * Created by drrakeshkumarsharma on 20/02/16.
 */
public class MainPage {

    public static Panel getContentGrid(HashMap dataMap) {

        int noOfRows = dataMap.size();
        // Create a grid
        Grid grid = new Grid(noOfRows, 2);



        // Add items to the grid
        int row = 0;
        for (Object key : dataMap.keySet()) {

            grid.setText(row, 0, key.toString());
            grid.setText(row, 1, dataMap.get(key).toString());
            row++;

            }


        // Return the panel
        grid.ensureDebugId("cwGrid");
        return grid;
    }

    public static VerticalPanel getNavBar(){

        // Create a Vertical Panel
        VerticalPanel vPanel = new VerticalPanel();
        vPanel.setSpacing(10);

        // Add some content to the panel
        vPanel.add(new Button("Home"));
        vPanel.add(new Button("Report"));
        vPanel.add(new Button("Logout"));

        // Return the content
        vPanel.ensureDebugId("cwVerticalPanel");
        return vPanel;
    }



}
