import java.io.IOException;
import edu.rit.io.InStream;
import edu.rit.io.OutStream;
import edu.rit.pj2.Tuple;


public class InputArray  extends Tuple{

	public PointT[] points;
	
	 public InputArray() {

	}
	 public InputArray(PointT[] points)
	 {
		 this.points=points.clone();
	 }
	 
	 public InputArray(PointT[]points,int count)
	 {
		 this.points=points;
		 
	 }
	 
	 
	@Override
	public void readIn(InStream in) throws IOException {
		
		points=(PointT[]) in.readObjectArray();
		
	}

	@Override
	public void writeOut(OutStream out) throws IOException {
		out.writeObjectArray(points);
		
	}

}
