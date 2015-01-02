package entities;

import org.lwjgl.input.Mouse;

public class EntityCamera extends Camera
{
	private float distanceFromPlayer = 15;
	private float angleAroundPlayer = 0;

	private Node node;

	public EntityCamera(Node node)
	{
		this.node = node;
	}

	public void move()
	{
		calculateZoom();
		calculatePitch();
		calculateAngleAroundPlayer();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDistance, verticalDistance);
		this.yaw = 180 - (node.getRotY() + angleAroundPlayer);
	}

	private void calculateCameraPosition(float horizDistance,
			float verticDistance)
	{
		float theta = node.getRotY() + angleAroundPlayer;
		float offsetX = (float) (horizDistance * Math
				.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizDistance * Math
				.cos(Math.toRadians(theta)));
		position.x = node.getPosition().getX() - offsetX;
		position.y = node.getPosition().getY() + verticDistance;
		position.z = node.getPosition().getZ() - offsetZ;
	}

	private float calculateHorizontalDistance()
	{
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}

	private float calculateVerticalDistance()
	{
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}

	private void calculateZoom()
	{
		float zoomLevel = Mouse.getDWheel() * 0.1f;
		distanceFromPlayer -= zoomLevel;
	}

	private void calculatePitch()
	{
		if (Mouse.isButtonDown(1))
		{
			float pitchChange = Mouse.getDY() * 0.3f;
			pitch -= pitchChange;
		}
	}

	private void calculateAngleAroundPlayer()
	{
		if (Mouse.isButtonDown(0))
		{
			float angleChange = Mouse.getDX() * 0.3f;
			angleAroundPlayer -= angleChange;
		}
	}

}
