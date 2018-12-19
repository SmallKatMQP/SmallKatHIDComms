package com.HIDInterface;

import org.hid4java.*;
import org.hid4java.event.HidServicesEvent;
import sun.plugin2.message.Message;

import java.util.concurrent.TimeUnit;


public class HIDInterface implements HidServicesListener {
    private static int VendorID = 0x3742; //default used for SmallKat v3
    private static int ProductID = 0x5750; //default used for SmallKat v3
    private static int PacketLength = 64;//default for HID
    public static String SerialNumer = null; //unnecessary unless multiple devices with the same VID & PID are used
    private static HidDevice device;
    private static HidServices hidServices;

    public static void main(String[] args) throws HidException {

    }

    public void init() {
        HidServicesSpecification hidServicesSpecification = new HidServicesSpecification();
        hidServicesSpecification.setAutoShutdown(true);
        hidServicesSpecification.setScanInterval(1);
        hidServicesSpecification.setPauseInterval(1);
        hidServicesSpecification.setScanMode(ScanMode.SCAN_AT_FIXED_INTERVAL_WITH_PAUSE_AFTER_WRITE);

        // Get HID services using custom specification
        this.hidServices = HidManager.getHidServices(hidServicesSpecification);
        this.hidServices.addHidServicesListener(this);
        this.hidServices.start();
        this.device = this.hidServices.getHidDevice(this.VendorID, this.ProductID, this.SerialNumer);

    }

    public void HIDInterface(int VID, int PID, int packetLength) {
        this.ProductID = PID;
        this.VendorID = VID;
        this.PacketLength = packetLength;

    }

    public void hidDeviceAttached(HidServicesEvent hidServicesEvent) {
        System.out.println("Device attached: " + hidServicesEvent);

        // Add serial number when more than one device with the same
        // vendor ID and product ID will be present at the same time
        // if (hidServicesEvent.getHidDevice().isVidPidSerial(VendorID, ProductID, SerialNumer)) {
        //sendMessage(hidServicesEvent.getHidDevice());
        // }
    }

    public void hidDeviceDetached(HidServicesEvent hidServicesEvent) {

    }

    public void hidFailure(HidServicesEvent hidServicesEvent) throws HidException {

    }

    public int getVendorID() {
        return this.VendorID;
    }

    public int getPacketLength() {
        return PacketLength;
    }

    public int getProductID() {
        return ProductID;
    }

    public String getSerialNumer() {
        return SerialNumer;
    }

    public byte[] sendMessage(byte[] Message) {
        byte data[] = new byte[this.PacketLength];
        if(this.hidServices == null){
            this.init();
        }
        if(this.device == null){
            return (data);
        }

           if(!(this.device.isOpen())){
            this.device.open();
        }
        if (this.device != null) {
            int val = this.device.write(Message, this.PacketLength, (byte) 0x00);
            boolean moreData = true;

            while (moreData) {
                // This method will now block for 500ms or until data is read
                val = this.device.read(data, 0);
                switch (val) {
                    case -1:
                        System.err.println(this.device.getLastErrorMessage());
                        break;
                    case 0:
                        moreData = false;
                        break;
                    default:
                        break;
                }
            }
        }
        return data;
    }

    public void EndHID(){
        this.hidServices.shutdown();

    }
}
