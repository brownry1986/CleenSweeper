package com.sweeper.simulator.ui;

import com.sweeper.exceptions.XmlProcessingException;
import com.sweeper.hardware.controller.SimulatorController;
import com.sweeper.hardware.floorplan.FloorCell;
import com.sweeper.hardware.read.ParseFloorPlanXml;
import com.sweeper.hardware.state.SweeperState;
import com.sweeper.hardware.state.SweeperStateActions;
import com.sweeper.simulator.ui.util.DrawFloorPlan;
import com.sweeper.simulator.ui.util.FloorPlanCellColor;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Zach
 * This GUI has 3 buttons. 1 for loading new floor plans, 1 for starting the simulation, and 1 for stopping the simulation.
 * It also will draw out the floorplan from the provided xml file.
 */

public class SimulatorGUI extends JFrame implements SimulatorUI {

	private JPanel basePanel = new JPanel();
	private JPanel floorPlanPanel;

    private JButton runSimButton;
	private JButton stopSimButton;
	private JButton saveLogButton;
	private JButton saveFloorPlanButton;
	
	private JLabel emptyMeLabel;
	private JLabel batteryLevelLabel;
	private JLabel remainingRoomInDirtContainerLabel;
	
	private Point prevLocation;
	private Point curLocation;
	private Map<Point, JPanel> gridFloorCells;
	
	private SimulatorController simulatorController; 
	
	private JLabel sweeperImageLabel = new JLabel (new ImageIcon(this.getClass().getResource("/img/Sweeper.png")));
	private JLabel sweeperCleaningImagelabel = new JLabel(new ImageIcon(this.getClass().getResource("/img/Sweeper_Cleaning.png")));
		
	
	public SimulatorGUI() {
		buildUI();
	}
	
	private void buildUI() {
		//Setup the JFrame
		setTitle("CleanSweeper Simulator v1.0");
		setBounds(100, 100, 1000, 700);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		WindowListener exitListener = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e){
				exit();
			}
		};
		addWindowListener(exitListener);
		
		//Setup the menu bar
		setJMenuBar(createMenuBar());
		
		//Create the default, empty panel for the floor plan
        JPanel buttonPanel = createButtonPanel();
		
		floorPlanPanel = createInitialFloorPlanPanel();

		basePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		basePanel.setLayout(new BorderLayout());
		setContentPane(basePanel);
		
		basePanel.add(buttonPanel, BorderLayout.SOUTH);
		basePanel.add(floorPlanPanel, BorderLayout.CENTER);
	}
	
	public void runUI(){
		if (simulatorController == null) {
			JOptionPane.showMessageDialog(null, "The Simulator Controller has not been set. Application will exit now.", "Controller Error", JOptionPane.ERROR_MESSAGE);
		} else {
			//set the default delay to 0.25 seconds
			simulatorController.setDelay(0.25d);
			setVisible(true);
		}
		
	}
	
	
	/**
	 * This method will open a FileChooser and get an xml file. Then invokes the drawFloorPlan method
	 */
	private void getFloorPlanFile() {
		Map<Point, FloorCell> floorPlan;
		floorPlanPanel.removeAll();
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(
			new FileFilter() {
				@Override
				public boolean accept(File f){
					if (f.isDirectory()) {
						return true;
					}
					final String name = f.getName();
					return name.endsWith(".xml");
				}
			
				@Override
				public String getDescription() {
					return "*.xml";
				}
			}
		);
		fileChooser.setAcceptAllFileFilterUsed(false);
		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			//TODO set the floor plan using the controller methods. ALso the errors should not be surpressed in underlying classes.
			// I need to them populate up here so a dialog can be displayed.
			try {
				floorPlan = ParseFloorPlanXml.getFloorCellPlan(fileChooser.getSelectedFile());
				simulatorController.setFloorPlan(floorPlan);
				gridFloorCells = drawFloorPlan(floorPlan);
			} catch (IOException ioe) {
				JOptionPane.showMessageDialog(null, "There was an Input/Output issue with the xml file.\n" + ioe.getMessage(), "IO Error", JOptionPane.ERROR_MESSAGE);
				//TODO log the error
			} catch (XmlProcessingException xpe) {
				JOptionPane.showMessageDialog(null, "There was a problem parsing the xml file.\n" + xpe.getCause().getMessage(), "XML Parsing Error\n", JOptionPane.ERROR_MESSAGE);
				//TODO log the error
			}
			
		}
		
	}
	
	/**
	 * This method will build GUI components to represent the floor plan based on what is in the static class SweeperState.
	 * It leverages off a utility class to build components from and arranges them into a grid on the floorPlanPanel.
	 */
	private Map<Point, JPanel> drawFloorPlan(Map<Point, FloorCell> floorPlan) {
		Map<Point, JPanel> gridComponents;
		basePanel.remove(floorPlanPanel);
		gridComponents = DrawFloorPlan.draw(floorPlan, floorPlanPanel);
		basePanel.add(floorPlanPanel, BorderLayout.CENTER);
		if (gridComponents != null) {
			runSimButton.setEnabled(true);
			//set the icon at the initial location
			curLocation = new Point(SweeperState.getXLocation(), SweeperState.getYLocation());
			prevLocation = curLocation;
		}
		refreshGUI();
		return gridComponents;
	}
	
	private void startSimulation() {
		runSimButton.setEnabled(false);
		stopSimButton.setEnabled(true);
		saveLogButton.setEnabled(true);
		saveFloorPlanButton.setEnabled(true);
		simulatorController.start();
	}
	
	private void stopSimulation() {
		stopSimButton.setEnabled(false);
		simulatorController.stop();	
	}
	
	
	private void exit() {
		stopSimulation();
		System.exit(0);
	}
	
	private void saveSweeperLog() {
		List<String> logList = simulatorController.getSweeperLog();
		//if the size is 0, then open a popup to say that there is no log.
		if (logList.size() == 0) {
			JOptionPane.showMessageDialog(null, "The log is empty", "Empty Log Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		final JFileChooser fileChooser = new JFileChooser();
		if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			File saveFile = fileChooser.getSelectedFile();
			BufferedWriter writer = null;
			try {
				writer = new BufferedWriter(new FileWriter(saveFile));
				String lineBreak = System.getProperty("line.separator");
				for (String logString : logList){
					writer.write(logString);
					writer.write(lineBreak);
				}
			} catch (IOException ioe) {
				JOptionPane.showMessageDialog(null, "Unable to save file: " + ioe.getMessage(), "File Save Error", JOptionPane.ERROR_MESSAGE);
			} finally {
				try {
					writer.close();
				} catch (IOException ioe){}
			}

		}

	}
	
	private void saveFloorPlan() {
		List<String> floorPlanList = simulatorController.getSweeperFloorPlan();
		//if the size is 0, then open a popup to say that there is no log.
		if (floorPlanList.size() == 0) {
			JOptionPane.showMessageDialog(null, "The floor plan is empty", "Empty Floor Plan Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		final JFileChooser fileChooser = new JFileChooser();
		if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			File saveFile = fileChooser.getSelectedFile();
			BufferedWriter writer = null;
			try {
				writer = new BufferedWriter(new FileWriter(saveFile));
				String lineBreak = System.getProperty("line.separator");
				for (String floorPlanString : floorPlanList){
					writer.write(floorPlanString);
					writer.write(lineBreak);
				}
			} catch (IOException ioe) {
				JOptionPane.showMessageDialog(null, "Unable to save file: " + ioe.getMessage(), "File Save Error", JOptionPane.ERROR_MESSAGE);
			} finally {
				try {
					writer.close();
				} catch (IOException ioe){}
			}

		}

	}
	
	private void setSimulationDelay(double delay) {
		simulatorController.setDelay(delay);
		
	}
	
	public void setSimulatorController(SimulatorController simulatorController){
		this.simulatorController = simulatorController;
	}
	
	
	public void notifyUpdate(SweeperStateActions action){
		if (gridFloorCells == null){
			return;
		}
		if (action == SweeperStateActions.MOVEMENT) {
			prevLocation = curLocation;
			curLocation = new Point(SweeperState.getXLocation(), SweeperState.getYLocation());
			gridFloorCells.get(prevLocation).remove(sweeperImageLabel);
			gridFloorCells.get(curLocation).add(sweeperImageLabel);
			refreshGUI();
		} else if (action == SweeperStateActions.BATTERY) {
			this.batteryLevelLabel.setText("Battery: " + SweeperState.getBatteryPowerUnits());
		} else if (action == SweeperStateActions.DIRT_BIN) {
            this.emptyMeLabel.setText("");
            this.remainingRoomInDirtContainerLabel.setText("Room in dirt container: " + SweeperState.getRemainingDirtUnits());
		} else if (action == SweeperStateActions.CLEAN) {
			if (!SweeperState.isDirty()){
				gridFloorCells.get(curLocation).setBackground(FloorPlanCellColor.CLEAN_CELL);
			}
			gridFloorCells.get(curLocation).remove(sweeperImageLabel);
			gridFloorCells.get(curLocation).add(sweeperCleaningImagelabel);
			refreshGUI();
			try {
				Thread.sleep(100);
			} catch (InterruptedException ie) {
                throw new RuntimeException(ie.getMessage(), ie);
            }
            gridFloorCells.get(curLocation).remove(sweeperCleaningImagelabel);
			gridFloorCells.get(curLocation).add(sweeperImageLabel);
			refreshGUI();
		} else if (action == SweeperStateActions.EMPTY_ME) {
			//TODO possibly add more code to make this an interactive feature.
			this.emptyMeLabel.setText("Empty Me Activated");
		}		
	}
	
	
	//Builder methods
	
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		//create the menu file
		JMenu menuFile = new JMenu("File");
		menuFile.setMnemonic('f');
		
		//create the menu file elements
		JMenuItem newFloorPlanItem = new JMenuItem("New Floor Plan");
		newFloorPlanItem.setMnemonic('n');
		newFloorPlanItem.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e){
					getFloorPlanFile();
				}
			
			}
		);
		
		//create a sub menu to set the delay
		JMenu simDelaySubMenu = new JMenu("Simulation Delay");
		simDelaySubMenu.setMnemonic('d');
		//sub menu items
		ButtonGroup delayGroup = new ButtonGroup(); 
		
		JRadioButtonMenuItem oneSecDelayMenuItem = new JRadioButtonMenuItem(new RadioDelayAction(".25 seconds"));
		oneSecDelayMenuItem.setActionCommand("0.25");
		oneSecDelayMenuItem.setSelected(true);
		JRadioButtonMenuItem twoSecDelayMenuItem = new JRadioButtonMenuItem(new RadioDelayAction(".5 seconds"));
		twoSecDelayMenuItem.setActionCommand("0.50");
		JRadioButtonMenuItem threeSecDelayMenuItem = new JRadioButtonMenuItem(new RadioDelayAction(".75 seconds"));
		threeSecDelayMenuItem.setActionCommand("0.75");
		JRadioButtonMenuItem fourSecDelayMenuItem = new JRadioButtonMenuItem(new RadioDelayAction("1 second"));
		fourSecDelayMenuItem.setActionCommand("1");
		JRadioButtonMenuItem fiveSecDelayMenuItem = new JRadioButtonMenuItem(new RadioDelayAction("2 seconds"));
		fiveSecDelayMenuItem.setActionCommand("2");
		
		delayGroup.add(oneSecDelayMenuItem);
		delayGroup.add(twoSecDelayMenuItem);
		delayGroup.add(threeSecDelayMenuItem);
		delayGroup.add(fourSecDelayMenuItem);
		delayGroup.add(fiveSecDelayMenuItem);
		
		simDelaySubMenu.add(oneSecDelayMenuItem);
		simDelaySubMenu.add(twoSecDelayMenuItem);
		simDelaySubMenu.add(threeSecDelayMenuItem);
		simDelaySubMenu.add(fourSecDelayMenuItem);
		simDelaySubMenu.add(fiveSecDelayMenuItem);
		

		
		//Exit menu item
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic('x');
		exitItem.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e){
					exit();
				}
			}
		);

		
		//add all the menu items to the menu.
		menuFile.add(newFloorPlanItem);
		menuFile.add(simDelaySubMenu);
		menuFile.add(exitItem);
		menuBar.add(menuFile);
		
		return menuBar;
	}
	
	private JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel();
		
		runSimButton = new JButton("Run Simulation");
		runSimButton.setMnemonic('r');
		runSimButton.setEnabled(false);
		runSimButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						startSimulation();
					}
				}
			);
		
		stopSimButton = new JButton("Stop Simulation");
		stopSimButton.setMnemonic('p');
		stopSimButton.setEnabled(false);
		stopSimButton.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					stopSimulation();
				}
			}
		);
		
		saveLogButton = new JButton("Save Log File");
		saveLogButton.setMnemonic('s');
		saveLogButton.setEnabled(false);
		saveLogButton.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					saveSweeperLog();
				}
			}
		);	
		saveFloorPlanButton = new JButton("Save Floor Plan File");
		saveFloorPlanButton.setMnemonic('l');
		saveFloorPlanButton.setEnabled(false);
		saveFloorPlanButton.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					saveFloorPlan();
				}
			}
		);
		
		batteryLevelLabel = new JLabel("Battery level: " + SweeperState.getBatteryPowerUnits());
	  	remainingRoomInDirtContainerLabel = new JLabel("Room in dirt container: " + SweeperState.getRemainingDirtUnits());
		emptyMeLabel = new JLabel();
	  	
	  	buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
	  	buttonPanel.add(saveLogButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(20, 20)));
	  	buttonPanel.add(saveFloorPlanButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(20, 20)));
	  	buttonPanel.add(emptyMeLabel);
		buttonPanel.add(Box.createRigidArea(new Dimension(20, 20)));
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(batteryLevelLabel);
		buttonPanel.add(Box.createRigidArea(new Dimension(20, 20)));
		buttonPanel.add(remainingRoomInDirtContainerLabel);
		buttonPanel.add(Box.createRigidArea(new Dimension(20, 20)));
		buttonPanel.add(runSimButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(20, 20)));
		buttonPanel.add(stopSimButton);
		return buttonPanel;
	}

	private JPanel createInitialFloorPlanPanel() {
		JPanel initialFloorPlanPanel = new JPanel();
		initialFloorPlanPanel.setBackground(Color.LIGHT_GRAY);
		return initialFloorPlanPanel;
	}
	
	private void refreshGUI() {
		basePanel.revalidate();
		basePanel.repaint();
	}
	
	private class RadioDelayAction extends AbstractAction {
		public RadioDelayAction (String name) {
			super(name);
		}
				
		public void actionPerformed(ActionEvent e){
			setSimulationDelay(Double.parseDouble(e.getActionCommand()));
		}
	}
	
}
