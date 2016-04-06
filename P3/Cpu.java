/*public class Cpu{
    //Creates new cpu with the given parameteres
    *@param gui //a ref to the gui interface
    *@param cpuQueue //the cpu queue to be used
    *@param maxCpuTime //the round robin time quant to be used
    *@param statistics //a ref to the the statistics collector*/

public class Cpu(Gui gui, Queue queue, long maxCpuTime, Statistics statistics){
        //----


        public Event switchProcess(long clock){
            if(activeProcess != null){
        //if the queue is empty the active process is allowed to continue
                if(!cpuQueue.isEmpty()){
        //put the active process at the end of the queue

        //switch in a new process from the cpu queue
        //update statistics
        }
        }
        else{
        //no active process, switch in a process if the queue is non-empty
        }
        //figure out what to return
        }

}