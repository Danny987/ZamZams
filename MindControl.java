

import javax.swing.JOptionPane;


import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

import edk.Edk;
import edk.EdkErrorCode;
import edk.EmoState;

/**
 * Object that represents simplified connection to EEG
 * @author Torin J. Adamson
 */
public class MindControl
{
	public static final short COMPOSER_PORT = 1726; /** The default port of EmoComposer */
	private Pointer edkEvent = Edk.INSTANCE.EE_EmoEngineEventCreate(); /** EDK pointer to event */
	private Pointer edkState = Edk.INSTANCE.EE_EmoStateCreate(); /** EDK pointer to state */
	private Pointer edkData = Edk.INSTANCE.EE_DataCreate(); /** EDK pointer to data */
	private IntByReference userID = new IntByReference(0); /** User ID */
	private IntByReference sampleCount = new IntByReference(0); /** Number of data samples taken */
	private boolean edkReady = false; /** Ready for action */
	private float engagement = 0.0f; /** Engagement levels */
	private float excitement = 0.0f; /** Excitement levels */
	private float frustration = 0.0f; /** Frustration levels */
	private float meditation = 0.0f; /** Meditation levels */
	private float pointerX = 256.0f; /** Pointer X location */
	private float pointerY = 256.0f; /** Pointer Y location */
	private float velocityX = 0.0f; /** Velocity X */
	private float velocityY = 0.0f; /** Velocity Y */
	private float rotationX = 1702.0f; /** Rotation angle in X */
	private float rotationY = 1702.0f; /** Rotation angle in Y */
	/**
	 * Initializes the connection to EDK
	 * @param useSimulator if true, will connect to EmoComposer instead of actual device (at 127.0.0.1 - same machine)
	 */
	public MindControl(boolean useSimulator)
	{
		// Connect
		if(useSimulator)
		{
			int ret = Edk.INSTANCE.EE_EngineRemoteConnect("127.0.0.1",COMPOSER_PORT,"Emotiv Systems-5");
			if(ret != EdkErrorCode.EDK_OK.ToInt())
			{
				JOptionPane.showMessageDialog(null,"Could not connect to EmoComposer (error code "+ret+")");
				System.exit(-1);
			}
		}
		else
		{
			int ret = Edk.INSTANCE.EE_EngineConnect("Emotiv Systems-5");
			if(ret != EdkErrorCode.EDK_OK.ToInt())
			{
				JOptionPane.showMessageDialog(null,"Could not connect to Emotiv device (error code "+ret+")");
				System.exit(-1);
			}
		}
		// Set buffer size
		Edk.INSTANCE.EE_DataSetBufferSizeInSec(1);
	}
	/**
	 * Collects data for the next frame.
	 */
	void next()
	{
		// Get the next event
		int ret;
		ret = Edk.INSTANCE.EE_EngineGetNextEvent(edkEvent);
		// Got event
		if(ret != EdkErrorCode.EDK_OK.ToInt())
		{
			// Check event type
			ret = Edk.INSTANCE.EE_EmoEngineEventGetType(edkEvent);
			// Is the device ready?
			if(ret == Edk.EE_Event_t.EE_UserAdded.ToInt() && userID != null)
			{
				Edk.INSTANCE.EE_DataAcquisitionEnable(userID.getValue(),true);
				EmoState.INSTANCE.ES_Init(edkState);
				edkReady = true;
			}
		}
		// Not ready?
		if(!edkReady)
			return;
		// Get emotion
		float tempEngagement = EmoState.INSTANCE.ES_AffectivGetEngagementBoredomScore(edkState);
		float tempExcitement = EmoState.INSTANCE.ES_AffectivGetExcitementShortTermScore(edkState);
		float tempFrustration = EmoState.INSTANCE.ES_AffectivGetFrustrationScore(edkState);
		float tempMeditation = EmoState.INSTANCE.ES_AffectivGetMeditationScore(edkState);
		engagement = criticallyDampedSpring(tempEngagement, engagement);
		excitement = criticallyDampedSpring(tempExcitement, excitement);
		frustration = criticallyDampedSpring(tempFrustration, frustration);
		meditation = criticallyDampedSpring(tempMeditation, meditation);
		
		// Get data
		//Edk.INSTANCE.EE_DataUpdateHandle(userID.getValue(),edkData);
		//Edk.INSTANCE.EE_DataGetNumberOfSample(edkData,sampleCount);
		/*if(sampleCount != null)
		{
			if(sampleCount.getValue() != 0)
			{
				double[] data=new double[sampleCount.getValue()];
				for(int n = 0;n < sampleCount.getValue();++n)
				{
					// Calculate velocity, position and rotation for X GyroX = index 17
					Edk.INSTANCE.EE_DataGet(edkData,17,data,sampleCount.getValue());
					velocityX += rotationX-data[n];
					pointerX += velocityX*0.05f;
					if(pointerX > 512.0f)
						pointerX = 512.0f;
					if(pointerX < 0.0f)
						pointerX = 0.0f;
					rotationX = (float)data[n];
					// Calculate velocity, position and rotation for Y GyroY = index 18
					Edk.INSTANCE.EE_DataGet(edkData,18,data,sampleCount.getValue());
					velocityY += rotationY-data[n];
					pointerY -= velocityY*0.05f;
					if(pointerY > 512.0f)
						pointerY = 512.0f;
					if(pointerY < 0.0f)
						pointerY = 0.0f;
					rotationY = (float)data[n];
				}
			}
		}*/
	}
	/**
	 * Returns true if the object is ready to collect EDK data.
	 * @return if EDK is ready
	 */
	public boolean isReady()
	{
		return edkReady;
	}
	/**
	 * Closes the connection to EDK.
	 */
	public void close()
	{
		// Stop EDK
		Edk.INSTANCE.EE_EngineDisconnect();
	}
	/**
	 * Gets the amount of user engagement.
	 * @return the engagement
	 */
	public float getEngagement()
	{
		return engagement;
	}
	/**
	 * Gets the amount of user determination.
	 * @return the determination
	 */
	public float getFrustration()
	{
		return frustration;
	}
	/**
	 * Gets the amount of user excitement.
	 * @return the excitement
	 */
	public float getExcitement()
	{
		return excitement;
	}
	/**
	 * Gets the amount of user mediation.
	 * @return the mediation
	 */
	public float getMeditation()
	{
		return meditation;
	}
	float criticallyDampedSpring(float target, float current)
	{
	  float velocity = 0.02f;
	  float currentToTarget = target - current;
	  float springForce = currentToTarget;
	  float dampingForce = (float) (-velocity*2*.3);
	  float force = springForce+dampingForce;
	  velocity += force;
	  return current+velocity;
	}
}
