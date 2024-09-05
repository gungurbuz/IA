package businesslayer;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.DefaultMutableThemeStyle;
import com.googlecode.lanterna.graphics.DelegatingTheme;
import com.googlecode.lanterna.graphics.DelegatingThemeDefinition;
import com.googlecode.lanterna.graphics.ThemeDefinition;
import com.googlecode.lanterna.graphics.ThemeStyle;
import com.googlecode.lanterna.gui2.Button;


public class ColoredButton extends Button {
	
	public ColoredButton(String label,Runnable x , final TextColor.ANSI color) {
		super(label, x);
		setTheme(new DelegatingTheme(getTheme()) {
			@Override
			public ThemeDefinition getDefinition(Class<?> clazz) {
				ThemeDefinition themeDefinition = super.getDefinition(clazz);
				return new FixedBackgroundButtonThemeStyle(themeDefinition, color);
			}
		});
	}
	
	private static class FixedBackgroundButtonThemeStyle extends DelegatingThemeDefinition {
		
		private final TextColor.ANSI color;
		
		public FixedBackgroundButtonThemeStyle(ThemeDefinition definition, TextColor.ANSI color) {
			super(definition);
			this.color = color;
		}
		
		@Override
		public ThemeStyle getNormal() {
			DefaultMutableThemeStyle mutableThemeStyle = new DefaultMutableThemeStyle(super.getNormal());
			return mutableThemeStyle.setBackground(color);
		}
		
		@Override
		public ThemeStyle getActive() {
			DefaultMutableThemeStyle mutableThemeStyle = new DefaultMutableThemeStyle(super.getActive());
			return mutableThemeStyle.setBackground(color);
		}
		
	}
	
}
