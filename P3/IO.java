public class IO {

    private Queue ioQueue;
    private Statistics statistics;
    private EventQueue eq;
    private Gui gui;
    private Process activeProcess;


    public IO(Queue ioQueue, Statistics statistics, Gui gui, EventQueue eq) {
        this.ioQueue = ioQueue;
        this.statistics = statistics;
        this.eq = eq;
        this.gui = gui;

    }

    public void addToQueue(Process p, long clock) {
        this.ioQueue.insert(p);

        if (this.activeProcess == null) {
            if (this.ioQueue.getNext() instanceof Process) {
                this.activeProcess = (Process) this.ioQueue.removeNext();
                this.gui.setIoActive(this.activeProcess);
                this.performIO(clock);
            }
        }

        this.statistics.ioQueueInserts++;
    }

    public void performIO(long clock) {
        if (this.activeProcess != null) {
            this.activeProcess.leftIoQueue(clock);
            this.eq.insertEvent(new Event(Constants.END_IO, clock + this.activeProcess.calcIOTime()));
        }
    }

    public Process endIoOp() {
        Process finishedIoProcess = this.activeProcess;
        this.activeProcess = null;

        if (this.ioQueue.getQueueLength() > 0) {
            if (this.ioQueue.getNext() instanceof Process) {
                this.activeProcess = (Process) this.ioQueue.removeNext();
            }
        }

        this.gui.setIoActive(activeProcess);
        this.statistics.nofProcessIO++;
        return finishedIoProcess;
    }

    public void timePassed(long timePassed) {
        this.statistics.ioQueueLengthTime += this.ioQueue.getQueueLength() * timePassed;
        if (this.ioQueue.getQueueLength() > this.statistics.ioQueueLargestLength) {
            this.statistics.ioQueueLargestLength = this.ioQueue.getQueueLength();

        }
    }

    public Process getActiveProcess() {
        return this.activeProcess;
    }
}