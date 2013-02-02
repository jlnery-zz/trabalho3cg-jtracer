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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jtrace.Material;
import org.jtrace.Materials;
import org.jtrace.Scene;
import org.jtrace.cameras.Camera;
import org.jtrace.cameras.PinHoleCamera;
import org.jtrace.examples.io.SimplePLYExample;
import org.jtrace.geometry.Plane;
import org.jtrace.geometry.Triangle;
import org.jtrace.io.PlyReader;
import org.jtrace.lights.Light;
import org.jtrace.primitives.ColorRGB;
import org.jtrace.primitives.Point3D;
import org.jtrace.primitives.ReflectanceCoefficient;
import org.jtrace.primitives.Vector3D;
import javax.swing.JLabel;

public class App {

	private static MainWindow window;
	private static JTextField tfTexture;
	private static JTextField tfPly;
	private static List<Triangle> triangles;
	private static Scene scene;
	private static JComboBox cbCamera;
	private static JComboBox cbLight;

	public static void main(final String[] args) {

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 450, 120);

		JButton btnTexture = new JButton("Textura");
		btnTexture.setBounds(20, 6, 92, 29);
		btnTexture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser filechooser = new JFileChooser();

				int returnVal = filechooser.showOpenDialog(window);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = filechooser.getSelectedFile();
					App.tfTexture.setText(file.getAbsolutePath());
				}
			}
		});
		panel.add(btnTexture);

		tfTexture = new JTextField();
		tfTexture.setBounds(124, 5, 134, 28);
		panel.add(tfTexture);
		tfTexture.setColumns(10);

		JButton btnPly = new JButton("Ply");
		btnPly.setBounds(30, 50, 75, 29);
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
		tfPly.setBounds(124, 49, 134, 28);
		panel.add(tfPly);
		tfPly.setColumns(10);

		String[] data = { "Frente", "Esquerda", "Traz", "Direita", "Baixo", "Cima" };
		cbCamera = new JComboBox(data);
		cbCamera.setBounds(324, 8, 92, 24);
		cbCamera.setSelectedIndex(0);
		cbCamera.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.tracePanel.setScene(createScene());
				
			}
		});
		panel.add(cbCamera);

		JLabel lblCamera = new JLabel("Camera:");
		lblCamera.setBounds(270, 11, 61, 16);
		panel.add(lblCamera);
		
		JLabel lblLight = new JLabel("Luz:");
		lblLight.setBounds(270, 53, 61, 16);
		panel.add(lblLight);

		
		cbLight = new JComboBox(data);
		cbLight.setSelectedIndex(0);
		cbLight.setBounds(324, 50, 92, 24);
		cbLight.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.tracePanel.setScene(createScene());
				
			}
		});
		panel.add(cbLight);
		
		window = new MainWindow();
		window.getContentPane().add(panel, BorderLayout.NORTH);
		window.setVisible(true);
	}

	public static Scene createScene() {

		scene = updateTriangles();
		
		final ReflectanceCoefficient kAmbient = new ReflectanceCoefficient(0.07, 0.07, 0.07);
        final ReflectanceCoefficient kDiffuse = new ReflectanceCoefficient(0.3, 0.3, 0.3);
		
		final Point3D planePoint = new Point3D(0, 20, 0);
        final Vector3D planeNormal = new Vector3D(0, -1, 0);
        final Material planeMaterial = new Material(ColorRGB.YELLOW, kAmbient, kDiffuse);
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
		case 4:
			eye = new Point3D(0, -100, 0);
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
		default:
			light = new Light(100, 0, 0);
		}

		final Camera pinHoleCamera = new PinHoleCamera(eye, lookAt, up);
		pinHoleCamera.setZoomFactor(10);
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
