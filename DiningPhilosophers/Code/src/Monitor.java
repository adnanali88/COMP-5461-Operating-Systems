/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor
{
	/*
	 * ------------
	 * Data members
	 * ------------
	 */
	//Eric&Adnan Edit: 
	int nPhilo;
	boolean[] eating;
	boolean speaking = false;


	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{
		// TODO: set appropriate number of chopsticks based on the # of philosophers
		//Eric's Edit: 
		nPhilo = piNumberOfPhilosophers;
		eating = new boolean[nPhilo];
	}

	/*
	 * -------------------------------
	 * User-defined monitor procedures
	 * -------------------------------
	 */

	/**
	 * Grants request (returns) to eat when both chopsticks/forks are available.
	 * Else forces the philosopher to wait()
	 */
	public synchronized void pickUp(final int piTID)
	{
		//Eric&Adnan Edit: 
	    try {
	      // While either of my neighbours are eating, I cannot eat
	      while (eating[(piTID + 1) % nPhilo] || eating[(piTID + (nPhilo - 1)) % nPhilo]) {
	        wait();
	      }
	      // When both neighbours are done eating, I start eating
	      eating[piTID] = true;
	    } catch (InterruptedException e) {
	      System.err.println("Monitor.pickUp():");
	      DiningPhilosophers.reportException(e);
	      System.exit(1);
	    }
	}

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID)
	{
		//Eric&Adnan Edit: 
	    eating[piTID] = false;
	    notifyAll();
	}

	/**
	 * Only one philopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	public synchronized void requestTalk()
	{
		//Eric&Adnan Edit: 
		try {
			// I cannot talk as long as someone else is speaking
			while (speaking) {
				wait();
			}
			// When no one else is talking, I can start talking
			speaking = true;
		} catch (InterruptedException e) {
			System.err.println("Monitor.requestTalk():");
			DiningPhilosophers.reportException(e);
			System.exit(1);
		}
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk()
	{
		//Eric&Adnan Edit: 
		speaking = false;
		notifyAll();
	}
}

// EOF
