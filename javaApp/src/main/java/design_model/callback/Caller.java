package design_model.callback;

public class Caller {

    private ICallback callback;


    public Caller(ICallback callback) {
        this.callback = callback;
    }



    public void call() {
        System.out.println("start ....");
        callback.callback();
        System.out.println("end ....");
    }

    public static void main(String[] args) {


        /**
         * Caller类，回调者
         * ICallback，回调接口
         */
        Caller caller = new Caller(new ICallback() {
            @Override
            public void callback() {
                System.out.println("终于回调成功了！！");
            }
        });
        caller.call();
    }
}
