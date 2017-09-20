/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.model.utils;

/**
 *
 * @author Swedge
 */
public class OSChecker {

	private String OS;

	public OSChecker() {

                OS = System.getProperty("os.name").toLowerCase();
		//System.out.println(OS);

		if (isWindows()) {
			//System.out.println("This is Windows");
		} else if (isMac()) {
			//System.out.println("This is Mac");
		} else if (isUnix()) {
			//System.out.println("This is Unix or Linux");
		} else if (isSolaris()) {
			//System.out.println("This is Solaris");
		} else {
			//System.out.println("Your OS is not support!!");
		}
	}

	public boolean isWindows() {

		return (OS.indexOf("win") >= 0);

	}

	public boolean isMac() {

		return (OS.indexOf("mac") >= 0);

	}

	public boolean isUnix() {

		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );

	}

	public boolean isSolaris() {

		return (OS.indexOf("sunos") >= 0);

	}

}
