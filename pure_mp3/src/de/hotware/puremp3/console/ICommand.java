/**
 * File ICommand.java
 * ---------------------------------------------------------
 *
 * Copyright (C) 2012 Martin Braun (martinbraun123@aol.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * - The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * - The origin of the software must not be misrepresented.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * TL;DR: As long as you clearly give me credit for this Software, you are free to use as you like, even in commercial software, but don't blame me
 *   if it breaks something.
 */
package de.hotware.puremp3.console;

import java.io.IOException;

import de.hotware.hotsound.audio.player.MusicPlayerException;

public interface ICommand {

	public String usage();

	/**
	 * @param pArgs
	 *            0-element is always the command key itself (like in the main
	 *            method)
	 * @throws UsageException
	 *             if used in a wrong way
	 * @throws MusicPlayerException 
	 * @throws InterruptedException 
	 */
	public void execute(String... pArgs) throws UsageException,
			ExecutionException,
			IOException, MusicPlayerException, InterruptedException;

	public String[] getKeys();

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

	public static class ExecutionException extends RuntimeException {

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
