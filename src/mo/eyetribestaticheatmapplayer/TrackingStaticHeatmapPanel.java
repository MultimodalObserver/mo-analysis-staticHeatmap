
package mo.eyetribestaticheatmapplayer;

import java.awt.BorderLayout;
import javafx.scene.media.Media;
import java.awt.Color;
import java.io.FileNotFoundException;



public class TrackingStaticHeatmapPanel extends javax.swing.JPanel {


    public TrackingStaticHeatmapPanel() {
        initComponents();
                       
    }
    
    public void setupMedia(Media media) throws FileNotFoundException {
        
        this.jfxPanel = new JFXPanelStaticHeatmap();
        this.setLayout(new BorderLayout());
        this.add(jfxPanel,BorderLayout.CENTER);
        this.jfxPanel.setColorFixations(Color.BLUE);

        this.jfxPanel.addVideo(media); 
                                   
    }
    
    


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setPreferredSize(new java.awt.Dimension(1280, 800));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    
    public void play(){

    }
    
    public void playVideo(){
        this.jfxPanel.playVideo();
        play(); //borrar
    }
    
    public void pauseVideo(){
        this.jfxPanel.pause();
    }    
    
    public void stopVideo(){
        this.jfxPanel.stop();
    }    
    
    public void seekVideo(long time){
        this.jfxPanel.seek(time);
    }
    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
          this.jfxPanel.correctSize();
    }//GEN-LAST:event_formComponentResized

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        
    }//GEN-LAST:event_formMouseClicked
  
    
    public JFXPanelStaticHeatmap getFxPanel(){
        return this.jfxPanel;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    private JFXPanelStaticHeatmap jfxPanel;   
    
}
