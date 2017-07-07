package bytenote.update;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.time.Duration;
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
	private long downloadSpeed;
	private int downloadSpeedSampleLen = 2;
	
	public DownloadTask(URL downloadUrl, File destFile) {
		this.download = downloadUrl;
		this.dest = destFile;
		try {
			this.totalBytes = downloadUrl.openConnection().getContentLengthLong();
			this.startTime = Instant.now();
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public long getDownloadSpeed() {
		return downloadSpeed;
	}
	
	public String getDownloadSpeedString() {
		String out;
		if(downloadSpeed > 10000000) {
			out = Math.round(downloadSpeed/10000000)/100.0+" GB/s";
		} else if(downloadSpeed > 10000) {
			out = Math.round(downloadSpeed/10000)/100.0+" MB/s";
		} else if(downloadSpeed > 100) {
			out = Math.round(downloadSpeed/100)/100.0+" kB/s";
		} else {
			out = downloadSpeed+" B/s";
		}
		return out;
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
		Instant sampleStart = Instant.now();
		long bytesReadSinceSample = 0;
		while(inInt != -1) {
			out.write(inInt);
			bytesLoaded++;
			this.updateProgress(bytesLoaded, totalBytes);
			inInt = in.read();
			bytesReadSinceSample++;
			if(Duration.between(sampleStart, Instant.now()).getSeconds() > downloadSpeedSampleLen) {
				downloadSpeed = bytesReadSinceSample/downloadSpeedSampleLen;
				sampleStart = Instant.now();
				bytesReadSinceSample = 0;
			}
		}
		out.close();
		in.close();
		return null;
	}

	public Instant getStartTime() {
		return startTime;
	}
	
}
