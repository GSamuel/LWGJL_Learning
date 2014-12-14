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

	private Entity entity;

	public Camera(Entity entity)
	{
		this.entity = entity;
	}

	public void move()
	{
		calculateZoom();
		calculatePitch();
		calculateAngleAroundPlayer();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDistance, verticalDistance);
		this.yaw = 180 - (entity.getRotY() + angleAroundPlayer);
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
		float theta = entity.getRotY() + angleAroundPlayer;
		float offsetX = (float) (horizDistance * Math
				.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizDistance * Math
				.cos(Math.toRadians(theta)));
		position.x = entity.getPosition().getX() - offsetX;
		position.y = entity.getPosition().getY() + verticDistance;
		position.z = entity.getPosition().getZ() - offsetZ;
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
