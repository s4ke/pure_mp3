package de.hotware.puremp3.console;


public interface ICommand {
	
	public String usage();
	/**
	 * @param pArgs 0-element is always the command key itself (like in the main method)
	 * @throws UsageException if used in a wrong way
	 */
	public void execute(String... pArgs) throws UsageException, ExecutionException;
	
	public static class UsageException extends Exception {
		
		private static final long serialVersionUID = -8849053578967497194L;
		private ICommand mCommand;

		public UsageException() {
			super();
		}

		public UsageException(String pMessage) {
			super(pMessage);
		}

		public UsageException(String pMessage, Throwable pCause) {
			super(pMessage, pCause);
		}

		public UsageException(Throwable pCause) {
			super(pCause);
		}

		public UsageException(String pString, ICommand pCommand) {
			super(pString);
			this.mCommand = pCommand;
		}
		
		public UsageException(Throwable pCause, ICommand pCommand) {
			super(pCause);
			this.mCommand = pCommand;
		}
		
		public ICommand getCommand() {
			return this.mCommand;
		}
		
	}
	
	public static class ExecutionException extends Exception {
		
		private static final long serialVersionUID = 4749622113667250487L;
		private ICommand mCommand;

		public ExecutionException() {
			super();
		}

		public ExecutionException(String pMessage) {
			super(pMessage);
		}

		public ExecutionException(String pMessage, Throwable pCause) {
			super(pMessage, pCause);
		}

		public ExecutionException(Throwable pCause) {
			super(pCause);
		}

		public ExecutionException(String pString, ICommand pCommand) {
			super(pString);
			this.mCommand = pCommand;
		}
		
		public ExecutionException(Throwable pCause, ICommand pCommand) {
			super(pCause);
			this.mCommand = pCommand;
		}
		
		public ICommand getCommand() {
			return this.mCommand;
		}
		
	}

}
