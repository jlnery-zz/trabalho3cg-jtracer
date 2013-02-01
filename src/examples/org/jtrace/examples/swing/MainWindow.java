package org.jtrace.examples.swing;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jtrace.Tracer;
import org.jtrace.ViewPlane;
import org.jtrace.shader.Shaders;
import org.jtrace.swing.TracerPanel;

public class MainWindow extends JFrame {

  private static final long serialVersionUID = 8122517505454630633L;
  public static TracerPanel tracePanel;

  public MainWindow() {
    setSize(700, 700);
    setTitle("JTrace");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    init();
  }

  private void init() {
    getContentPane().setLayout(null);
    JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    mainPanel.setBounds(0, 150, 700, 628);
    tracePanel = createTracerPanel();
    mainPanel.add(tracePanel);    
    getContentPane().add(mainPanel);
  }

  private TracerPanel createTracerPanel() {
	Tracer tracer = new Tracer();
	
	tracer.addShaders(Shaders.ambientShader(), Shaders.diffuseShader());
	  
    return new TracerPanel(tracer, App.createScene(), new ViewPlane(500, 500), 500, 500);
  }
  
}