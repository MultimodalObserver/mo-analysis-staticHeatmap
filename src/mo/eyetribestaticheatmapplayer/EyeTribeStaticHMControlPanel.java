/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mo.eyetribestaticheatmapplayer;

import com.theeyetribe.clientsdk.data.GazeData;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javafx.scene.media.Media;
import javax.swing.JOptionPane;
import static mo.analysis.NotesAnalysisPlugin.logger;
import org.joda.time.DateTime;

/**
 *
 * @author gustavo
 */
public class EyeTribeStaticHMControlPanel extends javax.swing.JPanel {

    int val;
    private long currentTime;
    private long starRangeTime;
    private long endRangeTime;
    private JFXPanelStaticHeatmap fixationPanel;
    private File outputDir;

    
    public EyeTribeStaticHMControlPanel(File dataFile, File mediaFile) throws FileNotFoundException {
 
         initComponents();
         this.currentTime = 0;
         this.starRangeTime = 0;
         this.endRangeTime = 0;
         Media media = new Media(mediaFile.toURI().toString());
         
         TrackingStaticHeatmapPanel videoPanel = (TrackingStaticHeatmapPanel)this.frontPanel;
         videoPanel.setupMedia(media);
         this.trackingPanel = (TrackingStaticHeatmapPanel)this.frontPanel;
         this.fixationPanel = this.trackingPanel.getFxPanel();
         
         /////////////////////////////////////////////////7         
         this.dataFile = dataFile;
        
    }    
    

    public EyeTribeStaticHMControlPanel(File dataFile, File mediaFile, File outputDir) throws FileNotFoundException {
 
         initComponents();
         this.currentTime = 0;
         this.starRangeTime = 0;
         this.endRangeTime = 0;
         Media media = new Media(mediaFile.toURI().toString());
         this.outputDir = outputDir;
         
         TrackingStaticHeatmapPanel videoPanel = (TrackingStaticHeatmapPanel)this.frontPanel;
         videoPanel.setupMedia(media);
         this.trackingPanel = (TrackingStaticHeatmapPanel)this.frontPanel;
         this.fixationPanel = this.trackingPanel.getFxPanel();
         
         /////////////////////////////////////////////////7        
         this.dataFile = dataFile;
         
    }  


    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        frontPanel = new mo.eyetribestaticheatmapplayer.TrackingStaticHeatmapPanel();
        opacityChooser = new javax.swing.JSlider();
        selectStartRangeButton = new javax.swing.JButton();
        selectEndRangeButton = new javax.swing.JButton();
        exportMapButton = new javax.swing.JButton();
        makeHeatMapButton = new javax.swing.JButton();
        multiplierChooser = new javax.swing.JSlider();
        opacityLabl = new javax.swing.JLabel();
        multiplierLabel = new javax.swing.JLabel();

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        frontPanel.setMinimumSize(new java.awt.Dimension(200, 200));
        frontPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                frontPanelComponentResized(evt);
            }
        });

        javax.swing.GroupLayout frontPanelLayout = new javax.swing.GroupLayout(frontPanel);
        frontPanel.setLayout(frontPanelLayout);
        frontPanelLayout.setHorizontalGroup(
            frontPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 811, Short.MAX_VALUE)
        );
        frontPanelLayout.setVerticalGroup(
            frontPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 335, Short.MAX_VALUE)
        );

        opacityChooser.setOpaque(false);
        opacityChooser.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                opacityChooserMouseDragged(evt);
            }
        });

        selectStartRangeButton.setText("<");
        selectStartRangeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectStartRangeButtonMouseClicked(evt);
            }
        });

        selectEndRangeButton.setText(">");
        selectEndRangeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectEndRangeButtonMouseClicked(evt);
            }
        });

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("i18n/mo/analysis/staticHeatmapPlugin/staticHeatmapControlPanel"); // NOI18N
        exportMapButton.setText(bundle.getString("saveImage")); // NOI18N
        exportMapButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exportMapButtonMouseClicked(evt);
            }
        });

        makeHeatMapButton.setText(bundle.getString("makeMap")); // NOI18N
        makeHeatMapButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                makeHeatMapButtonMouseClicked(evt);
            }
        });

        multiplierChooser.setOpaque(false);
        multiplierChooser.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                multiplierChooserMouseDragged(evt);
            }
        });
        multiplierChooser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                multiplierChooserMouseReleased(evt);
            }
        });

        opacityLabl.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        opacityLabl.setText(bundle.getString("transparency")); // NOI18N

        multiplierLabel.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        multiplierLabel.setText(bundle.getString("multiplier")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(selectStartRangeButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(selectEndRangeButton)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(opacityLabl)
                    .addComponent(multiplierLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(opacityChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(multiplierChooser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(81, 81, 81)
                .addComponent(makeHeatMapButton)
                .addGap(18, 18, 18)
                .addComponent(exportMapButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(frontPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(frontPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(selectStartRangeButton)
                                    .addComponent(selectEndRangeButton))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(exportMapButton)
                                    .addComponent(makeHeatMapButton))
                                .addGap(25, 25, 25))))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(opacityChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(multiplierChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(opacityLabl)
                                .addGap(18, 18, 18)
                                .addComponent(multiplierLabel))))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void frontPanelComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_frontPanelComponentResized

    }//GEN-LAST:event_frontPanelComponentResized

    private void opacityChooserMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_opacityChooserMouseDragged
        //this.trackingPanel.getFxPanel().setOpacityMaps(new Double(this.opacityChooser.getValue())/100);
        this.trackingPanel.getFxPanel().setHeatmapOpacity((float)this.opacityChooser.getValue()/100);
       // this.trackingPanel.getFxPanel().getFijationMap().cleanMap();
      //  this.trackingPanel.getFxPanel().getFijationMap().repaint();
        this.repaint();
    }//GEN-LAST:event_opacityChooserMouseDragged

    private void selectStartRangeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectStartRangeButtonMouseClicked
        this.starRangeTime = this.currentTime;
        this.fixationPanel.setStartRange(starRangeTime);
        this.repaint();
    }//GEN-LAST:event_selectStartRangeButtonMouseClicked

    private void selectEndRangeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectEndRangeButtonMouseClicked
        this.endRangeTime = this.currentTime;
        this.fixationPanel.setEndRange(endRangeTime);
        this.repaint();

    }//GEN-LAST:event_selectEndRangeButtonMouseClicked

    private void makeHeatMapButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_makeHeatMapButtonMouseClicked
        this.fixationPanel.setTimeRange(starRangeTime, endRangeTime);
        this.fixationPanel.updateHeatMap();
        this.repaint();
        
    }//GEN-LAST:event_makeHeatMapButtonMouseClicked

    private void exportMapButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exportMapButtonMouseClicked
        String now = DateTime.now().toString();
        File outputFile = new File(outputDir.getPath(), "heatMap_"+ now.substring(0, now.indexOf("T"))+"_"+".png");
        this.fixationPanel.mapToFile(outputFile);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("i18n/mo/analysis/staticHeatmapPlugin/staticHeatmapPluginDialogs");
        JOptionPane.showMessageDialog(frontPanel, bundle.getString("imageSaved") + outputFile.getPath());
        
    }//GEN-LAST:event_exportMapButtonMouseClicked

    private void multiplierChooserMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_multiplierChooserMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_multiplierChooserMouseDragged

    private void multiplierChooserMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_multiplierChooserMouseReleased
        //this.trackingPanel.getFxPanel().setOpacityMaps(new Double(this.opacityChooser.getValue())/100);
        this.trackingPanel.getFxPanel().setMultiplierHeatmapAndUpdate(((float)this.multiplierChooser.getValue()/100)*1.7f);
       // this.trackingPanel.getFxPanel().getFijationMap().cleanMap();
      //  this.trackingPanel.getFxPanel().getFijationMap().repaint();
        this.repaint();        
        
    }//GEN-LAST:event_multiplierChooserMouseReleased

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        int realWidth = this.trackingPanel.getFxPanel().getRealWidth();
        int realHeight = this.trackingPanel.getFxPanel().getRealHeight();
        //this.trackingPanel.setSize(realWidth, realHeight);
        //this.trackingPanel.getFxPanel().setSize(realWidth, realHeight);
        //this.trackingPanel.setPreferredSize(new Dimension(realWidth, realHeight));
    }//GEN-LAST:event_formComponentResized

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
    
    public void setOffset(long offset){
        this.trackingPanel.getFxPanel().setOffset(offset);
    }
    
    public void setHeatMap(BufferedImage image){
        this.trackingPanel.getFxPanel().setHeatMap(image);
    }    
    
    public void playVideo(){
        this.trackingPanel.getFxPanel().playVideo();
    }
    
    public void playData(long time){
    
    }
    
    public void addData(GazeData data){
        this.trackingPanel.getFxPanel().addData(data);
    }
    
    public void addDataWithoutAois(GazeData data){
        this.trackingPanel.getFxPanel().addDataWithoutAois(data);
    }    
    
    
    public void pauseVideo(){
            this.trackingPanel.getFxPanel().pauseVideo();
    }
    
    public void setDataFromHeatMap(ArrayList<GazeData> data){
        this.fixationPanel.setDataFromHeatMap(data);
    }
    
    public void pauseData(){

    }
    
    public void seekVideo(long time){
        this.fixationPanel.seek(time);
    }
    
    public void seekData(long time){
    
    }  
    
    public void stop(){
        this.trackingPanel.getFxPanel().stop();
    }
    
    public void reset(){
        this.trackingPanel.getFxPanel().reset();
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }
    
    public void setTime(long start, long end){
        this.fixationPanel.setStartTime(start);
        this.fixationPanel.setEndTime(end);
        this.endRangeTime = end;
        this.starRangeTime = start;
        this.fixationPanel.setTimeRange(start, end);
    }
    
    public void playWhitLimit(Long limit){
        this.trackingPanel.getFxPanel().playToLimit(limit);
    }    
    
    public void cleanLastPlayLimit(){
        this.trackingPanel.getFxPanel().cleanLastPlayLimit();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton exportMapButton;
    private javax.swing.JPanel frontPanel;
    private javax.swing.JButton makeHeatMapButton;
    private javax.swing.JSlider multiplierChooser;
    private javax.swing.JLabel multiplierLabel;
    private javax.swing.JSlider opacityChooser;
    private javax.swing.JLabel opacityLabl;
    private javax.swing.JButton selectEndRangeButton;
    private javax.swing.JButton selectStartRangeButton;
    // End of variables declaration//GEN-END:variables
    private boolean playing;
    private TrackingStaticHeatmapPanel trackingPanel;
    private File dataFile;

}


