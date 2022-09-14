package com.omri.components;

import com.omri.engine.Window;
import com.omri.renderer.DebugDraw;
import com.omri.util.Settings;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.Set;

public class GridLines extends  Component{
    @Override
    public void update(float dt){
        Vector2f cameraPos = Window.getScene().camera().position;
        Vector2f projectionSize = Window.getScene().camera().getProjectionSize();

        int firstX = ((int) (cameraPos.x / Settings.GRID_WIDTH) - 1) * Settings.GRID_WIDTH;
        int firstY = ((int) (cameraPos.y / Settings.GRID_HEIGHT) - 1) * Settings.GRID_HEIGHT;

        int numVtLines = (int) (projectionSize.x / Settings.GRID_WIDTH) + 2;
        int numHzLines = (int) (projectionSize.y / Settings.GRID_HEIGHT) + 2;
        //System.out.println(numVtLines);
        //System.out.println(numHzLines);
        int height = (int) projectionSize.y + Settings.GRID_HEIGHT * 2;
        int width = (int) projectionSize.x + Settings.GRID_WIDTH * 2;

        int maxLines = Math.max(numHzLines, numVtLines);
        Vector3f color = new Vector3f(0.2f, 0.2f, 0.2f);
        //System.out.println(maxLines);
//        System.out.println("firstX: " + firstX);
//        System.out.println("firstY: " + firstY);
        for(int i = 0; i < maxLines; i++){

            int x = firstX + (Settings.GRID_WIDTH * i);
            int y = firstY + (Settings.GRID_HEIGHT * i);

            if(i < numVtLines){
                DebugDraw.addLine2D(new Vector2f(x, firstY), new Vector2f(x, firstY + height), color);
            }

            if(i < numHzLines){
                DebugDraw.addLine2D(new Vector2f(firstX, y), new Vector2f(firstX + width, y), color);
            }
        }

    }
}
