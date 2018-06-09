/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mo.eyetribestaticheatmapplayer;

import com.theeyetribe.clientsdk.data.GazeData;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class HeatMapContainer {
    
    private HeatMap heatMap;
    private List<Point> points;
    private BufferedImage image;
    
    
    public HeatMapContainer(int width, int height){
        
        this.points =  new ArrayList<Point>();
        this.image = new  BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.heatMap = new HeatMap(this.points,image);
    }
    
    
    public void addData(GazeData gazeData){
    
    
    }    
    
    public void addPoint(Double x, Double y){
        if(!(x==0 && y == 0)){
            Point p = new Point(x.intValue(),y.intValue());
            this.points.add(p);
        }
    }
    
     public void addRelativePoint(Double x, Double y){
         Point p = new Point((int)(x*this.image.getWidth()),(int)(y*this.image.getHeight()));
         this.points.add(p);
    }   
    
    
    public void resize(int width, int height){
        this.heatMap = new HeatMap(this.points,new  BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
    }
   
    public void repaintImage(){
        image = this.heatMap.createHeatMapImage(5f);
    }
   
    public BufferedImage getImage(){
        return this.image;
    }
    
    public List<Point> getPoints(){
        return this.points;
    }
    
    
    public void setImaget(BufferedImage image){
        this.image= image;
    }
}
