package sodoku;

public class TimerPrivitera {
	long start, stop;

	public void start() {
		start = System.currentTimeMillis();
	}

	public void stop() {
		stop = System.currentTimeMillis();
	}

	public long getDuration() {
		return (stop - start);
	}

}
