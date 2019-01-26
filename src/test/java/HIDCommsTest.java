import com.HIDInterface.HIDInterface;

import java.util.Scanner;

public class  HIDCommsTest {

    static final Boolean Enabledebug = true;

    public static void main(String[] args) throws InterruptedException {

        byte[] msg = new byte[65];
        msg[0] = 0x40;


        byte[] reply;
        HIDInterface Motherboard = new HIDInterface(0x3742, 0x5750, 64);
        byte[] motors;
        int[] joints;
        int x;
        joints= InverseKinematics();
        if(joints[0]<0)
        {
            msg[1]+=1;
        }
        if(joints[1]<0)
        {
            msg[1]+=2;
        }
        if(joints[2]<0)
        {
            msg[1]+=4;
        }
        if(joints[3]<0)
        {
            msg[1]+=8;
        }
         motors = jointsToBytes(joints);
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
    public static byte[] jointsToBytes(int[] joints){
        byte [] jointBytes = new byte[8];
        byte [] tmp;

        for(int i = 0;i<8;i+=2) {
            tmp = angleToBytes(Math.abs(joints[i/2]));
            jointBytes[i] = tmp[0];
            jointBytes[i+1] = tmp[1];
        }
        for(byte x: jointBytes){
            System.out.print(x+",");
        }
        System.out.println();
        return jointBytes;
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
    public static int [] InverseKinematics(){
        int [] Positions = new int[4];
        Scanner sc = new Scanner(System.in);
        double[] Leg = new double[4];
        Leg[0] = 0;
        Leg[1] = 92;
        Leg[2] = 75;
        Leg[3] = 120;
        double Theta1, Theta21, Theta22, Theta31, Theta32, Theta41, Theta42;
        System.out.println("Enter Motor X coordinate");
        double X = Double.parseDouble(sc.nextLine());
        System.out.println("Enter Motor Y coordinate");
        double Y = Double.parseDouble(sc.nextLine());
        System.out.println("Enter Motor Z coordinate");
        double Z = Double.parseDouble(sc.nextLine());
        System.out.println("Enter Motor Theta coordinate");
        double Q = Double.parseDouble(sc.nextLine());



        Theta1 = Math.atan(Y/Math.abs(Z));

        double r1 = Math.sqrt(Math.pow(Z, 2) + Math.pow(Y, 2));
        double x1 = r1;
        double y1 = X;
        double Px = x1-Leg[3]*Math.sin(Q);
        double Py = y1-Leg[3]*Math.cos(Q);

        Theta31 = -(Math.acos(((Math.pow(Px, 2)+Math.pow(Py, 2))-(Math.pow(Leg[1], 2)+Math.pow(Leg[2], 2)))/(2*Leg[1]*Leg[2])));

        double B = Math.atan(Py/Px);
        double y = Math.acos((Math.pow(Px, 2)+Math.pow(Py,2)+Math.pow(Leg[1], 2)-Math.pow(Leg[2], 2))/(2*Leg[1]*Math.sqrt(Math.pow(Px, 2)+Math.pow(Py, 2))));

        Theta21 = B+y;


        Theta41 = ((Math.PI/2)-Q)-(Theta21+Theta31);

        Theta1 = Theta1 * 57.2957795;
        Theta21 = Theta21 * 57.2957795;
        Theta31 = Theta31 * 57.2957795;
        Theta41 = Theta41 * 57.2957795;


        Positions[0] = (int)((Theta1/360) * 4096);
        Positions[1] = (int)((Theta21/360) * 4096);
        Positions[2] = (int)((Theta31/360) * 4096);
        Positions[3] = (int)((Theta41/360) * 4096);

        for(int x :Positions){
            System.out.print(x+",");
        }
        System.out.println();
        return Positions;
    }
}
