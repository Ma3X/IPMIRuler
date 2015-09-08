package com.ma3x.ipmiruler.ipmirule;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

import java.io.IOException;
import java.net.InetAddress;
import com.veraxsystems.vxipmi.api.async.ConnectionHandle;
import com.veraxsystems.vxipmi.api.sync.IpmiConnector;
import com.veraxsystems.vxipmi.coding.commands.IpmiVersion;
import com.veraxsystems.vxipmi.coding.commands.PrivilegeLevel;
import com.veraxsystems.vxipmi.coding.commands.chassis.GetChassisStatus;
import com.veraxsystems.vxipmi.coding.commands.chassis.GetChassisStatusResponseData;
import com.veraxsystems.vxipmi.coding.protocol.AuthenticationType;
import com.veraxsystems.vxipmi.coding.security.CipherSuite;

public class MainActivity extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });
/*
        IpmiConnector connector = null;

        // Create the connector, specify port that will be used to communicate
        // with the remote host. The UDP layer starts listening at this port, so
        // no 2 connectors can work at the same time on the same port.
        try {
            connector = new IpmiConnector(6000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Connector created");

        // Create the connection and get the handle, specify IP address of the
        // remote host. The connection is being registered in ConnectionManager,
        // the handle will be needed to identify it among other connections
        // (target IP address isn't enough, since we can handle multiple
        // connections to the same host)
        ConnectionHandle handle = null;
        try {
            handle = connector.createConnection(InetAddress
                    .getByName("10.0.0.11"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Connection created");

        // Get available cipher suites list via getAvailableCipherSuites and
        // pick one of them that will be used further in the session.
        CipherSuite cs = null;
        try {
            cs = connector.getAvailableCipherSuites(handle).get(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Cipher suite picked");

        // Provide chosen cipher suite and privilege level to the remote host.
        // From now on, your connection handle will contain these information.
        try {
            connector.getChannelAuthenticationCapabilities(handle, cs,
                    PrivilegeLevel.Administrator);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Channel authentication capabilities receivied");

        // Start the session, provide username and password, and optionally the
        // BMC key (only if the remote host has two-key authentication enabled,
        // otherwise this parameter should be null)
        try {
            connector.openSession(handle, "ADMIN", "ADMIN", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Session open");

        // Send some message and read the response
        GetChassisStatusResponseData rd = null;
        try {
            rd = (GetChassisStatusResponseData) connector
                    .sendMessage(handle, new GetChassisStatus(IpmiVersion.V20, cs,
                            AuthenticationType.RMCPPlus));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Received answer");
        System.out.println("System power state is "
                + (rd.isPowerOn() ? "up" : "down"));

        // Close the session
        try {
            connector.closeSession(handle);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Session closed");

        // Close connection manager and release the listener port.
        connector.tearDown();
        System.out.println("Connection manager closed");
*/
    }
}
