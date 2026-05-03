package org.moka.structure.base;

import java.time.Duration;
import java.time.Instant;

public class RunTimer {
	private Instant startTime;

	private void start() {
		startTime = Instant.now();
	}

	private void stop() {
		if (startTime == null) {
			throw new IllegalStateException("Timer가 시작되지 않았습니다.");
		}
		Instant endTime = Instant.now();
		long nanos = Duration.between(startTime, endTime).toNanos();
		System.out.println("실행 시간: " + toHumanTimes(nanos));
	}

	public void measure(Runnable task) {
		start();
		task.run();
		stop();
	}

	private String toHumanTimes(long nanos){
		String display = "";
		String sub = "("+nanos+" ns)";
		if(nanos > 10000000){
			display = String.format("%.2f s", nanos / 1_000_000_000.0);
		} else if (nanos > 1000000) {
			display = String.format("%.2f ms", nanos / 1_000_000.0);
		} else if (nanos > 1000) {
			display = String.format("%.2f μs", nanos / 1_000.0);
		} else {
			display = String.format("%d ns", nanos);
		}
		return display+sub;
	}

}
