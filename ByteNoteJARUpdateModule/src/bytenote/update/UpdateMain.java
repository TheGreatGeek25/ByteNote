package bytenote.update;

import java.io.File;

public class UpdateMain {

	public static void main(String[] args) {
		if(args.length < 1) {
			throw new IllegalArgumentException("Invalid number of parameters. There must be at least one parameter.");
		}
		File jarFile = new File(args[0]);
		
	}

}
