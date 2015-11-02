
import java.io.IOException;

import edu.rit.io.InStream;
import edu.rit.io.OutStream;
import edu.rit.pj2.Tuple;
import edu.rit.pj2.Vbl;

/*
 * 
 * Class:TetraReductionVbl
 * This class implements the VBl interface and extends the Tuple class.
 * This class is responsible for carrying out the multi-core reduction.
 * This same class also acts as tuple that is used later to carry out cluster reduction.
 * 
 * @author:Salil Rajadhyaksha
 * @version:16 Oct-2015
 */
public class TetraReductionVbl extends Tuple implements Vbl {

	//fields to store the indices of the points
	private int i;
	private int j;
	private int k;
	private int l;
	private double volume;

	/*
	 * Constructor that makes a deep copy of the object passed.
	 * 
	 * @param obj: Object of class TetraReductionVbl
	 */
	public TetraReductionVbl(TetraReductionVbl obj)
	{
		this.Copy(obj);
	}


	/*
	 * Defualt constructor
	 */
	public TetraReductionVbl()
	{

	}

	/*
	 * Constructor
	 * @param :p array of the points of the tetrahedran.
	 * @param :i Integer,index of point  1.
	 * @param :j Integer, index of point 2.
	 * @param :k Integer, index of point 3.
	 * @param :l Integer, index of point 4.
	 */
	public TetraReductionVbl(PointT[]p,int i ,int j,int k,int l)
	{
		this.i=i;
		this.j=j;
		this.k=k;
		this.l=l;

		//Calculating the volume given the indexes of the 4 points of the tetrahedran.

		this.volume=Math.abs(((p[l].z-p[i].z)*((p[j].x-p[i].x)*(p[k].y-p[i].y)-(p[j].y-p[i].y)*(p[k].x-p[i].x))+
				(p[l].y-p[i].y)*((p[j].z-p[i].z)*(p[k].x-p[i].x)-(p[j].x-p[i].x)*(p[k].z-p[i].z))+
				(p[l].x-p[i].x)*((p[j].y-p[i].y)*(p[k].z-p[i].z)-(p[j].z-p[i].z)*(p[k].y-p[i].y)))/6);

	}					  




	/*
	 * Constructor 
	 * @param : volume -Double Set the volume of the object.
	 * 
	 */
	public TetraReductionVbl(double volume)
	{
		this.volume=volume;
		this.i=0;
		this.j=0;
		this.k=0;
		this.l=0;
	}

	/*
	 * 
	 * Reduction variable.
	 * @param : tVbl- the variable to be compared
	 * method compared the volume and if the given volume<this.volume
	 * makes this a deep copy of the given object.
	 */
	@Override
	public void reduce(Vbl tVbl) {

		TetraReductionVbl temp=(TetraReductionVbl)tVbl;

		if(temp.volume<this.volume)
		{
			this.Copy(temp);
		}
	}

	/*
	 * This methods makes this a deep copy of the object tVbl.
	 * 
	 * @param :tVbl-Object of type Vbl
	 * 
	 */
	@Override
	public void set(Vbl tVbl) {

		TetraReductionVbl vbl=(TetraReductionVbl)tVbl;
		this.Copy(vbl);
	}

	/*
	 * Object returns a deep copy of this object.
	 * @return : obj the tetraReductionVbl to be returned.
	 */
	public Object clone()
	{
		TetraReductionVbl obj=null;
		obj = (TetraReductionVbl)super.clone();
		obj.Copy(this);
		return obj;

	}

	/*
	 * Make this a deep copy of obj.
	 * @param :obj - The TetraReductionVbl to be copied to this.
	 */
	public void Copy(TetraReductionVbl obj)
	{
		this.i=obj.i;
		this.j=obj.j;
		this.k=obj.k;
		this.l=obj.l;
		this.volume=obj.volume;
	}

	/*
	 * Print the current object.
	 * 
	 * @param : the array of points.
	 */
	public void printVbl(PointT[] p)
	{
		printToConsole(i,p);
		printToConsole(j, p);
		printToConsole(k, p);
		printToConsole(l, p);

		System.out.printf ("%.5g",this.volume);
		System.out.println();
	}

	/*
	 * Print the points corresponding to the current index
	 * 
	 * @param : i the index of the point to be printed.
	 * @param : p the array of points
	 */
	private void printToConsole(int i, PointT[] p) {

		System.out.print(i+" "+"(");
		System.out.printf ("%.5g", p[i].x);
		System.out.print(",");
		System.out.printf ("%.5g", p[i].y);
		System.out.print(",");
		System.out.printf ("%.5g", p[i].z);
		System.out.println(")");


	}

	/*
	 * method that overrides the readIn method of the Tuple class.
	 * Reads object content from the stream.
	 * @in :Object type InStream.
	 */
	@Override
	public void readIn(InStream in) throws IOException {

		i=in.readInt();
		j=in.readInt();
		k=in.readInt();
		l=in.readInt();
		volume=in.readDouble();
	}

	/*
	 * Method that overrides the writeOut method of the Tuple class
	 * Writeout the contents of the object using the object of class OutStream.
	 * @param :out -object of type OutStream.
	 *
	 */
	@Override
	public void writeOut(OutStream out) throws IOException {

		out.writeInt(i);
		out.writeInt(j);
		out.writeInt(k);
		out.writeInt(l);
		out.writeDouble(volume);

	}

}
