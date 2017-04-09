package bytenote.update;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javafx.concurrent.Task;

public class DownloadTask extends Task<Void> {
	
	protected URL download;
	protected File dest;
	protected InputStream in;
	protected FileOutputStream out;
	
	public DownloadTask(URL downloadUrl, File destFile) {
		this.download = downloadUrl;
		this.dest = destFile;
	}
	
	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		if(mayInterruptIfRunning) {
			if(isRunning()) {
				try {
					in.close();
					out.close();
					dest.delete();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return super.cancel(mayInterruptIfRunning);
	}
	
	@Override
	protected Void call() throws Exception {
		URLConnection connect = download.openConnection();
		connect.setConnectTimeout(2000);
		in = connect.getInputStream();
		int total = in.available();
		out = new FileOutputStream(dest);
		while(in.available() > 0) {
			out.write(in.read());
			this.updateProgress(total-in.available(), total);
		}
		out.close();
		in.close();
		return null;
	}
	

}
