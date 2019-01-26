import com.HIDInterface.HIDInterface;

import java.util.Scanner;

public class  HIDCommsTest {

    static final Boolean Enabledebug = false;

    public static void main(String[] args) throws InterruptedException {

        byte[] msg = new byte[65];
        msg[0] = 0x40;
        msg[1] = (byte) 0x77;


        byte[] reply;
        HIDInterface Motherboard = new HIDInterface(0x3742, 0x5750, 64);
        byte[] motors;
        int x;

        motors = getMotorPos();
        for(x = 0; x<8;x++) {
            msg[x+2] = motors[x];
        }
        for (int i = 0;i<1000;i++) {
            if(Enabledebug) {
                for (byte b : msg) {
                    System.out.printf(" %02x", b);
                }
                System.out.println(Motherboard.getVendorID() + " " + Motherboard.getProductID() + " " + Motherboard.getPacketLength());

            }
            reply = Motherboard.sendMessage(msg);
            if(Enabledebug) {
                for (byte b : reply) {
                    System.out.printf(" %02x", b);
                }
                System.out.println();
            }
            Thread.sleep(8);
        }
        Motherboard.EndHID();

    }

    public static byte[] angleToBytes(int angle){
        byte[] angleBytes = new byte[2];
        angleBytes[0] = (byte)(angle>>8);
        angleBytes[1] = (byte)(angle);
        return angleBytes;
    }
    public static byte[] getMotorPos(){
        byte[] motorPos = new byte[8];
        byte[] tmp;
        Scanner sc = new Scanner(System.in);
        for(int i = 0;i<8;i+=2) {
            System.out.println("Enter Motor "+i/2+" pos");
            tmp = angleToBytes(Integer.parseInt(sc.nextLine()));
            motorPos[i] = tmp[0];
            motorPos[i+1] = tmp[1];
        }
        return motorPos;
    }
}
