package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.EvaluationDao;
import entity.Evaluation;
import entity.Personne;

/**
 * Servlet implementation class of EvaluationServlet
 * 
 * Permet de traiter la page d'affichage de 'la liste des Evaluation en cours'
 * pour les personne vérifiées étant "Formateur"
 * 
 * @author Luis Martins
 */
@WebServlet("/evaluations")
public class EvaluationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Evaluation> evaluations = null;
		EvaluationDao dao = new EvaluationDao();
		int idFormateur = 0;
		
		try {
			/**
			 * On vérifie si la Session existe sinon on l'instancie par defaut 'not formateur'
			 * Si elle existe et que la personne connecté est formateur, Ok
			 */
			if(((Personne) request.getSession(true).getAttribute("user")).isEstFormateur()) {
				Personne personne = (Personne) request.getSession(true).getAttribute("user");
				idFormateur = personne.getId();
				evaluations = dao.getListByIdFormateur(idFormateur);
				request.setAttribute("listeEvaluations", evaluations);
				request.getRequestDispatcher("/WEB-INF/listeEvaluations.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			response.getWriter().println("PROBLEMATIQUE DE SELECTION");
			e.printStackTrace();
		}
	}
}
