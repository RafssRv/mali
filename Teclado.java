import java.io.*;

public class Teclado
{
    private static BufferedReader teclado =
                   new BufferedReader (new InputStreamReader (System.in));

    public static int getUmInt () throws Exception
    {
        return Integer.parseInt (teclado.readLine ());
    }

    public static char getUmChar () throws Exception
    {
        return teclado.readLine().charAt (0);
    }
}
