package Fibonacci;

public class ThreadingFib extends Thread{
    private final int n;
    private final int granularity;
    private long result;

    public ThreadingFib(int n, int granularity) {
        this.n=n;
        this.granularity=granularity;
    }

    @Override
    public void run(){
        if(n<=granularity){
            result=SequentialFib.fib(n);
        }else{
            ThreadingFib leftFib = new ThreadingFib(n-1, granularity);
            ThreadingFib rightFib = new ThreadingFib(n-2, granularity);

            leftFib.start();
            rightFib.start();

            try{
                leftFib.join();
                rightFib.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            result=leftFib.getResult() + rightFib.getResult();
        }
    }


    public long getResult(){
        return result;
    }
}
