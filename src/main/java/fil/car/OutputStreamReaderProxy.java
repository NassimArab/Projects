package fil.car;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class OutputStreamReaderProxy extends OutputStreamWriter {

	OutputStream outC;
	

	public OutputStreamReaderProxy(OutputStream out) {
		super(out);
		this.outC = out;
	}
	public OutputStream getOutC() {
		return outC;
	}
	public void setOutC(OutputStream outC) {
		this.outC = outC;
	}
	@Override
	public void write(String str) throws IOException {
		// TODO Auto-generated method stub
		super.write(str);
	}
	

}
