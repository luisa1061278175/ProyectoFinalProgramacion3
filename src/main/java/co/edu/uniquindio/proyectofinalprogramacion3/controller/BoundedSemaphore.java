package co.edu.uniquindio.agencia20241.controller;

public class BoundedSemaphore {
	private int senial = 0;
	private int bound   = 0;

	public BoundedSemaphore(int upperBound){
		this.bound = upperBound;
	}

	
	
	public synchronized void ocupar() throws InterruptedException{
		while(this.senial == bound){
			wait();
		}
		this.senial++;
		this.notify();
	}

	public synchronized void liberar() throws InterruptedException{
		while(this.senial == 0){
			wait();
		}
		this.senial--;
		this.notify();
	}
}

