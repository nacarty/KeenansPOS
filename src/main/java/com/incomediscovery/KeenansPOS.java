package com.incomediscovery;

import com.incomediscovery.service.KeenansGUI;


public class KeenansPOS
{
    public static void main( String[] args ) {
        String filename = "";
        if (args != null &&  args.length > 0)
            filename = args[0];
        new KeenansGUI(filename);
    }
}
