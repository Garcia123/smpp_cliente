package org.atiperu.hilos;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GrupoHilos {

	private ExecutorService executor;
	private List<Runnable> runables;

	public GrupoHilos(int cantidadDeHilos) {
		// TODO Auto-generated constructor stub
		this.executor = Executors.newFixedThreadPool(cantidadDeHilos);
	}

	public void asignando(Runnable r) {
		this.getRunables().add(r);
	}

	public void completando() {
		for (Runnable r : runables) {
			this.executor.execute(r);
		}
		executor.shutdown();
		while (!this.executor.isTerminated()) {
		}
	}

	public ExecutorService getExecutor() {
		return executor;
	}

	public void setExecutor(ExecutorService executor) {
		this.executor = executor;
	}

	public List<Runnable> getRunables() {
		if (this.runables == null)
			runables = new ArrayList<Runnable>();
		return runables;
	}

	public void setRunables(List<Runnable> runables) {
		this.runables = runables;
	}

}
