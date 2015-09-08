package com.ma3x.ipmiruler.ipmirule;

import android.content.Context;
import android.os.AsyncTask;

import com.veraxsystems.vxipmi.api.async.ConnectionHandle;
import com.veraxsystems.vxipmi.api.sync.IpmiConnector;
import com.veraxsystems.vxipmi.coding.commands.IpmiVersion;
import com.veraxsystems.vxipmi.coding.commands.PrivilegeLevel;
import com.veraxsystems.vxipmi.coding.commands.chassis.ChassisControl;
import com.veraxsystems.vxipmi.coding.commands.chassis.ChassisControlResponseData;
import com.veraxsystems.vxipmi.coding.commands.chassis.PowerCommand;
import com.veraxsystems.vxipmi.coding.commands.chassis.GetChassisStatus;
import com.veraxsystems.vxipmi.coding.commands.chassis.GetChassisStatusResponseData;
import com.veraxsystems.vxipmi.coding.protocol.AuthenticationType;
import com.veraxsystems.vxipmi.coding.security.CipherSuite;

import java.io.IOException;
import java.net.InetAddress;

public class RetrieveInfoTask extends AsyncTask<String, Void, String> {

    public MainActivity activity;
    private Exception exception;
    private String ret;

    public RetrieveInfoTask(MainActivity a)
    {
        this.activity = a;
    }

    private void getInfo() {
        IpmiConnector connector = null;
        try {
            // Create the connector, specify port that will be used to communicate
            // with the remote host. The UDP layer starts listening at this port, so
            // no 2 connectors can work at the same time on the same port.
            connector = new IpmiConnector(6000);
            System.out.println("Connector created");

            // Create the connection and get the handle, specify IP address of the
            // remote host. The connection is being registered in ConnectionManager,
            // the handle will be needed to identify it among other connections
            // (target IP address isn't enough, since we can handle multiple
            // connections to the same host)
            ConnectionHandle handle = connector.createConnection(InetAddress
                    .getByName("10.0.0.11"));
            System.out.println("Connection created");

            // Get available cipher suites list via getAvailableCipherSuites and
            // pick one of them that will be used further in the session.
            CipherSuite cs = connector.getAvailableCipherSuites(handle).get(3);
            System.out.println("Cipher suite picked");

            // Provide chosen cipher suite and privilege level to the remote host.
            // From now on, your connection handle will contain these information.
            connector.getChannelAuthenticationCapabilities(handle, cs,
                    PrivilegeLevel.Administrator);
            System.out.println("Channel authentication capabilities receivied");

            // Start the session, provide username and password, and optionally the
            // BMC key (only if the remote host has two-key authentication enabled,
            // otherwise this parameter should be null)
            connector.openSession(handle, "ADMIN", "ADMIN", null);
            System.out.println("Session open");

            try {
                // Send some message and read the response
                GetChassisStatusResponseData rd = null;
                try {
                    // Send some message and read the response
                    rd = (GetChassisStatusResponseData) connector
                            .sendMessage(handle, new GetChassisStatus(IpmiVersion.V20, cs,
                                    AuthenticationType.RMCPPlus));
                } catch (Exception e) {
                    e.printStackTrace();
                    // double two
                    // Send some message and read the response
                    rd = (GetChassisStatusResponseData) connector
                            .sendMessage(handle, new GetChassisStatus(IpmiVersion.V20, cs,
                                    AuthenticationType.RMCPPlus));
                }

                System.out.println("Received answer");
                System.out.println("System power state is " + (rd.isPowerOn() ? "up" : "down"));
                this.ret = rd.isPowerOn() ? "UP" : "DOWN";

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // Close the session
                connector.closeSession(handle);
                System.out.println("Session closed");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close connection manager and release the listener port.
            connector.tearDown();
            System.out.println("Connection manager closed");
        }
    }

    private void sendPowerCommand(String cmd) {
        IpmiConnector connector = null;
        try {
            // Create the connector, specify port that will be used to communicate
            // with the remote host. The UDP layer starts listening at this port, so
            // no 2 connectors can work at the same time on the same port.
            connector = new IpmiConnector(6000);
            System.out.println("Connector created");

            // Create the connection and get the handle, specify IP address of the
            // remote host. The connection is being registered in ConnectionManager,
            // the handle will be needed to identify it among other connections
            // (target IP address isn't enough, since we can handle multiple
            // connections to the same host)
            ConnectionHandle handle = connector.createConnection(InetAddress
                    .getByName("10.0.0.11"));
            System.out.println("Connection created");

            // Get available cipher suites list via getAvailableCipherSuites and
            // pick one of them that will be used further in the session.
            CipherSuite cs = connector.getAvailableCipherSuites(handle).get(3);
            System.out.println("Cipher suite picked");

            // Provide chosen cipher suite and privilege level to the remote host.
            // From now on, your connection handle will contain these information.
            connector.getChannelAuthenticationCapabilities(handle, cs,
                    PrivilegeLevel.Administrator);
            System.out.println("Channel authentication capabilities receivied");

            // Start the session, provide username and password, and optionally the
            // BMC key (only if the remote host has two-key authentication enabled,
            // otherwise this parameter should be null)
            connector.openSession(handle, "ADMIN", "ADMIN", null);
            System.out.println("Session open");

            try {
                PowerCommand command = null;
                switch (cmd) {
                    case "ON":
                        command = PowerCommand.PowerUp;
                        break;
                    case "RESET":
                        command = PowerCommand.HardReset;
                        break;
                    case "OFF":
                        command = PowerCommand.PowerDown;
                        break;
                    default:
                        command = null;
                        break;
                }
                if(command != null) {
                    // Send some message and read the response
                    ChassisControlResponseData rd = (ChassisControlResponseData) connector
                            .sendMessage(handle, new ChassisControl(IpmiVersion.V20, cs,
                                    AuthenticationType.RMCPPlus, command));

                    System.out.println("Received answer");
                    System.out.println("Command sended");
                    this.ret = "Command sended";
                } else {
                    System.out.println("Command not sended");
                    this.ret = "Command not sended";
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // Close the session
                connector.closeSession(handle);
                System.out.println("Session closed");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close connection manager and release the listener port.
            connector.tearDown();
            System.out.println("Connection manager closed");
        }
    }

    protected String doInBackground(String... sendText) {
        this.ret = "";

        switch (sendText[1]) {
            case "hello":
                getInfo();
                break;
            case "ON":
                sendPowerCommand(sendText[1]);
                break;
            case "RESET":
                sendPowerCommand(sendText[1]);
                break;
            case "OFF":
                sendPowerCommand(sendText[1]);
                break;
            default:
                break;
        }

        try {
            //return new HWClient().getInfo(sendText);
            //return HWClient.main(sendText);
            ;
        } catch (Exception e) {
            this.exception = e;
            return null;
        }
        return this.ret;
    }

    protected void onPostExecute(String feed) {
        // TODO: check this.exception
        // TODO: do something with the feed
        activity.PostRetrieveInfoTask(this.ret);
    }
}