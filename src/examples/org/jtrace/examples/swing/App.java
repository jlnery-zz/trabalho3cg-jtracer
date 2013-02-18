package org.jtrace.examples.swing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.media.j3d.BoundingBox;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.vecmath.Point3d;

import org.jtrace.Material;
import org.jtrace.Materials;
import org.jtrace.Scene;
import org.jtrace.cameras.Camera;
import org.jtrace.cameras.OrthogonalCamera;
import org.jtrace.cameras.PinHoleCamera;
import org.jtrace.examples.io.SimplePLYExample;
import org.jtrace.geometry.Plane;
import org.jtrace.geometry.Triangle;
import org.jtrace.interceptor.TextureInteceptor;
import org.jtrace.io.PlyReader;
import org.jtrace.lights.Light;
import org.jtrace.primitives.ColorRGB;
import org.jtrace.primitives.Point3D;
import org.jtrace.primitives.ReflectanceCoefficient;
import org.jtrace.primitives.Vector3D;

public class App {

	private static MainWindow window;
	private static JTextField tfTexturePly;
	private static JTextField tfPly;
	private static List<Triangle> triangles;
	private static Scene scene;
	private static JComboBox cbCamera;
	private static JComboBox cbLight;
	private static JComboBox cbZoom;
	private static JTextField tfTexturePlane;
	private static JComboBox cbLightColor;
	private static JComboBox cbCameraType;
	private static JCheckBox turnBoundingBox;
	

	public static void main(final String[] args) {

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 631, 178);

		JButton btnTexturePly = new JButton("Textura Ply");
		btnTexturePly.setBounds(6, 6, 134, 29);
		btnTexturePly.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser filechooser = new JFileChooser();

				int returnVal = filechooser.showOpenDialog(window);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = filechooser.getSelectedFile();
					App.tfTexturePly.setText(file.getAbsolutePath());
					try {
						TextureInteceptor.getInstance(ImageIO.read(new File(tfTexturePly.getText())), ImageIO.read(new File(tfTexturePlane.getText())));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		panel.add(btnTexturePly);

		tfTexturePly = new JTextField(SimplePLYExample.class.getResource("earth.jpg")
				.getPath());
		tfTexturePly.setBounds(144, 6, 134, 28);
		panel.add(tfTexturePly);
		tfTexturePly.setColumns(10);
		
		JButton btnTexturePlane = new JButton("Textura Plano");
		btnTexturePlane.setBounds(6, 89, 134, 29);
		btnTexturePlane.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser filechooser = new JFileChooser();

				int returnVal = filechooser.showOpenDialog(window);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = filechooser.getSelectedFile();
					App.tfTexturePlane.setText(file.getAbsolutePath());
					try {
						TextureInteceptor.getInstance(ImageIO.read(new File(tfTexturePly.getText())), ImageIO.read(new File(tfTexturePlane.getText())));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		panel.add(btnTexturePlane);
		
		tfTexturePlane = new JTextField(SimplePLYExample.class.getResource("chess.jpg")
				.getPath());
		tfTexturePlane.setColumns(10);
		tfTexturePlane.setBounds(144, 90, 134, 28);
		panel.add(tfTexturePlane);
		

		JButton btnPly = new JButton("Ply");
		btnPly.setBounds(30, 48, 75, 29);
		btnPly.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser filechooser = new JFileChooser();

				int returnVal = filechooser.showOpenDialog(window);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = filechooser.getSelectedFile();
					App.tfPly.setText(file.getAbsolutePath());
					MainWindow.tracePanel.setScene(createScene());
				}
			}
		});
		panel.add(btnPly);

		tfPly = new JTextField(SimplePLYExample.class.getResource("dart.ply")
				.getPath());
		tfPly.setBounds(144, 48, 134, 28);
		panel.add(tfPly);
		tfPly.setColumns(10);

		String[] cameraPosition = { "Frente", "Esquerda", "Traz", "Direita", "Cima" };
		cbCamera = new JComboBox(cameraPosition);
		cbCamera.setBounds(344, 9, 92, 24);
		cbCamera.setSelectedIndex(0);
		cbCamera.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.tracePanel.setScene(createScene());
				
			}
		});
		panel.add(cbCamera);

		JLabel lblCamera = new JLabel("Camera:");
		lblCamera.setBounds(290, 12, 61, 16);
		panel.add(lblCamera);
		
		JLabel lblLight = new JLabel("Luz:");
		lblLight.setBounds(290, 54, 61, 16);
		panel.add(lblLight);

		
		cbLight = new JComboBox(cameraPosition);
		cbLight.setSelectedIndex(0);
		cbLight.setBounds(344, 51, 92, 24);
		cbLight.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.tracePanel.setScene(createScene());
				
			}
		});
		panel.add(cbLight);
		
		JLabel lblZoom = new JLabel("Zoom:");
		lblZoom.setBounds(290, 94, 61, 16);
		panel.add(lblZoom);
		
		Integer[] zoom = { 0, 1, 2};
		cbZoom = new JComboBox(zoom);
		cbZoom.setSelectedIndex(1);
		cbZoom.setBounds(344, 91, 92, 24);
		cbZoom.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.tracePanel.setScene(createScene());
				
			}
		});
		panel.add(cbZoom);
		
		
		
		JLabel lblTipoCamera = new JLabel("Tipo camera:\n");
		lblTipoCamera.setBounds(448, 12, 85, 16);
		panel.add(lblTipoCamera);
		
	    String[] cameraType = {"Ortogonal", "Pin Hole"};
		cbCameraType = new JComboBox(cameraType);
		cbCameraType.setSelectedIndex(1);
		cbCameraType.setBounds(533, 6, 92, 24);
		cbCameraType.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.tracePanel.setScene(createScene());
				
			}
		});
		panel.add(cbCameraType);
		
		JLabel lblCorDaLuz = new JLabel("Cor da Luz:");
		lblCorDaLuz.setBounds(448, 56, 73, 16);
		panel.add(lblCorDaLuz);		

	    String[] lightColor = {"Branca", "Preta", "Vermelha", "Verde", "Azul", "Amarela", "Roxa" };
		cbLightColor = new JComboBox(lightColor);
		cbLightColor.setSelectedIndex(0);
		cbLightColor.setBounds(533, 50, 92, 24);
		cbLightColor.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.tracePanel.setScene(createScene());
				
			}
		});
		panel.add(cbLightColor);
		
		JLabel lblBoundingBox = new JLabel("Bounding Box:");
		lblBoundingBox.setBounds(448, 90, 100, 25);
		panel.add(lblBoundingBox);	
		
		turnBoundingBox = new JCheckBox();
		turnBoundingBox.setMnemonic(KeyEvent.VK_B); 
		turnBoundingBox.setSelected(true);
		turnBoundingBox.setBounds(535, 90, 20, 20);
		turnBoundingBox.addItemListener(new ItemListener() {	
			@Override
			public void itemStateChanged(ItemEvent e) {
				MainWindow.tracePanel.setScene(createScene());
			}
		});
		
		panel.add(turnBoundingBox);
	    
	    		
		try {
			window = new MainWindow(ImageIO.read(new File(tfTexturePly
					.getText())), ImageIO.read(new File(tfTexturePlane
					.getText())));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		window.getContentPane().add(panel, BorderLayout.NORTH);	
		window.setVisible(true);
	}

	public static Scene createScene() {

		scene = updateTriangles();
		
		final ReflectanceCoefficient kAmbient = new ReflectanceCoefficient(0.25, 0.25, 0.25);
        final ReflectanceCoefficient kDiffuse = new ReflectanceCoefficient(0.3, 0.3, 0.3);
		
		final Point3D planePoint = new Point3D(0, 20, 0);
        final Vector3D planeNormal = new Vector3D(0, -1, 0);
        final Material planeMaterial = Materials.metallic(ColorRGB.BLUE);
        final Plane plane2 = new Plane(planePoint.multiply(-1), planeNormal.multiply(-1), planeMaterial);
        

		final Point3D lookAt = Point3D.ORIGIN;

		final Point3D eye;
		switch (cbCamera.getSelectedIndex()) {
		case 0:
			eye = new Point3D(0, 0, 100);
			break;
		case 1:
			eye = new Point3D(-100, 0, 0);
			break;
		case 2:
			eye = new Point3D(0, 0, -100);
			break;
		case 3:
			eye = new Point3D(100, 0, 0);
			break;
		default:
			eye = new Point3D(0, +100, 0);
		}
		final Vector3D up = new Vector3D(0, 1, 0);

		final Light light;
		switch (cbLight.getSelectedIndex()) {
		case 0:
			light = new Light(0, 0, 100);
			break;
		case 1:
			light = new Light(-100, 0, 0);
			break;
		case 2:
			light = new Light(0, 0, -100);
			break;
		case 3:
			light = new Light(100, 0, 0);
			break;
		default:
			light = new Light(0, 100, 0);
		}
		
		
		switch (cbLightColor.getSelectedIndex()) {
		case 0:
			light.setColor(ColorRGB.WHITE);
			break;
		case 1:
			light.setColor(ColorRGB.BLACK);
			break;
		case 2:
			light.setColor(ColorRGB.RED);
			break;
		case 3:
			light.setColor(ColorRGB.GREEN);
			break;
		case 4:
			light.setColor(ColorRGB.BLUE);
			break;
		case 5:
			light.setColor(ColorRGB.YELLOW);
			break;
		default:
			light.setColor(ColorRGB.PURPLE);
		}

		final Camera camera;
		switch (cbCameraType.getSelectedIndex()) {
		case 0:
			camera = new  OrthogonalCamera(eye, lookAt, up);
			break;
		default:
			camera  = new PinHoleCamera(eye, lookAt, up);
			break;
		}
		
		camera.setZoomFactor(Math.pow(10, new Double(cbZoom.getSelectedIndex())));
		
		scene.add(plane2).addWithoutBoundBox(plane2).add(light).setCamera(camera);
		return scene;
	}

	private static Scene updateTriangles() {
		InputStream is;
		try {
			is = new FileInputStream(tfPly.getText());
			triangles = PlyReader.read(is, Materials.metallic(ColorRGB.GREEN));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Double maxX = -100000.0; 
		Double maxY = -100000.0;
		Double maxZ = -100000.0;
		Double minX = 100000.0;
		Double minY = 100000.0;
		Double minZ = 100000.0;
		
		for (Triangle t : triangles) {
			if(maxX < t.getV1().getX()){
				maxX = t.getV1().getX();
			}
			if(maxY < t.getV1().getY()){
				maxY = t.getV1().getY();
			}
			if(maxZ < t.getV1().getZ()){
				maxZ = t.getV1().getZ();
			}
			if(minX > t.getV1().getX()){
				minX = t.getV1().getX();
			}
			if(minY > t.getV1().getY()){
				minY = t.getV1().getY();
			}
			if(minZ > t.getV1().getZ()){
				minZ = t.getV1().getZ();
			}

			if(maxX < t.getV2().getX()){
				maxX = t.getV2().getX();
			}
			if(maxY < t.getV2().getY()){
				maxY = t.getV2().getY();
			}
			if(maxZ < t.getV2().getZ()){
				maxZ = t.getV2().getZ();
			}
			if(minX > t.getV2().getX()){
				minX = t.getV2().getX();
			}
			if(minY > t.getV2().getY()){
				minY = t.getV2().getY();
			}
			if(minZ > t.getV2().getZ()){
				minZ = t.getV2().getZ();
			}
			
			if(maxX < t.getV3().getX()){
				maxX = t.getV3().getX();
			}
			if(maxY < t.getV3().getY()){
				maxY = t.getV3().getY();
			}
			if(maxZ < t.getV3().getZ()){
				maxZ = t.getV3().getZ();
			}
			if(minX > t.getV3().getX()){
				minX = t.getV3().getX();
			}
			if(minY > t.getV3().getY()){
				minY = t.getV3().getY();
			}
			if(minZ > t.getV3().getZ()){
				minZ = t.getV3().getZ();
			}
			
		}

		Point3d l = new Point3d(minX, minY, minZ);
		Point3d u = new Point3d(maxX, maxY, maxZ);
		System.out.println("Lower Corner " + l.toString());
		System.out.println("Upper Corner " + u.toString());
		
		BoundingBox bb = new BoundingBox(l, u);
		Scene scene = new Scene();
		boolean comBoudingBox = turnBoundingBox.isSelected();
		scene.setTestBoundingBoxs(comBoudingBox);
		for (Triangle t : triangles) {
			scene.add(t);
		}
		scene.add(bb);
		return scene;
	}
}
