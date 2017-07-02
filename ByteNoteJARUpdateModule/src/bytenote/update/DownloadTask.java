package bytenote.update;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.time.Instant;

import javafx.concurrent.Task;

public class DownloadTask extends Task<Void> {
	
	protected URL download;
	protected File dest;
	protected InputStream in;
	protected FileOutputStream out;
	protected long totalBytes;
	protected long bytesLoaded = 0;
	private Instant startTime;
	
	public DownloadTask(URL downloadUrl, File destFile) {
		this.download = downloadUrl;
		this.dest = destFile;
		try {
			this.totalBytes = downloadUrl.openConnection().getContentLengthLong();
			this.startTime = Instant.now();
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public long getTotalBytes() {
		return totalBytes;
	}
	
	public long getBytesLoaded() {
		return bytesLoaded;
	}
	
	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		if(mayInterruptIfRunning) {
			if(isRunning()) {
				try {
					if(in != null) {
						in.close();
					}
					if(out != null) {
						out.close();
					}
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
		out = new FileOutputStream(dest);
		int inInt = in.read();
		while(inInt != -1) {
			out.write(inInt);
			bytesLoaded++;
			this.updateProgress(bytesLoaded, totalBytes);
			inInt = in.read();
		}
		out.close();
		in.close();
		return null;
	}

	public Instant getStartTime() {
		return startTime;
	}
	
}
