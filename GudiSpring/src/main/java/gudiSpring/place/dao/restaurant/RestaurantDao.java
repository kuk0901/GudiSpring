package gudiSpring.place.dao.restaurant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gudiSpring.place.dto.restaurant.RestaurantDto;

public class RestaurantDao {

	private Connection connection;

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	// user 식당 리스트
	public List<RestaurantDto> selectRestaurantList(int areaNo) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "";

		sql += "SELECT PLACE_NAME, PLACE_NO";
		sql += " FROM PLACE P, AREA A";
		sql += " WHERE P.AREA_NO = A.AREA_NO";
		sql += " AND P.CATEGORY = '식당'";
		sql += " AND A.AREA_NO = ?";

		ArrayList<RestaurantDto> restaurantList = new ArrayList<RestaurantDto>();

		try {

			pstmt = connection.prepareStatement(sql);

			pstmt.setInt(1, areaNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				String placeName = rs.getString("PLACE_NAME");
				int placeNo = rs.getInt("PLACE_NO");
				RestaurantDto restaurantDto = new RestaurantDto(placeName, placeNo);

				restaurantList.add(restaurantDto);
			}
			return restaurantList;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return restaurantList;
	}

	// user 식당 정보
	public RestaurantDto selectRestaurantInfomation(int placeNo) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "";

		sql += "SELECT PLACE_NO, PLACE_NAME, PL_ADDRESS, PL_PHONE, PL_WEBSITE";
		sql += " FROM PLACE";
		sql += " WHERE CATEGORY = '식당'";
		sql += " AND PLACE_NO = ?";

		RestaurantDto restaurantDto = null;
		try {

			pstmt = connection.prepareStatement(sql);

			pstmt.setInt(1, placeNo);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				String placeName = rs.getString("PLACE_NAME");
				String plAddress = rs.getString("PL_ADDRESS");
				String plPhone = rs.getString("PL_PHONE");
				String plWebsite = rs.getString("PL_WEBSITE");

				restaurantDto = new RestaurantDto(placeName, plAddress, plPhone, plWebsite);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return restaurantDto;
	}

	// admin
	public List<RestaurantDto> selectRestaurantList() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<RestaurantDto> restaurantList = new ArrayList<>();

		try {

			String sql = "";
			sql += "SELECT PLACE_NO, CATEGORY, PLACE_NAME, PL_ADDRESS, PL_PHONE, PL_WEBSITE, GEN_RESERVATION, RECO_RESERVATION";
			sql += " FROM PLACE";
			sql += " WHERE CATEGORY = '식당'";

			pstmt = connection.prepareStatement(sql);

			rs = pstmt.executeQuery();

			int placeNo = 0;
			String category = "";
			String placeName = "";
			String plAddress = "";
			String plPhone = "";
			String plWebsite = "";
			int genReservation = 0;
			int recoReservation = 0;

			RestaurantDto restaurantDto = null;

			while (rs.next()) {
				placeNo = rs.getInt("PLACE_NO");
				category = rs.getString("CATEGORY");
				placeName = rs.getString("PLACE_NAME");
				plAddress = rs.getString("PL_ADDRESS");
				plPhone = rs.getString("PL_PHONE");
				plWebsite = rs.getString("PL_WEBSITE");
				genReservation = rs.getInt("GEN_RESERVATION");
				recoReservation = rs.getInt("RECO_RESERVATION");

				restaurantDto = new RestaurantDto(placeNo, category, placeName, plAddress, plPhone, plWebsite, genReservation,
				    recoReservation);

				restaurantList.add(restaurantDto);
			}

			return restaurantList;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (rs != null) {
					rs.close();
				}

				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // finally 종료

		return restaurantList;
	}
}