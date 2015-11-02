import edu.rit.pj2.Job;
import edu.rit.pj2.Loop;
import edu.rit.pj2.Task;
import edu.rit.pj2.tuple.ObjectArrayTuple;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


/*
 * Class: TetraClu
 * Class that extends Job.
 * Reads the points from the file and sets up the masterFor.
 * Also sets a finishRule to carry out reduction at the end.
 * 
 */
public class TetraClu extends Job{

	/*
	 * Main method
	 * @param :args -The command line arguments.
	 */
	@Override
	public void main(String[] args)  {

		ArrayList<PointT>list=new ArrayList<>();//list of points
		if(args.length!=1)//if number of arguments incorrect.
		{
			System.out.println("Invalid number of arguments");
			return;
		}
		Scanner read=null;
		
		try {
			read=new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			System.out.println("File could not be read.");
		}
		
		PointT temp;
		//read all points and put it in ArrayList.
		while(read.hasNext())
		{
			temp =new PointT(read.nextDouble(),read.nextDouble(),read.nextDouble());
			list.add(temp);
		}
		
		int count=list.size();//number of points.
		
		if(count<4)
		{
			System.out.println("Invalid number of points");
		}
		
		PointT pointsList[]=new PointT[count];//array to store the points
		
		for(int i=0;i<count;i++)
		{
			pointsList[i]=list.get(i);//copy all the points from ArrayList to Array.
		}
		read.close();
		
		putTuple(new ObjectArrayTuple<PointT>(pointsList));//put the tuple consisting of array of points to tuple space.
		
		 masterSchedule(proportional);//schedule for masterFor.
		  masterFor(0,count-4, WorkerTask.class);//masterFor Loop
		
		rule().atFinish().task (ReduceTask.class).args().runInJobProcess();//rule to carry out reduction after all the workers have finished executing.
	}
	
	/*
	 * Class:WorkerTask 
	 * This class contains the workerFor which has been called by the masterFor in the TetraClu class.
	 * 
	 * 
	 *@author: Salil Rajadhyaksha
	 *@version: 16-oct-2015
	 *
	 **/
	 
	private static class WorkerTask extends Task
	{

		
		TetraReductionVbl globalVbl;//global reduction variable for semi-final reduction.
		/*
		 *The main method.
		 *@param :args Command line arguments. 
		 */
		@Override
		public void main(String[] args) throws Exception {

			globalVbl=new TetraReductionVbl(Double.MAX_VALUE);
			ObjectArrayTuple<PointT>input=readTuple(new ObjectArrayTuple<PointT>());//read the points array from Tuple space.
			PointT points[]=input.item;
			//Worker for loop with proportional schedule.
			workerFor().schedule(proportional).exec(new Loop() {
				
				int count;	
				TetraReductionVbl localVbl;
				//Method for initialization of per thread local variables.
				public void start() throws IOException
				{
					count=points.length;
					localVbl=threadLocal(globalVbl);//per core reduction variable.
				}
				/*method that carries out execution at the core level.
				 * @param :i the loop iteration.
				 */
				@Override
				public void run(int i) throws Exception {
					
					for(int j=i+1;j<=count-3;j++)
						for(int k=j+1;k<=count-2;k++)
							for(int l=k+1;l<=count-1;l++)
							{
									localVbl.reduce(new TetraReductionVbl(points,i, j, k, l));//reduce using the points at the given 4 indices.
							}				
				}
			});
			
			putTuple(globalVbl);//put tuple in tuple space for furtur reduction.
		}
		
		
		
		
	}
	
	/*
	 * 
	 * Class :ReduceTask
	 * Class to carry out reduction at the end of execution of each worker for.
	 */
	private static class ReduceTask
    extends Task
    {
    /**
     * ReducTask  main program.
     * @param :args -Command line arguments.
     */
    public void main(String[] args) throws Exception
       {

    	TetraReductionVbl template=new TetraReductionVbl();
		TetraReductionVbl getSmallest;
		ObjectArrayTuple<PointT> in=readTuple(new ObjectArrayTuple<PointT>());//read the points array from tuple spacce.
		
        TetraReductionVbl result=new TetraReductionVbl(Double.MAX_VALUE);
       while ((getSmallest = tryToTakeTuple (template)) != null)//while tuple present in tuple space.
          result.reduce (getSmallest);//get smallest.
       
       result.printVbl(in.item);
       }
    }

}
