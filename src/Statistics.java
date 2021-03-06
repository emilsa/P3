/**
 * This class contains a lot of public variables that can be updated
 * by other classes during a simulation, to collect information about
 * the run.
 */
public class Statistics
{
	/** The number of processes that have exited the system */
	public long nofCompletedProcesses = 0;
	/** The number of processes that have entered the system */
	public long nofCreatedProcesses = 0;
	/** The total time that all completed processes have spent waiting for memory */
	public long totalTimeSpentWaitingForMemory = 0;
	/** The time-weighted length of the memory queue, divide this number by the total time to get average queue length */
	public long memoryQueueLengthTime = 0;
	/** The largest memory queue length that has occured */
	public long memoryQueueLargestLength = 0;

	public long nofProcessSwitches = 0;
	public long nofProcessIO = 0;
	public long timeSpentCPU = 0;
	public long largestCPUQueueLength = 0;
	public long cpuQueueLargestLength = 0;
	public long cpuQueueLengthTime = 0;
	public long ioQueueLargestLength = 0;
	public long ioQueueLengthTime = 0;
	public long memoryQueueInserts = 0;
	public long cpuQueueInserts = 0;
	public long ioQueueInserts = 0;
	public long totalTimeInSystem = 0;
	public long totalTimeWaitingForCpu = 0;
	public long totalTimeProcessing = 0;
	public long totalTimeWaitingForIo = 0;
	public long totalTimeSpentInIo = 0;





	/**
	 * Prints out a report summarizing all collected data about the simulation.
	 * @param simulationLength	The number of milliseconds that the simulation covered.
	 */
	public void printReport(long simulationLength) {
		System.out.println();
		System.out.println("Simulation statistics:");
		System.out.println();
		System.out.println("Number of completed processes:                                "+nofCompletedProcesses);
		System.out.println("Number of created processes:                                  "+nofCreatedProcesses);
		System.out.println();
		System.out.println("Number of process switches:                                    "+nofProcessSwitches);
		System.out.println("Number of processed IO operations:                             "+nofProcessIO);
		System.out.println("Average throughput:                                            "+(float)nofCompletedProcesses/simulationLength*1000);
		System.out.println();
		System.out.println("Total CPU time spent processing:                               "+timeSpentCPU+ "ms");
		System.out.println("Fraction of CPU time spent processing:                         "+(float)timeSpentCPU/simulationLength*100 + "%");
		System.out.println("Total CPU time spent waiting:                                  "+(simulationLength-timeSpentCPU)+ "ms");
		System.out.println("Fraction of CPU time spent waiting:                            "+(float)(simulationLength-timeSpentCPU)/simulationLength*100 + "%");
		System.out.println();



		System.out.println("Largest occurring memory queue length:                         "+memoryQueueLargestLength);
		System.out.println("Average memory queue length:                                  "+(float)memoryQueueLengthTime/simulationLength);

		System.out.println("Largest occuring cpu queue length:                          "+cpuQueueLargestLength);
		System.out.println("Average cpu queue length:                         "+(float)cpuQueueLengthTime/simulationLength);
		System.out.println("Largest occuring I/O queue length:                         "+ioQueueLargestLength);
		System.out.println("Average I/O queue length:                         "+(float)ioQueueLengthTime/simulationLength);
		System.out.println("Average # of times a process has been placed in cpu queue:                        "+(float)cpuQueueInserts/nofCreatedProcesses);
		System.out.println("Average # of times a process has been placed in I/O queue:                     "+(float)ioQueueInserts/nofCreatedProcesses);
		System.out.println();


		if(nofCompletedProcesses > 0) {
			System.out.println("Average # of times a process has been placed in memory queue: "+1);
			System.out.println("Average time spent waiting for memory per process:            "+
				totalTimeSpentWaitingForMemory/nofCompletedProcesses+" ms");
		}

		System.out.println();
		System.out.println("Average time spent in system per process:                        "+(float)totalTimeInSystem/nofCreatedProcesses + "ms");
		System.out.println("Average time spent waiting for memory per process:                      "+(float)totalTimeSpentWaitingForMemory/nofCreatedProcesses + "ms");
		System.out.println("Average time spent waiting for cpu per process:                         "+(float)totalTimeWaitingForCpu/nofCreatedProcesses + "ms");
		System.out.println("Average time spent processing per process:                         "+(float)totalTimeProcessing/nofCreatedProcesses + "ms");
		System.out.println("Average time spent waiting for I/O per process:                          "+(float)totalTimeWaitingForIo/nofCreatedProcesses + "ms");
		System.out.println("Average time spent in I/O per process:                          "+(float)totalTimeSpentInIo/nofCreatedProcesses + "ms");


	}
}
