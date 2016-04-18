/*public class Cpu{
    //Creates new cpu with the given parameteres
    *@param gui //a ref to the gui interface
    *@param cpuQueue //the cpu queue to be used
    *@param maxCpuTime //the round robin time quant to be used
    *@param statistics //a ref to the the statistics collector*/

public class CPU{

    private long maxCpuTime;
    private Process activeProcess;
    private Queue cpuQueue;
    private Statistics statistics;
    private Gui gui;
    private EventQueue eq;



    //----
    public CPU(Queue cpuQueue, Statistics statistics, long maxCpuTime, Gui gui, EventQueue eq){
        this.cpuQueue = cpuQueue;
        this.statistics = statistics;
        this.maxCpuTime = maxCpuTime;
        this.gui = gui;
        this.eq = eq;
    }

    public void addToQueue(Process p, long clock){
        this.cpuQueue.insert(p);
        p.arrivedAtCpuQueue(clock);


        if(this.activeProcess == null && this.cpuQueue.getQueueLength() == 1){
            this.eq.insertEvent(new Event(Constants.SWITCH_PROCESS, clock));
        }

        this.statistics.cpuQueueInserts++;
    }

    public void processNextProcess(long clock){
        if(this.activeProcess != null){
            if(this.activeProcess.getCpuTimeNeeded() > this.maxCpuTime && this.activeProcess.getTimeToNextIO() <= this.maxCpuTime){
                this.activeProcess.updateCpuTimeNeeded(this.activeProcess.getTimeToNextIO());
                this.eq.insertEvent(new Event(Constants.IO_REQUEST, clock + this.activeProcess.getTimeToNextIO()));
                this.eq.insertEvent(new Event(Constants.SWITCH_PROCESS, clock + this.activeProcess.getTimeToNextIO()+1));
            }
            else if(this.activeProcess.getCpuTimeNeeded() <= maxCpuTime){
                this.eq.insertEvent(new Event(Constants.END_PROCESS, clock + this.activeProcess.getCpuTimeNeeded()));
                this.eq.insertEvent(new Event(Constants.SWITCH_PROCESS, clock + this.activeProcess.getCpuTimeNeeded()+1));

                activeProcess.updateCpuTimeNeeded(maxCpuTime);

                this.statistics.timeSpentCPU += this.activeProcess.getCpuTimeNeeded();
            }
            else if(this.activeProcess.getCpuTimeNeeded() > this.maxCpuTime){
                activeProcess.updateCpuTimeNeeded(maxCpuTime);

                this.eq.insertEvent(new Event(Constants.SWITCH_PROCESS, clock + maxCpuTime));
                this.statistics.nofProcessSwitches++;
                this.statistics.timeSpentCPU += this.maxCpuTime;
            }

        }
    }



    public void switchProcess(long clock){
        if(activeProcess != null) {
            this.cpuQueue.insert(this.activeProcess);
        }
        if(!cpuQueue.isEmpty()) {

            if(cpuQueue.getNext() instanceof Process){
                this.activeProcess = (Process) cpuQueue.getNext();
                this.cpuQueue.removeNext();
                this.activeProcess.leftCpuQueue(clock);
                this.gui.setCpuActive(activeProcess);
            }
        }
    }

    public Process endProcess(){
        Process oldProcess = null;
        if (activeProcess != null){
            oldProcess = activeProcess;
            activeProcess = null;
        }

        this.gui.setCpuActive(null);
        this.statistics.nofCompletedProcesses++;
        this.statistics.totalTimeInSystem += oldProcess.calcTotalTimeInSystem();
        return oldProcess;
    }

    public Process getProcessForIO(){
        Process ioProcess = this.activeProcess;
        this.activeProcess = null;
        this.gui.setCpuActive(null);
        return ioProcess;
    }

    public void timePassed(long timePassed){
        this.statistics.cpuQueueLengthTime += this.cpuQueue.getQueueLength()*timePassed;

        if(this.cpuQueue.getQueueLength()>this.statistics.cpuQueueLargestLength){
            this.statistics.cpuQueueLargestLength = this.cpuQueue.getQueueLength();
        }
    }

}

