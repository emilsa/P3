public class IO implements Constants{
    public Event addIoRequest(Process requestingProcess, long clock){

        //ADd the process to the I/O queue

        ioQueue.insert(requestingProcess);
        requestingProcess.calculateTimeToNextIoOperation();
        //Chceck if a new I/O operation can be started

    }
     public Event startIoOperation(long clock){
         if(activeProcess == null){
             //the device is free
             if(!ioQueue.isEmpty()){
                 //------

                 //let the first process in the queue start I/O

                 //----

                 //update statistics
                 statistics.nofProcessedIoOperations++;

                 //Calculate the duration of the I/O operation and return the END_IO event

                 //-----

                 return new Event(END_IO, clock + ioOperationTime);

     //else no process are waiting for I/O
     //else another process is already doing I/O

             }
         }
     }
}