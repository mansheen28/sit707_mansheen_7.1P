package sit707_week7;

import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Test;

public class BodyTemperatureMonitorTest {

	@Test
	public void testStudentIdentity() {
		String studentId = "S223026486";
		Assert.assertNotNull("Student ID is null", studentId);
	}

	@Test
	public void testStudentName() {
		String studentName = "Mansheen Kaur";
		Assert.assertNotNull("Student name is null", studentName);
	}
	
	@Test
	public void testReadTemperatureNegative() {
		TemperatureSensor temperatureSensor = () -> -1.0; // Mock TemperatureSensor to return a negative temperature
        BodyTemperatureMonitor btm = new BodyTemperatureMonitor(temperatureSensor, null, null);
        double temperature = btm.readTemperature();
        Assert.assertTrue("Temperature should be negative", temperature < 0);
	}
	
	@Test
	public void testReadTemperatureZero() {
		 TemperatureSensor temperatureSensor = () -> 0.0; // Mock TemperatureSensor to return zero temperature
	        BodyTemperatureMonitor btm = new BodyTemperatureMonitor(temperatureSensor, null, null);
	        double temperature = btm.readTemperature();
	        Assert.assertEquals("Temperature should be zero", 0.0, temperature, 0.0);
	}
	
	@Test
	public void testReadTemperatureNormal() {
		TemperatureSensor temperatureSensor = () -> 37.0; // Mock TemperatureSensor to return normal temperature
        BodyTemperatureMonitor btm = new BodyTemperatureMonitor(temperatureSensor, null, null);
        double temperature = btm.readTemperature();
        Assert.assertEquals("Temperature should be normal", 37.0, temperature, 0.0);
	}

	@Test
	public void testReadTemperatureAbnormallyHigh() {
		TemperatureSensor temperatureSensor = () -> 40.0; // Mock TemperatureSensor to return abnormally high temperature
        BodyTemperatureMonitor btm = new BodyTemperatureMonitor(temperatureSensor, null, null);
        double temperature = btm.readTemperature();
        Assert.assertEquals("Temperature should be abnormally high", 40.0, temperature, 0.0);
	}

	/*
	 * CREDIT or above level students, Remove comments. 
	 */
	@Test
	public void testReportTemperatureReadingToCloud() {
		// Mock reportTemperatureReadingToCloud() calls cloudService.sendTemperatureToCloud()
		
		CloudService cloudService = mock(CloudService.class);
        TemperatureReading temperatureReading = new TemperatureReading(); // Mock temperature reading

        // Create BodyTemperatureMonitor instance with mocked dependencies
        BodyTemperatureMonitor btm = new BodyTemperatureMonitor(null, cloudService, null);
        btm.reportTemperatureReadingToCloud(temperatureReading);

        // Verify if sendTemperatureToCloud() method is called with the correct argument
        verify(cloudService).sendTemperatureToCloud(temperatureReading);
	}
	
	
	/*
	 * CREDIT or above level students, Remove comments. 
	 */
	@Test
	public void testInquireBodyStatusNormalNotification() {
		CloudService cloudService = mock(CloudService.class);
        NotificationSender notificationSender = mock(NotificationSender.class);

        // Set up cloud service to return "NORMAL" status
        when(cloudService.queryCustomerBodyStatus(any())).thenReturn("NORMAL");

        // Create BodyTemperatureMonitor instance with mocked dependencies
        BodyTemperatureMonitor btm = new BodyTemperatureMonitor(null, cloudService, notificationSender);
        btm.inquireBodyStatus();


	}
	
	/*
	 * CREDIT or above level students, Remove comments. 
	 */
	@Test
	public void testInquireBodyStatusAbnormalNotification() {
		CloudService cloudService = mock(CloudService.class);
        NotificationSender notificationSender = mock(NotificationSender.class);

        // Set up cloud service to return "ABNORMAL" status
        when(cloudService.queryCustomerBodyStatus(any())).thenReturn("ABNORMAL");

        // Create BodyTemperatureMonitor instance with mocked dependencies
        BodyTemperatureMonitor btm = new BodyTemperatureMonitor(null, cloudService, notificationSender);
        btm.inquireBodyStatus();


	}
}
