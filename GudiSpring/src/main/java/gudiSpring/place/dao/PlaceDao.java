package gudiSpring.place.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gudiSpring.place.dto.PlaceDto;

public class PlaceDao  {

	private Connection connection;

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	// 사용자 ui용 place list
	public List<PlaceDto> selectPlaceList(int start, int pageSize) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<PlaceDto> placeList = new ArrayList<>();
		
		try {			
			String sql = "";
			sql += "SELECT PLACE_NO, CATEGORY, PLACE_NAME, PL_ADDRESS, PL_PHONE, PL_WEBSITE";
			sql += " , GEN_RESERVATION, RECO_RESERVATION";
			sql += " FROM (SELECT p.*, ROWNUM rnum";
			sql += " FROM (SELECT PLACE_NO, CATEGORY, PLACE_NAME, PL_ADDRESS, PL_PHONE, PL_WEBSITE, GEN_RESERVATION, RECO_RESERVATION";
			sql += " FROM PLACE";
			sql += " ORDER BY PLACE_NO) p";
			sql += " WHERE ROWNUM <= ?)";
			sql += " WHERE rnum > ?";

			pstmt = connection.prepareStatement(sql);
      pstmt.setInt(1, start + pageSize); // 시작 인덱스 + 페이지 크기
      pstmt.setInt(2, start); // 시작 인덱스

      rs = pstmt.executeQuery();

			PlaceDto placeDto = null;

			while (rs.next()) {
         placeDto = new PlaceDto(
            rs.getInt("PLACE_NO"),
            rs.getString("CATEGORY"),
            rs.getString("PLACE_NAME"),
            rs.getString("PL_ADDRESS"),
            rs.getString("PL_PHONE"),
            rs.getString("PL_WEBSITE"),
            rs.getInt("GEN_RESERVATION"),
            rs.getInt("RECO_RESERVATION")
        );
        
        placeList.add(placeDto);
    }

			return placeList;

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

		return null;
	}

	// admin place detail info select
	public PlaceDto adminPlaceDetailSelect(int placeNo) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		PlaceDto placeDto = null;

		try {
			String sql = "";

			sql += "SELECT PLACE_NO, CATEGORY, PLACE_NAME, PL_ADDRESS, PL_PHONE, PL_WEBSITE";
			sql += " , GEN_RESERVATION, RECO_RESERVATION, PLACE_IMG_PATH, AREA_NAME";
			sql += " FROM PLACE P, AREA A";
			sql += " WHERE P.AREA_NO = A.AREA_NO";
			sql += " AND PLACE_NO = ?";

			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, placeNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				placeDto = new PlaceDto(
						rs.getInt("PLACE_NO"), 
						rs.getString("CATEGORY"), 
						rs.getString("PLACE_NAME"), 
						rs.getString("PL_ADDRESS"), 
						rs.getString("PL_PHONE"), 
						rs.getString("PL_WEBSITE"),
						rs.getInt("GEN_RESERVATION"),
						rs.getInt("RECO_RESERVATION"),
						rs.getString("PLACE_IMG_PATH"),
						rs.getString("AREA_NAME")
					);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return placeDto;

	}

	// admin placeList select
	public List<PlaceDto> searchPlaceList(String placeName, int start, int pageSize) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		
		ArrayList<PlaceDto> placeList = new ArrayList<>();

		try {

			String sql = "";

			sql += "SELECT PLACE_NO, CATEGORY, PLACE_NAME, PL_ADDRESS, PL_PHONE, GEN_RESERVATION, RECO_RESERVATION";
			sql += " FROM (SELECT PLACE_NO, CATEGORY, PLACE_NAME, PL_ADDRESS, PL_PHONE, GEN_RESERVATION, RECO_RESERVATION, ROWNUM rnum";
			sql += " FROM PLACE";
			sql += " WHERE PLACE_NAME LIKE UPPER(?)";
			sql += " AND ROWNUM <= ?)";
			sql += "  WHERE rnum > ?";

			pstmt = connection.prepareStatement(sql);
			
      pstmt.setString(1, "%" + placeName + "%"); // 검색어 설정
      pstmt.setInt(2, start + pageSize); // start + pageSize로 전체 개수를 가져옴
      pstmt.setInt(3, start); // 시작 인덱스 설정

      rs = pstmt.executeQuery();

      PlaceDto placeDto = null;
      
			while (rs.next()) {

				placeDto = new PlaceDto(
		        rs.getInt("PLACE_NO"),
		        rs.getString("CATEGORY"),
		        rs.getString("PLACE_NAME"),
		        rs.getString("PL_ADDRESS"),
		        rs.getString("PL_PHONE"),
		        rs.getInt("GEN_RESERVATION"),
		        rs.getInt("RECO_RESERVATION")
		    );

				placeList.add(placeDto);
			}

			return placeList;

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

		return placeList;
	}

	// place count
	public int placeTotalCount() {
		int totalCount = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT COUNT(*) FROM PLACE";
      pstmt = connection.prepareStatement(sql);
      rs = pstmt.executeQuery();

      if (rs.next()) {
      	totalCount = rs.getInt(1); // 첫 번째 칼럼
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
		} // finally 종료
		
		return totalCount;
	}
	
	public int searchTotalCount(String placeName) {
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
        String sql = "SELECT COUNT(*) FROM PLACE WHERE PLACE_NAME LIKE ?";
        pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, "%" + placeName + "%"); // 검색어 설정

        rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1); // 첫 번째 칼럼의 값을 반환
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

    return 0; // 기본값
	}
	
	// admin insert place
	public int placeInsert(PlaceDto placeDto) {
		int result = 0;
		PreparedStatement pstmt = null;

		try {

			String sql = "";
			sql += "INSERT INTO PLACE";
			sql += " (PLACE_NO, CATEGORY, AREA_NO, PLACE_NAME, PL_ADDRESS, PL_PHONE, PL_WEBSITE, PLACE_IMG_PATH)";
			sql += " VALUES (PLACE_NO_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";

			int colIndex = 1;

			String category = placeDto.getCategory();
			int areaNo = placeDto.getAreaNo();
			String placeName = placeDto.getPlaceName();
			String plAddress = placeDto.getPlAddress();
			String plPhone = placeDto.getPlPhone();
			String plWebsite = placeDto.getPlWebsite();
			String placeImgPath = placeDto.getPlaceImgPath();

			pstmt = connection.prepareStatement(sql);

			pstmt.setString(colIndex++, category);
			pstmt.setInt(colIndex++, areaNo);
			pstmt.setString(colIndex++, placeName);
			pstmt.setString(colIndex++, plAddress);
			pstmt.setString(colIndex++, plPhone);
			pstmt.setString(colIndex++, plWebsite);
			pstmt.setString(colIndex, placeImgPath);

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // finally 종료

		return result;
	}

	// admin place update
	public int placeUpdate(PlaceDto placeDto) {
		int result = 0;
		PreparedStatement pstmt = null;

		try {

			String sql = "";
			sql += "UPDATE PLACE";
			sql += " SET PLACE_NAME = ?, PL_ADDRESS = ?, PL_PHONE = ?, PL_WEBSITE = ?, PLACE_IMG_PATH = ?";
			sql += " WHERE PLACE_NO = ?";

			int colIndex = 1;

			pstmt = connection.prepareStatement(sql);

			pstmt.setString(colIndex++, placeDto.getPlaceName());
			pstmt.setString(colIndex++, placeDto.getPlAddress());
			pstmt.setString(colIndex++, placeDto.getPlPhone());
			pstmt.setString(colIndex++, placeDto.getPlWebsite());
			pstmt.setString(colIndex++, placeDto.getPlaceImgPath());
			pstmt.setInt(colIndex, placeDto.getPlaceNo());

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // finally 종료

		return result;
	}

	// admin placeList delete
	public int placeDelete(ArrayList<Integer> removePlaceNoList) {
		int result = 0;
		PreparedStatement pstmt = null;

		String sql = "DELETE FROM PLACE WHERE PLACE_NO = ?";

		try {

			pstmt = connection.prepareStatement(sql);

      for (Integer placeNo : removePlaceNoList) {
          pstmt.setInt(1, placeNo);
          result += pstmt.executeUpdate();
      }

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // finally 종료

		return result;
	}
}