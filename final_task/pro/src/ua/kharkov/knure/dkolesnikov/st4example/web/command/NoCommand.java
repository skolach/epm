package ua.kharkov.knure.dkolesnikov.st4example.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.kharkov.knure.dkolesnikov.st4example.Path;

/**
 * No command.
 * 
 * @author D.Kolesnikov
 * 
 */
public class NoCommand extends Command {

	private static final long serialVersionUID = -2785976616686657267L;

	private static final Logger log = Logger.getLogger(NoCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		log.debug("Command starts");
		
		String errorMessage = "No such command";
		request.setAttribute("errorMessage", errorMessage);
		log.error("Set the request attribute: errorMessage --> " + errorMessage);

		log.debug("Command finished");
		return Path.PAGE__ERROR_PAGE;
	}

}