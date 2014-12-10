package entities;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera
{
	private float distanceFromPlayer = 50;
	private float angleAroundPlayer = 0;

	private Vector3f position = new Vector3f(0, 0, 0);
	private float pitch;// rotation x
	private float yaw;// rot y
	private float roll;// rot z

	private Player player;

	public Camera(Player player)
	{
		this.player = player;
	}

	public void move()
	{
		calculateZoom();
		calculatePitch();
		calculateAngleAroundPlayer();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDistance, verticalDistance);
		this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public void setPosition(Vector3f position)
	{
		this.position = position;
	}

	public float getPitch()
	{
		return pitch;
	}

	public float getYaw()
	{
		return yaw;
	}

	public float getRoll()
	{
		return roll;
	}

	private void calculateCameraPosition(float horizDistance,
			float verticDistance)
	{
		float theta = player.getRotY() + angleAroundPlayer;
		float offsetX = (float) (horizDistance * Math
				.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizDistance * Math
				.cos(Math.toRadians(theta)));
		position.x = player.getPosition().getX() - offsetX;
		position.y = player.getPosition().getY() + verticDistance;
		position.z = player.getPosition().getZ() - offsetZ;
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
