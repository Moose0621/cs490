package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {

   public static void main(String[] args) {
      try {
         ServerSocket serverSkt = new ServerSocket(8182, 6, InetAddress.getLocalHost());
         ServerConnectionThread serverThread;

         while (true) {
            System.out.println("Listening for Connection on port: " + serverSkt + "!!!");
            Socket clientConnection = serverSkt.accept();
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
            DataOutputStream outputStream = new DataOutputStream(clientConnection.getOutputStream());
            serverThread = new ServerConnectionThread(outputStream, inputStream);
            serverThread.start();
         }

      } catch (IOException e) {
         System.out.println("Connection Failed: 8181");
         e.printStackTrace();

         // TODO must implement interaction with MySQL dp to get info about files in storage.

         // TODO Only GET request needs to be implemented. The URL field in GET method contains the name of the
         // requested file;

      }
   }
}
