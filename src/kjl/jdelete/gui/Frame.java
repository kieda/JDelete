package kjl.jdelete.gui;

import java.io.File;
import java.util.Arrays;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import kjl.jdelete.mechanics.DelList;
import kjl.jdelete.mechanics.DelQueue;
import org.filedrop.FileDrop;

/**
 * The gui/frame implementation for JDelete. I didn't feel like separating the
 * frontend and backend at the time very much so I guess I'm stuck with this.
 * Ok I guess
 * 
 * @author zkieda
 * @since when did I start commenting code?
 */
public class Frame extends JFrame {
    /**
     * should this program stop? If true, this program exits. 
     */
    private boolean stop = false;
    
    /**
     * a basic logger that logs information to the JTextArea at the bottom
     * of the panel
     */
    private SimpleLog logger = new SimpleLog() {
        @Override
        public void append(String s) {
            outputTextArea.append(s);
        }
        @Override
        public void clear() {
            outputTextArea.setText("");
        }
    };
    
    /**
     * the queue of things that we should delete. We send error messages about
     * deletions to the JTextArea
     */
    private final DelQueue delQueue = new DelQueue(logger);
    
    /**
     * the list of things that could possibly be deleted. This list is like the
     * 'recycling bin' - we have an option to remove an item on this list from
     * deletion, but deleting an item from this list prevents it from being 
     * recovered - it is in the queue for deletion.
     */
    private final DelList delList = new DelList(delQueue);
    
    /**
     * this is the list model we use to represent all of the items that are in
     * the list for deletion. Invariant : each element in {@code list} has a
     * representative element in {@code listModel}.
     */
    private final DefaultListModel listModel = new DefaultListModel();

    /**
     * a runnable used to repaint this frame.
     */
    private final Runnable painter = new Runnable() {
        @Override
        public void run() {
            try {
            while(!stop){
                repaint();
                Thread.currentThread().sleep(100);
            }
            } catch (InterruptedException e) {}
            delQueue.close();
        }
    };
    
    /**
     * the range model used for the progress bar
     */
    private final BoundedRangeModel progressModel = new DefaultBoundedRangeModel(){
        @Override
        public int getValue() {
            //represent the percentage of files we've deleted. 
            return (int)(100*delQueue.amountComplete());
        }
        @Override
        public boolean getValueIsAdjusting() {
            return false;
        }
    };
    
    /**
     * Adds a file for the list in the recycling bin. Updates the ui 
     * representation as well as the back-end. An item in the recycling
     * bin can subsequently be deleted permanently, or removed from the 
     * recycling bin. <br>
     * 
     * The recycling bin is backed by {@code delList}. 
     * @param f The file to put in the recycling bin
     */
    private void add(File f){
        delList.add(f);
        listModel.addElement(f);
    }
    
    /**
     * Removes a series of indices from the recycling bin. Updates the ui 
     * representation as well as the back-end representation. The indices of
     * elements in the recycling bin are updated as well, as the recycling bin
     * is represented as an array of items to be deleted. 
     * 
     * @param is For each {@code i} in {@code is}, the file in the recycling bin
     * at index {@code i} is removed from the recycling bin.
     */
    private void rem(int[] is) {
        //who the hell uses asserts these days?
        assert is != null;
        
        //sort the indices first for removal 
        Arrays.sort(is);
        
        //remove indices from list representation
        delList.rem(is);
        
        //remove indices from front end.
        for(int i = is.length-1; i >= 0; i--)
            listModel.remove(is[i]);
    }
    /**
     * Sends a series of files in the recycling bin to the queue for deletion.
     * Removes files from ui, and removes element from backend representation
     * of the recycle bin. 
     * 
     * @param is For each {@code i} in {@code is}, the file in the recycling bin
     * at index {@code i} is queued for permanent deletion.
     */
    private void del(int[] is) {
        Arrays.sort(is);
        delList.del(is);
        for(int i = is.length-1; i >= 0; i--)
            listModel.remove(is[i]);
    }
    
    /**
     * Sends all of the elements in the recycling bin to the queue for 
     * permanent deletion. Updates the ui as well. 
     */
    private void delAll() {
        delList.delAll();
        listModel.removeAllElements();
    }
    
    /**
     * closes this frame, and eventually all of the other relevant information.
     */
    private void close(){
        stop = true;
    }
    
    /**
     * sets the flags that should be used as command line arguments.
     */
    private void applyFlags(){
        delQueue.setFlags(flagTextField.getText());
    }
    
    /**
     * Creates new form Frame
     */
    public Frame() {
        initComponents();
        
        //we apply any default flags. 
        applyFlags();
        
        //we prepare the recycleBinList for drag n' drop
        new FileDrop(recycleBinList, new FileDrop.Listener() {
            @Override
            public void filesDropped(File[] files) {
                for (int i = 0; i < files.length; i++) {
                    add(files[i]);
                }
            }
        });
        
        //start the painter thread
        new Thread(painter).start();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        listScrollPane = new javax.swing.JScrollPane();
        recycleBinList = new javax.swing.JList();
        progressBar = new javax.swing.JProgressBar();
        outputScrollPane = new javax.swing.JScrollPane();
        outputTextArea = new javax.swing.JTextArea();
        outputLabel = new javax.swing.JLabel();
        flagTextField = new javax.swing.JTextField();
        buttonPanel = new javax.swing.JPanel();
        deleteButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        deleteAllButton = new javax.swing.JButton();
        flagsLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        recycleBinList.setModel(listModel);
        listScrollPane.setViewportView(recycleBinList);

        progressBar.setModel(progressModel);

        outputTextArea.setEditable(false);
        outputTextArea.setColumns(20);
        outputTextArea.setRows(5);
        outputScrollPane.setViewportView(outputTextArea);

        outputLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        outputLabel.setText("Output");

        flagTextField.setText("-p 32 -s -c");
        flagTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                flagTextFieldActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        removeButton.setText("Remove");
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        deleteAllButton.setText("Delete All");
        deleteAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteAllButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(deleteButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(removeButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(deleteAllButton)
                .addContainerGap())
        );
        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonPanelLayout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteButton)
                    .addComponent(removeButton)
                    .addComponent(deleteAllButton)))
        );

        flagsLabel.setText("Flags...");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(progressBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(listScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
                    .addComponent(outputScrollPane, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(outputLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(flagsLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(flagTextField)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(listScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(flagTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(flagsLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(outputLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(outputScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void flagTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_flagTextFieldActionPerformed
        applyFlags();
    }//GEN-LAST:event_flagTextFieldActionPerformed

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        rem(recycleBinList.getSelectedIndices());
    }//GEN-LAST:event_removeButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        applyFlags();
        del(recycleBinList.getSelectedIndices());
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void deleteAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteAllButtonActionPerformed
        applyFlags();
        delAll();
    }//GEN-LAST:event_deleteAllButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        close();
    }//GEN-LAST:event_formWindowClosing

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton deleteAllButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextField flagTextField;
    private javax.swing.JLabel flagsLabel;
    private javax.swing.JScrollPane listScrollPane;
    private javax.swing.JLabel outputLabel;
    private javax.swing.JScrollPane outputScrollPane;
    private javax.swing.JTextArea outputTextArea;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JList recycleBinList;
    private javax.swing.JButton removeButton;
    // End of variables declaration//GEN-END:variables
}
