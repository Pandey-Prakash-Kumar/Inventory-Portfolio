import java.util.Scanner;
public class Test {
    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public static void main(String[] args) {
        Test t1 = new Test();
        t1.setNum(8);
        float fl;
        System.out.println("16th line");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number from that u want to divide:");
        int x = sc.nextInt();
        try{
            fl = t1.num/x;
            System.out.println(fl);
        }
        catch (ArithmeticException e){
            System.out.println(e.getMessage());
        }
        finally{
            System.out.println("this is finally block");
        }


        int num = 5 * 6;
        System.out.println(num);

    }
}



