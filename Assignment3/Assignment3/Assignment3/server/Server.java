package Assignment3.server;

import java.rmi.*;
import java.net.*;

import Assignment3.client.LoginRem; //import interface
import Assignment3.client.LoginRemImpl; //import Impl

public class Server {

    public static void main(String args[]) {

        try {
            System.out.println("----------Server is Active------------");
            LoginRem locobj = new LoginRemImpl();
            String name = "//in-csci-rrpc01:6724/LoginRemImpl";
            Naming.rebind(name, locobj);
        } catch (Exception e) {
            System.out.println("Server err: " + e.getMessage());
            e.printStackTrace();
        }

    }

}