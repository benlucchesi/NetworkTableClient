
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTablesJNI;
import edu.wpi.first.networktables.PubSubOption;
import edu.wpi.first.networktables.StringPublisher;
import edu.wpi.first.networktables.StringSubscriber;
import edu.wpi.first.networktables.StringTopic;
import edu.wpi.first.util.CombinedRuntimeLoader;

import java.io.IOException;

import org.opencv.core.Core;

import edu.wpi.first.cscore.CameraServerJNI;
import edu.wpi.first.cscore.OpenCvLoader;
import edu.wpi.first.math.jni.EigenJNI;
import edu.wpi.first.util.WPIUtilJNI;

/**
 * Program
 */
public class Program {
    public static void main(String[] args) throws IOException {
        NetworkTablesJNI.Helper.setExtractOnStaticLoad(false);
        WPIUtilJNI.Helper.setExtractOnStaticLoad(false);
        EigenJNI.Helper.setExtractOnStaticLoad(false);
        CameraServerJNI.Helper.setExtractOnStaticLoad(false);
        OpenCvLoader.Helper.setExtractOnStaticLoad(false);
        CombinedRuntimeLoader.loadLibraries(Program.class, "wpiutiljni", "wpimathjni", "ntcorejni", Core.NATIVE_LIBRARY_NAME, "cscorejni");


        System.out.println("Your program has started...")
        // get the default instance of NetworkTables
        NetworkTableInstance networkTable = NetworkTableInstance.getDefault();

        // start the client with a team number
        networkTable.startClient4("simple client");
        networkTable.setServerTeam(6352);
        
        // connect to IP address
        // networkTable.setServer("10.63.52.129");
        // OR
        // get IP address from driver station
        networkTable.startDSClient();

        // get a table and publish/subscribe to topics
        NetworkTable table = networkTable.getTable("datatable");
        StringTopic myStringTopic = table.getStringTopic("myStringTopic");
        StringPublisher myStringPublisher = myStringTopic.publish(PubSubOption.keepDuplicates(true), PubSubOption.sendAll(true));

        // send a value to the robot
        myStringPublisher.set("Hello, robot");
        System.out.println("sent value...");

        // subscribe to a value from the robot - get the limelight's IP address address
        StringTopic limelight = networkTable.getStringTopic("/SmartDashboard/limelight_Interface");
        StringSubscriber limeLightSubscriber = limelight.subscribe("not set");
        System.out.println(limeLightSubscriber.get());

        System.out.println("your program has ended.");
    }
}
