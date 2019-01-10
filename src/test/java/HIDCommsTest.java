import com.HIDInterface.HIDInterface;
public class HIDCommsTest {

    public static void main(String[] args){

        byte[] msg = new byte[65];
msg[0] = 0x40;
msg[1] = 0x77;


        byte[] reply = new byte[64];
        HIDInterface iface = new HIDInterface(0x3742, 0x5750, 64);
        for (byte b : msg) {
            System.out.printf(" %02x", b);
        }

        System.out.println(iface.getVendorID()+" "+ iface.getProductID()+" "+iface.getPacketLength());
       reply = iface.sendMessage(msg);
        for (byte b : reply) {
             System.out.printf(" %02x", b);
        }
        iface.EndHID();

    }
}
