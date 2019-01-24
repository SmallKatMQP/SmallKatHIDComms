import com.HIDInterface.HIDInterface;
public class  HIDCommsTest {

    public static void main(String[] args) throws InterruptedException {

        byte[] msg = new byte[65];
        msg[0] = 0x40;
        msg[1] = (byte) 0x77;
        msg[2] = (byte) 0x0F;
        msg[3] = (byte) 0xFA;
        msg[4] = (byte) 0x07;
        msg[5] = (byte) 0x77;
        msg[6] = (byte) 0x02;
        msg[7] = (byte) 0x44;
        msg[8] = (byte) 0x09;
        msg[9] = (byte) 0xFE;

        byte[] reply = new byte[64];
        HIDInterface iface = new HIDInterface(0x3742, 0x5750, 64);
        for( int x = 0;x< 100; x++){
        for (byte b : msg) {
            System.out.printf(" %02x", b);
        }

        System.out.println(iface.getVendorID() + " " + iface.getProductID() + " " + iface.getPacketLength());
        reply = iface.sendMessage(msg);
        for (byte b : reply) {
            System.out.printf(" %02x", b);
        }
            System.out.println();
             Thread.sleep(8);
        }
        iface.EndHID();


//        int b = 0x6577;
//        System.out.printf(String.format("0x%08X",(byte) b));
//        System.out.printf(String.format("0x%08X",(byte) (b>>8)));



    }

    public static byte[] angleToBytes(int angle){
        byte[] angleBytes = new byte[2];
        angleBytes[0] = (byte)(angle>>>8);
        angleBytes[1] = (byte)(angle);

        return (angleBytes);
    }
}
