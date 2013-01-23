package org.jtrace.shader;

import java.util.Random;


import org.jtrace.primitives.Vector3D;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SpecularShaderUnitTest {
	
	@Test
	public void testCalculateSpecularLightReflection() {
		
		Vector3D v1 = randomVector().normal();
		Vector3D normal = randomVector().normal();
		
		Vector3D reflected = new SpecularShader(4).calculateSpecularLightReflection(v1, normal);
		
		Assert.assertEquals(v1.angleBetween(normal), reflected.angleBetween(normal), 0.000000000000001);
	}
	
	@Test
	public void testCalculateSpecularLightReflection_VequalsNormal() {
		
		Vector3D v1 = new Vector3D(1, 2, 3);
		Vector3D normal = new Vector3D(1, 2, 3);
		
		Vector3D reflected = new SpecularShader(4).calculateSpecularLightReflection(v1, normal);
		
		Assert.assertEquals(v1.angleBetween(normal), reflected.angleBetween(normal), 0.000000000000001);
	}

	@Test
	public void testCalculateSpecularLightReflection_VisParalell_to_Y_Axis() {
		
		Vector3D v1 = Vector3D.UNIT_Y;
		Vector3D normal = randomVector().normal();
		
		Vector3D reflected = new SpecularShader(4).calculateSpecularLightReflection(v1, normal);
		
		Assert.assertEquals(v1.angleBetween(normal), reflected.angleBetween(normal), 0.000000000000001);
	}
	
	@Test
	public void testCalculateSpecularLightReflection_NormalIsParalell_to_Y_Axis() {
		
		Vector3D v1 = randomVector().normal();
		Vector3D normal = Vector3D.UNIT_Y;
		
		Vector3D reflected = new SpecularShader(4).calculateSpecularLightReflection(v1, normal);
		
		Assert.assertEquals(v1.angleBetween(normal), reflected.angleBetween(normal), 0.000000000000001);
	}
	
	private Vector3D randomVector() {
		Random random = new Random();
		
		double x = random.nextDouble();
		double y = random.nextDouble();
		double z = random.nextDouble();
		
		return new Vector3D(x, y, z);
	}

}
