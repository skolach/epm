
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Serv03
 */
@WebServlet("/Serv03")
public class Serv03 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private List list;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Serv03() {
		
		super();
		List list = new ArrayList<String>();
		list.add("ABC");
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());

		request.getRequestDispatcher("/NewFile.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String param = request.getParameter("name");
		if (param != null) {
			session.setAttribute("sparam", param);
		}
//		request.getRequestDispatcher("/NewFile.jsp").forward(request, response);
		response.sendRedirect("NewFile.jsp");
	}

}
