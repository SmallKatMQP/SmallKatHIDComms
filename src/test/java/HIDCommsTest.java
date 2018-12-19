import com.HIDInterface.HIDInterface;
public class HIDCommsTest {

    public static void main(String[] args){

        byte[] msg = new byte[64];
msg[0] = 0x3f;
msg[1] = 0x77;


        byte[] reply = new byte[64];
        HIDInterface iface = new HIDInterface();
        iface.HIDInterface(0x3742, 0x5750, 64);
        iface.init();
        System.out.println(iface.getVendorID()+" "+ iface.getProductID()+" "+iface.getPacketLength());
       reply = iface.sendMessage(msg);
        for (byte b : reply) {
             System.out.printf(" %02x", b);
        }
        iface.EndHID();

    }
}
