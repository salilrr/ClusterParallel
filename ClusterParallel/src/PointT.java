import java.io.IOException;
//import java.util.Scanner;

import edu.rit.io.InStream;
import edu.rit.io.OutStream;
import edu.rit.io.Streamable;

/*
 * Class: PointT
 * Class used to store the x,y and z co-ordinates of a point.
 * Implements the Streamable Interface that is needed since it will be used in inter-core communication.
 * 
 * @author	:	Salil Rajadhyaksha
 * @version	: 16 -Oct-2015
 */

public class PointT  implements Streamable{

	
	//the point x,y and z co-ordinates 
	public double x;
	public double y;
	public double z;
	
	/*
	 * Default Constructor.
	 */
	public PointT()
	{
		
	}
	/*
	 * Constructor that takes in three double points.
	 * @param :x  double x co-ordinate.
	 * @param :y  double y co-ordinate.
	 * @param :z  double z co-ordinate.
	 */
	public PointT(double x,double y,double z)
	{
		this.x=x;
		this.y=y;
		this.z=z;
	}
	
	/*
	 * Constructor that takes an object.
	 * @param obj -An object of type PointT
	 * 
	 */
	public PointT(PointT obj)
	{
		this.x=obj.x;
		this.y=obj.y;
		this.z=obj.z;
	}
	
	/*
	 * method that returns an object that is deep copy of the given object.
	 * @param :obj object which is to be cloned.
	 * 
	 * @return : temp  object of class PointT. 
	 * 
	 */
	public Object clone(Object obj)
	{
		PointT temp=(PointT)obj;
		this.x=temp.x;
		this.y=temp.y;
		this.z=temp.z;
		
		return temp;
	}
	
	
	
	
	
	
	/*
	 *
	 * Overrides  the method from the streamable Interface.
	 * reads object from the stream.
	 * @param in- Object of type InStream
	 */

	@Override
	public void readIn(InStream in) throws IOException {

		x=in.readDouble();
		y=in.readDouble();
		z=in.readDouble();
	}

	/*
	 * Override of the method from the Streamable Interface.
	 * writes the contents of the object using object of type outStream
	 * 
	 * @param out-Object of type OutStream.
	 */
	@Override
	public void writeOut(OutStream out) throws IOException {

		out.writeDouble(x);
		out.writeDouble(y);
		out.writeDouble(z);
	}
	
	
	
	
}
