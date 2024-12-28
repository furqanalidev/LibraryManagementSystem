package com.assignment.gui.theme;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

public class myTheme
	extends FlatMacDarkLaf
{
	public static final String NAME = "myTheme";

	public static boolean setup() {
		return setup( new myTheme() );
	}

	public static void installLafInfo() {
		installLafInfo( NAME, myTheme.class );
	}

	@Override
	public String getName() {
		return NAME;
	}
}
