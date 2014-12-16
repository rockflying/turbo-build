package com.turbo.build.util;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class Console {
	private static MessageConsole console = new MessageConsole("Build Path", null);
	
	static {		
		IConsoleManager manager = ConsolePlugin.getDefault()
				.getConsoleManager();
		IConsole[] existing = manager.getConsoles();
		boolean exists = false;
		for (int i = 0; i < existing.length; i++) {
            if (console.equals(existing[i])){
            	exists = true;
            }
        }
		if (!exists) {
            manager.addConsoles(new IConsole[] { console });
        }
	}
	
	public static void println(String msg) {
		MessageConsoleStream printer = console.newMessageStream();  
		printer.setActivateOnWrite(true);
		printer.println(msg);
	}
	
	public static void println(int msg) {
		println(""+msg);
	}
	
	public static void println(Object obj) {
		println(obj.toString());
	}
}
