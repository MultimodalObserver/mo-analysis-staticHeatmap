/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mo.eyetribestaticheatmapplayer;

import com.theeyetribe.clientsdk.data.GazeData;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import static mo.analysis.NotesAnalysisPlugin.logger;
import mo.core.ui.dockables.DockableElement;
import mo.core.ui.dockables.DockablesRegistry;
import mo.visualization.Playable;


public class EyeTribeStaticHeatmapPlayer implements Playable{

    private long start;
    private long end;
    private EyeTribeStaticHMControlPanel mainPanel;
    private File dataFile;
    private File mediaFile;
    private ArrayList<GazeData> data;
    private GazeData current;
    private int currentCount;
    private boolean videoIsPlaying;
    private boolean isFinalized;
    private long offset;
    
    private BufferedImage heatMap;
    private long updateHeatMapTime;
    private boolean heatMapUpdating;
    private Timer timer;
    private Boolean isSync;
    private ArrayList<Long> frames;
    private int frameCount;
    
    public EyeTribeStaticHeatmapPlayer(File dataFile, File mediaFile, File outputFolder){
        
        this.dataFile = dataFile;
        this.mediaFile = mediaFile;
        
        this.data = readData(dataFile);
        this.start = this.data.get(0).timeStamp;
        this.end = this.data.get(this.data.size()-1).timeStamp;       
        this.current = this.data.get(0);
        this.currentCount = 0;
        this.videoIsPlaying = false;
        this.isFinalized = false;
        this.offset = -1;
        this.isSync = false;
        
        this.heatMapUpdating = false;
        this.frameCount = 0;
        
        String framesFileName = mediaFile.getName().substring(0,mediaFile.getName().lastIndexOf(".")) + "-frames.txt" ;
        
        File framesFile = new File(mediaFile.getParentFile(), framesFileName);
        if(framesFile!=null){
            if(framesFile.exists()){
                this.frames = this.loadFrames(framesFile);
            }
        }        
        
        
        EyeTribeStaticHMControlPanel panel;
        try {
             
            panel = new EyeTribeStaticHMControlPanel(dataFile, mediaFile, outputFolder);
            this.mainPanel = panel;
            /*JFrame mainWindow=new JFrame("Menu sample");
            mainWindow.setLayout(new BorderLayout());
            mainWindow.add(panel);
            mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainWindow.setVisible(true);
            mainWindow.pack();   
            
            */
                SwingUtilities.invokeLater(() -> {
                    try {
                        DockableElement e = new DockableElement();
                        e.add(panel);
                        
                        DockablesRegistry.getInstance().addAppWideDockable(e);
                        
                    } catch (Exception ex) {
                        logger.log(Level.INFO, null, ex);
                    }
                });    
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EyeTribeStaticHeatmapPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
          
        t=0;
        
        
        ArrayList<Point> points =  new ArrayList<Point>();
        
        for(GazeData g : this.data){
            
            //final int x = (int)new Double((g.smoothedCoordinates.x/1920)*1920).intValue();
            //final int y = (int)new Double((g.smoothedCoordinates.y/1080)*1200).intValue();
            final int x = (int)new Double((g.smoothedCoordinates.x/1920)*1920).intValue();
            final int y = (int)new Double((g.smoothedCoordinates.y/1080)*1200).intValue();
            
            if(x==0 && y==0 ){}else{
            final Point p = new Point(x, y);
            points.add(p);}            
        }
        
      this.mainPanel.setDataFromHeatMap(data);
      this.mainPanel.setOffset(this.start);
      this.offset = this.start;
      this.mainPanel.setTime(start, end);
      
    }
    
    
    private ArrayList<GazeData> readData(File file){
    
        FileReader fr;
        BufferedReader br;  
        GazeData aux;
        ArrayList<GazeData> data =  new ArrayList<GazeData>();
        
        try {
            
            fr = new FileReader(file);
            br = new BufferedReader(fr);    
            String line;
            
            line = br.readLine();
            
            while(line!=null){

                 data.add(this.parseDataFromLine(line));
                 line = br.readLine();
            }
            
            return data;
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EyeTribeStaticHeatmapPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EyeTribeStaticHeatmapPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return null;
    }
    
    
    private GazeData parseDataFromLine(String line) {
        String[] parts = line.split(" ");
        GazeData data = new GazeData();
        for (String part : parts) {
            
            try {

                String[] keyNValue = part.split(":");
                String k = keyNValue[0];
                String v = keyNValue[1];

                switch (k) {
                    case "t":
                        data.timeStamp = Long.parseLong(v);
                        break;
                    case "fx":
                        data.isFixated = Boolean.parseBoolean(v);
                        break;
                    case "sm":
                        data.smoothedCoordinates.x = Double.parseDouble(v.split(";")[0]);
                        data.smoothedCoordinates.y = Double.parseDouble(v.split(";")[1]);
                        break;
                    case "rw":
                        data.rawCoordinates.x = Double.parseDouble(v.split(";")[0]);
                        data.rawCoordinates.y = Double.parseDouble(v.split(";")[1]);
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                logger.log(
                        Level.WARNING,
                        "Error reading part <{0}> line <{1}>:{2}",
                        new Object[]{part, line, e});
            }
        }

        return data;
    }    
    
    
    @Override
    public long getStart() {
        return start;
    }

    @Override
    public long getEnd() {
        return end;
    }

    @Override
    public void play(long millis) {
        
        if(this.offset<0){
            this.offset = millis;
            this.mainPanel.setOffset(offset);
        }
        
        
        if(!isSync){
            if(!this.videoIsPlaying){
                this.mainPanel.playVideo();
                this.videoIsPlaying = true;
                }
            }
        else{
            if(frameCount<0){frameCount = this.getActualFrameCount(millis);}
            if(frameCount<frames.size()){
                if(millis==frames.get(frameCount)){
                    this.mainPanel.playWhitLimit(frames.get(frameCount)-offset);
                    frameCount++;
                }
            } 
        }
        
        if (!this.isFinalized) {
            while (millis >= this.current.timeStamp) {

                this.currentCount++;
                if(this.currentCount< this.data.size()){
                    this.current = this.data.get(this.currentCount);
                    this.mainPanel.addData(current);
                    
                }else{
                //esto es para salir del while sin utilizar un break
                  millis=0;
                }
            }
        } else {
           this.isFinalized = false;
           play(this.start);
        } 
        
        
        mainPanel.setCurrentTime(millis);
/*        
        if((millis-this.offset)%this.updateHeatMapTime==0){
        
            JOptionPane.showMessageDialog(null, "ventana") ;
        }
  */      

    }

    @Override
    public void pause() {
        if(this.videoIsPlaying&!this.isSync){
            this.mainPanel.pauseVideo();
            this.videoIsPlaying = false;
        }
        this.mainPanel.setCurrentTime(this.current.timeStamp);
    }

    @Override
    public void seek(long millis) {
        /*this.mainPanel.reset();
        this.current= this.getCompatibleData(data, millis);
        this.mainPanel.setCurrentTime(millis);
        this.mainPanel.seekVideo(millis-this.offset);
        this.mainPanel.setCurrentTime(millis);*/
        this.mainPanel.reset();
        this.current= this.getCompatibleData(data, millis);
        if(!this.videoIsPlaying){
            this.mainPanel.playVideo();
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(EyeTribeStaticHeatmapPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.mainPanel.pauseVideo();        
        }
        if(isSync){
            this.mainPanel.cleanLastPlayLimit();
            this.frameCount = this.getActualFrameCount(millis);
        }
        this.mainPanel.seekVideo(millis-offset);
        this.mainPanel.setCurrentTime(millis);

    }

    @Override
    public void stop() {
        this.mainPanel.stop();
        this.videoIsPlaying = false;
        this.isFinalized = true;
        this.currentCount = 0;
        this.current = this.data.get(currentCount);
        this.frameCount = 0;
        if(this.isSync){this.mainPanel.cleanLastPlayLimit();}        
    }
    
    ////////////////////////////////7
    private long t;
    
    public long getT(){
        return t;
    }
    
    public void sumT(long i){
        t = t + i ;
    }
    
    public GazeData getCompatibleData(ArrayList<GazeData> data, long millis) {

        for (int i = 0; i < data.size(); i++) {

            this.mainPanel.addDataWithoutAois(data.get(i));
            if (data.get(i).timeStamp >= millis) {
                this.currentCount = i;
                return data.get(i);
            }
        }

        return null;
    }    

    @Override
    public void sync(boolean bln) {
        this.isSync = bln;
        if(!isSync){
            this.frameCount = -1;
            this.mainPanel.cleanLastPlayLimit();            
            /*if(this.videoIsPlaying){
                this.mainPanel.playVideo();
            }*/
        } 
    }
    
    private ArrayList<Long> loadFrames(File file){
        
        ArrayList<Long> framesReads =  new ArrayList<Long>();
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            
            String line = reader.readLine();
            
            while(line!=null){
                
                framesReads.add(Long.parseLong(line));
                line = reader.readLine();
            }
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EyeTribeStaticHeatmapPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EyeTribeStaticHeatmapPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return framesReads;
    }
    
    public int getActualFrameCount(Long currentTime){
        int i = 0;
        for(Long frame : frames){
            if(currentTime<=frame){
                return i;
            }
            i++;
        }
        return i;
    }    
        
    
    
}
