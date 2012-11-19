package server;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Andrew McCoy -- This class will create a thread instance of a server connection to allow multiple users to
 *         query the directory server concurrently.
 */

public class ServerConnectionThread extends Thread {
   BufferedReader inputStream;
   DataOutputStream outputStream;
   PrintWriter printWriter;
   String clientQuery;
   int queryFlag;
   String response;

   public ServerConnectionThread(DataOutputStream out, BufferedReader in) {
      inputStream = in;
      outputStream = out;
   }

   @SuppressWarnings("deprecation")
   @Override
   public void run() {
      PrintWriter printWriter = new PrintWriter(outputStream, true);

      while (true) {

         printWriter.println("Hola!!! How's it hangin homie??");

         try {
            response = inputStream.readLine();
         } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }

         response.toUpperCase();

         printWriter.println(response + "!!! \n Have a great Day!!");

      }
   }

}
