import com.HIDInterface.HIDInterface;
public class  HIDCommsTest {

    public static void main(String[] args){

        byte[] msg = new byte[65];
        msg[0] = 0x40;
        msg[1] = (byte) 0x01;
        msg[2] = (byte) 0xFE;
        msg[3] = 0x77;
        msg[4] = 0x77;
        msg[5] = 0x77;
        msg[6] = 0x77;
        msg[7] = 0x77;
        msg[8] = 0x77;
        msg[9] = 0x77;
        msg[10] = 0x77;
        msg[11] = 0x77;
        msg[12] = 0x77;
        msg[13] = 0x77;
        msg[14] = 0x77;
        msg[15] = 0x77;
        msg[16] = 0x77;
        msg[17] = 0x77;
        msg[18] = 0x77;
        msg[19] = 0x77;
        msg[20] = 0x77;
        msg[21] = 0x77;
        msg[22] = 0x77;
        msg[23] = 0x77;
        msg[24] = 0x77;
        msg[25] = 0x77;
        msg[26] = 0x77;
        msg[27] = 0x77;
        msg[28] = 0x77;
        msg[29] = 0x77;
        msg[30] = 0x77;
        msg[31] = 0x77;
        msg[32] = 0x77;
        msg[33] = 0x77;
        msg[34] = 0x77;
        msg[35] = 0x77;
        msg[36] = 0x77;
        msg[37] = 0x77;
        msg[38] = 0x77;
        msg[39] = 0x77;
        msg[40] = 0x77;
        msg[41] = 0x77;
        msg[42] = 0x77;
        msg[43] = 0x77;
        msg[44] = 0x77;
        msg[45] = 0x77;
        msg[46] = 0x77;
        msg[47] = 0x77;
        msg[48] = 0x77;
        msg[49] = 0x77;
        msg[50] = 0x77;
        msg[51] = 0x77;
        msg[52] = 0x77;
        msg[53] = 0x77;
        msg[54] = 0x77;
        msg[55] = 0x77;
        msg[56] = 0x77;
        msg[57] = 0x77;
        msg[58] = 0x77;
        msg[59] = 0x77;
        msg[60] = 0x77;
        msg[61] = 0x77;
        msg[62] = 0x77;
        msg[63] = 0x77;


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
