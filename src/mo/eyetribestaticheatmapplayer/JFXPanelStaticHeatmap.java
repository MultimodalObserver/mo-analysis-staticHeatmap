/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mo.eyetribestaticheatmapplayer;

import com.theeyetribe.clientsdk.data.GazeData;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableMap;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaMarkerEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.Timer;


/**
 *
 * @author gustavo
 */
public class JFXPanelStaticHeatmap extends JFXPanel {

    
    private Color colorFijations;
    
    private int videoWidth;
    private int videoHeight;
    
    private int originalWidth; 
    private int originalHeight; 
    
    private int propotionCase;
    private double proportion;
    
    private DoubleProperty mvw ;
    private DoubleProperty mvh ;
    
    private MediaPlayer player;
    private FixationMap fijationMap;
    
    private boolean isPlaying ;
    
    private int initXDrag;
    private int initYDrag;
    private int endXDrag;
    private int endYDrag;
    private boolean isDraged;
    
    private long offset;
        
    private HeatMapContainer heatMap;
    private  BufferedImage heatMapImage;
   // private int bussyHeatMap;
    private int realWidth;
    private int realHeight;
    
    private long startHeatMap;
    private long endHeatMap;
    
    private long startTime;
    private long endTime;
    
    private ArrayList<GazeData> dataFromHeatMap;
    private ObservableMap<String, Duration> markers;
    
    private float opacityMap;
    private float multiplier;
        
    public JFXPanelStaticHeatmap() throws FileNotFoundException {
        
        super();
        this.dataFromHeatMap = null;
        this.videoWidth = -1;
        this.videoHeight = -1;
        this.offset = -1;
        this.heatMap = null;
        this.isPlaying = false;
        this.heatMapImage = null;
        this.startHeatMap = 0;
        this.endHeatMap = 0;
        this.startTime=0;
        this.endTime=0;
        this.opacityMap = 0.5f;
        this.multiplier = 0.5f;
         /////////////////////////////////////////////////7
                  
           
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });        
        
    }
    
    
    
    private void formComponentResized(java.awt.event.ComponentEvent evt) {                                      
       if(this.heatMapImage!=null){
        this.heatMapImage = this.resizeImage(heatMapImage, this.realWidth, this.realHeight);
       }
    }   
    
    ///////////////////////////////////////////////////////////
    //paint and importants methods
    ////////////////////////////////////////////////////////////
    
    @Override
    public void paint(Graphics g) {
    
        correctSize();    
        super.paint(g);
        
            //mapa de calor          
                Graphics2D g2d = (Graphics2D)g;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                this.opacityMap));    
                g.drawImage(this.heatMapImage, 0, 0, this);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                1));    

        correctSize();    
        this.fijationMap.resize(this.realWidth, this.realHeight);
        this.drawRangeLine((Graphics2D)g);
    }
    
    
    
    
    
    public void addVideo(Media media){
        
        Platform.setImplicitExit(false); 
        
        player = new MediaPlayer(media);
        MediaView mediaView = new MediaView(player);
                    
//        Scene scene =  new Scene(new Group(mediaView),this.getWidth(),this.getHeight());
        Scene scene =  new Scene(new Group(mediaView),1920,1080);
        this.setScene(scene);  
        this.setSize(this.getSize());
                    
        player.setVolume(0.5);//volumen
        player.setCycleCount(MediaPlayer.INDEFINITE);          
    
        mvw = mediaView.fitWidthProperty();
        mvh = mediaView.fitHeightProperty();
              
        mvw.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
        mvh.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height")); 
        
        mediaView.setPreserveRatio(true); 
        while (media.getWidth() == 0 || media.getHeight() == 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(JFXPanelStaticHeatmap.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("getting video dimensions: " +media.getWidth() + "x" + media.getHeight());
        }        
        this.originalWidth = media.getWidth();
        this.originalHeight = media.getHeight();
        this.propotionCase = this.getProportionCase(this.originalWidth, this.originalHeight);
        this.proportion = this.getProportionValue(this.originalWidth, this.originalHeight); 
        this.fijationMap = new FixationMap(this.originalWidth, this.originalHeight, Color.BLUE);
        
        this.markers = media.getMarkers();
        initSyncControl();       
        this.player.setVolume(0.0);
    }
    
    
    public void addData(GazeData data){
        
        if (data.state != GazeData.STATE_TRACKING_FAIL
                && data.state != GazeData.STATE_TRACKING_LOST
                && !(data.smoothedCoordinates.x == 0 && data.smoothedCoordinates.y == 0)
                && data.smoothedCoordinates.x > 0 
                && data.smoothedCoordinates.y > 0
                ) {        
        
          this.fijationMap.addData(data);
        
          Double actualX;
          Double actualY;
          
          actualX = (data.smoothedCoordinates.x/this.originalWidth) * this.realWidth;
          actualY = (data.smoothedCoordinates.y/this.originalHeight) * this.realHeight;

          if(this.heatMap!=null){
              this.heatMap.addPoint(actualX, actualY);
              
          }
        }
    }    
    
    
    public void addDataWithoutAois(GazeData data){
        this.fijationMap.addData(data);
    }     
        
    
    public void correctSize(){
    
        if(this.propotionCase == -1){
            Double changeValue = new Double(this.getHeight()) / this.originalHeight;
            this.realWidth =(int) (changeValue * this.originalWidth);
            this.realHeight = this.getHeight();            
        }
        
        if(this.propotionCase == 1){
            Double changeValue = new Double(this.getWidth()) / this.originalWidth;
            this.realHeight = (int) (changeValue * this.getHeight());
            this.realWidth = this.getWidth();
            this.realHeight = this.getHeight();
            this.heatMapImage = this.resizeImage(heatMapImage, this.realWidth, this.realHeight);        }

    }    
    
        
 /////////////////////////////////////////////
 ////////////////auxiliar methods
 //////////////////////////////////////////////   
     private int getProportionCase(int width, int height){
                
        if(width>height){return -1;}
        if(width==height){return 0;}
        if(width<height){return 1;}
        return -2;
    }
    
    private Double getProportionValue(int width, int height){
        
        Double w = new Double(width);
        Double h = new Double(height);
        
        return w/h;
    }         
    
    
    private void drawRangeLine(Graphics2D g){
        
        
        int startPoint = new Double((new Double(this.startHeatMap-this.offset)/(this.endTime-this.offset))*this.realWidth).intValue();
        int endPoint = new Double((new Double(this.endHeatMap-this.offset)/(this.endTime-this.offset))*this.realWidth).intValue();        
         
        g.setColor(Color.WHITE);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));    
        g.fillRect(startPoint, this.getSize().height-10, endPoint - startPoint, 12);
        g.setColor(Color.yellow);
        g.fillRect(startPoint, this.getSize().height-10, 10  , 12);
        g.fillRect(endPoint-10, this.getSize().height-10, 12  , 12);
        
    }

    
    
    ////////////////////////////////////////////////////////
    //events control/////////////////////////////////
    ///////////////////////////////////////////////////////7
    
    
    public void mapToFile(File outputFile){
    
        BufferedImage bimg = new BufferedImage(this.realWidth, this.realHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) bimg.getGraphics();
        super.paint(g);
        
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                this.opacityMap));    
                g.drawImage(this.heatMapImage, 0, 0, null);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                1));        
        
        try {
            ImageIO.write(bimg, "png", outputFile);
        } catch (IOException ex) {
            Logger.getLogger(JFXPanelStaticHeatmap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

/////////////////////////////////////////////////////////////////
/////////////////reproduction control
////////////////////////////////////////////////////////////////
    
    
    public void play(){
        this.playVideo();
    }
    
    public void playVideo(){
        this.player.play();
        this.isPlaying = true;
    }    
    
    public void pauseVideo(){
        this.player.pause();
        this.isPlaying = false;
    
    }    
    
    public void pause(){
        this.pauseVideo();
    }
    
    public void stop(){
        this.player.stop();
        this.reset();
    }
    
    public void seek(long millis){
        this.player.seek(Duration.millis(millis));
    }   
    
    public void seekVideo(long millis){
        this.player.seek(Duration.millis(millis));
    }       
    
    
    public void reset(){
        this.fijationMap.reset();
    }
    
   /////////////////////////////////////////
   ///////setters and getters
   //////////////////////////////////////
    
    public void setColorFijations(Color color){
        this.colorFijations = color;
        this.fijationMap.setColor(color);
    }
    
    public void setOpacityMaps(Double value){
        this.fijationMap.setOpacity(value);
    }

    public FixationMap getFijationMap() {
        return fijationMap;
    }
    
   
    public void setColorFixations(Color color){
        this.colorFijations = color;
    }
    
    
    public void setOffset(long offset){
        this.offset = offset;
    }
     
    
    public void setHeatMap(BufferedImage image){  
        this.heatMapImage = image;
    }

    public void setDataFromHeatMap(ArrayList<GazeData> dataFromHeatMap) {
        this.dataFromHeatMap = dataFromHeatMap;
    }
    
    
    public void setStartRange(long start){
        this.startHeatMap = start;
    }
    
    
    public void setEndRange(long end){
        this.endHeatMap = end;
    }
    
    public void setTimeRange(long start, long end){
        this.startHeatMap = start;
        this.endHeatMap = end;
    }
    
    public void updateHeatMap(){
    
        
            
         ArrayList<Point> points =  new ArrayList<Point>();
                        
         //binary search
        int foundIndex = -1 , max = this.dataFromHeatMap.size() - 1 , min = 0 , pivot;
        

        while (foundIndex == -1) {
           
            pivot= (max + min)/2;            
            
            if (this.dataFromHeatMap.get(pivot).timeStamp == this.startHeatMap||max==min) {
                foundIndex = max;
            } else {
                if(Math.abs(min-max)==1){
                    if(Math.abs(this.dataFromHeatMap.get(min).timeStamp-this.startHeatMap)>
                       Math.abs(this.dataFromHeatMap.get(max).timeStamp-this.startHeatMap)){
                        foundIndex = max;
                    }else{
                        foundIndex = min;
                    }
                }else{
                    if (this.dataFromHeatMap.get(pivot).timeStamp > this.startHeatMap) {
                        max= pivot;

                    } else {
                        if (this.dataFromHeatMap.get(pivot).timeStamp < this.startHeatMap) {
                            min = pivot;
                        }
                    }
                }
            }
        }
        
        int i = foundIndex;
        GazeData data = this.dataFromHeatMap.get(i) ;
        
        while(data.timeStamp<this.endHeatMap){
            
                    int x = (int)new Double((data.smoothedCoordinates.x/this.originalWidth)*this.realWidth).intValue();
                    int y = (int)new Double((data.smoothedCoordinates.y/this.originalHeight)*this.realHeight).intValue();            
                    if(x==0 && y==0 ){}else{
                    Point p = new Point(x, y);
                    points.add(p);}             
            
            i++;
            data = this.dataFromHeatMap.get(i) ;
        }           
 
            this.correctSize();
            HeatMap m = new HeatMap(points,new BufferedImage(this.realWidth, this.realHeight, BufferedImage.TYPE_INT_ARGB));
            this.heatMapImage = m.createHeatMapImage(this.multiplier);

    }
    
     private BufferedImage resizeImage(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        //g2d.dispose();
        return dimg;
    }   

    public void initSyncControl(){
        
    player.setOnMarker(new javafx.event.EventHandler<MediaMarkerEvent>() {
      @Override
      public void handle(final MediaMarkerEvent event) {
        Platform.runLater(new Runnable() {
          @Override public void run() {
              player.pause();
          }
        });
      }
    });
    
    }
    
    public void setNextPauseTime(Long millis){
        markers.put("nextPause", Duration.millis(millis));
    }
    
    public void playToLimit(Long millis){
        this.markers.clear();
        setNextPauseTime(millis);
        //if(player.getStatus().name().equals(Status.PLAYING.name())){
            this.player.play();
        //}
    }
    
    public void cleanLastPlayLimit(){
        this.markers.clear();
    }     
     
     
     
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
    
    public void setHeatmapOpacity(float f){
        this.opacityMap = f;
    }    
    
    public void setMultiplierHeatmap(float f){
        this.multiplier = f;
    }


    public void setMultiplierHeatmapAndUpdate(float f){
        this.multiplier = f;
        this.updateHeatMap();
    }    

    public int getRealWidth() {
        return realWidth;
    }

    public int getRealHeight() {
        return realHeight;
    }
     
    
    
    
}
