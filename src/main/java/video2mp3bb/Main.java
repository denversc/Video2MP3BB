package video2mp3bb;

public class Main implements Runnable {

    private final String[] _args;

    public Main(String[] args) {
        this._args = Util.copy(args);
    }

    public void run() {
        if (this._args != null && this._args.length == 1 && "init".equals(this._args[0])) {
            this.runInit();
        } else {
            this.runNormal();
        }
    }

    public void runInit() {

    }

    public void runNormal() {

    }

    public static void main(String[] args) {
        new Main(args).run();
    }
}
