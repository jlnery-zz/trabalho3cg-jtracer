package org.jtrace.examples.swing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jtrace.Material;
import org.jtrace.Materials;
import org.jtrace.Scene;
import org.jtrace.cameras.Camera;
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
import org.jtrace.shader.Shaders;

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
		
		tfTexturePlane = new JTextField(SimplePLYExample.class.getResource("sea.jpg")
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

		final Camera pinHoleCamera = new PinHoleCamera(eye, lookAt, up);
		
		pinHoleCamera.setZoomFactor(Math.pow(10, new Double(cbZoom.getSelectedIndex())));
		
		scene.add(plane2).add(light).setCamera(pinHoleCamera);
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

		Scene scene = new Scene();

		for (Triangle t : triangles) {
			scene.add(t);
		}
		return scene;
	}
}
