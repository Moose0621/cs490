package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientConnection {
   private static DataOutputStream outputStream;

   /**
    * @return the outputStream
    */
   public static DataOutputStream getOutputStream() {
      return outputStream;
   }

   /**
    * @param outputStream the outputStream to set
    * 
    *           public static void setOutputStream(DataOutputStream outputStream) { ClientConnection.outputStream =
    *           outputStream; }
    * 
    *           /**
    * @return the inputstream
    */
   public static BufferedReader getInputstream() {
      return inputstream;
   }

   public static boolean sendtoServer(String toBeSent) {

      try {
         outputStream.writeUTF(toBeSent);
      } catch (IOException e) {
         e.printStackTrace();
         return false;
      }

      return true;

   }

   /**
    * @param inputstream the inputstream to set
    */
   public static void setInputstream(BufferedReader inputstream) {
      ClientConnection.inputstream = inputstream;
   }

   private static BufferedReader inputstream;

   public static void connectToDirectoryServer() {

      try {
         Socket skt = new Socket(InetAddress.getLocalHost(), 8182);
         outputStream = new DataOutputStream(skt.getOutputStream());
         inputstream = new BufferedReader(new InputStreamReader(skt.getInputStream()));

      } catch (UnknownHostException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
}
