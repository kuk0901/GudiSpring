package gudiSpring.place.controller.cafe;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import gudiSpring.place.dao.cafe.CafeDao;
import gudiSpring.place.dto.cafe.CafeDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(value = "/area/place/cafe")
public class CafeListController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// 카페 리스트 출력
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = null;

		try {
			ServletContext sc = this.getServletContext();

			conn = (Connection) sc.getAttribute("conn");

			String areaNoParam = req.getParameter("areaNo");
			int areaNo = 1;

			if (areaNoParam != null && !areaNoParam.isEmpty()) {
				try {
					areaNo = Integer.parseInt(areaNoParam);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
			CafeDao cafeDao = new CafeDao();
			cafeDao.setConnection(conn);

			ArrayList<CafeDto> cafeList = (ArrayList<CafeDto>) cafeDao.selectCafeList(areaNo);

			req.setAttribute("cafeList", cafeList);

			RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/place/cafe/cafeListView.jsp");
			dispatcher.forward(req, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
