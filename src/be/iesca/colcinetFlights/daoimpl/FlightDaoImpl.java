package be.iesca.colcinetFlights.daoimpl;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import be.iesca.colcinetFlights.dao.FlightDao;
import be.iesca.colcinetFlights.domaine.Flight;
import be.iesca.colcinetFlights.domaine.Glide;
import be.iesca.colcinetFlights.domaine.Pilot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FlightDaoImpl implements FlightDao {

	private static final String ADD = "INSERT INTO vol (datevol, duree, prix, idplaneur, idpilote) VALUES (?, ?, ?, ?, ?)";
	
	private static final String LIST_WITH_DATE = "SELECT vol.id, vol.datevol, vol.duree, vol.prix, vol.idplaneur, vol.idpilote FROM vol "+
													"INNER JOIN planeur ON vol.idplaneur = planeur.id "+
													"INNER JOIN pilote ON vol.idpilote = pilote.id "+
												 "WHERE vol.datevol =? "+
												 "ORDER BY vol.duree DESC";
	
	private static final String LIST_WITHOUT_DATE = "SELECT vol.id, vol.datevol, vol.duree, vol.prix, vol.idplaneur, vol.idpilote FROM vol "+
														"INNER JOIN planeur ON vol.idplaneur = planeur.id "+
														"INNER JOIN pilote ON vol.idpilote = pilote.id "+
													"ORDER BY vol.datevol ASC, vol.duree DESC";
	
	private static final String GET_PILOT = "SELECT * FROM pilote WHERE id = ?";
	
	private static final String GET_GLIDE = "SELECT * FROM planeur WHERE id = ?";
	
	private static final String ROWCOUNT_WITH_DATE = "SELECT COUNT(*) FROM vol WHERE datevol = ?";
	private static final String ROWCOUNT_WITHOUT_DATE = "SELECT COUNT(*) FROM vol";
	
	public FlightDaoImpl() {

	}

	private void close(ResultSet rs, PreparedStatement pstmt, Connection con) {
		try {
			if (rs != null)
				rs.close();
		} catch (Exception e) {
		}

		try {
			if (pstmt != null)
				pstmt.close();
		} catch (Exception e) {
		}

		try {
			if (con != null)
				con.close();
		} catch (Exception e) {
		}
	}

	@Override
	public boolean addFlight(Flight flight) {
		boolean addResult = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		java.sql.Date sqlDate = new java.sql.Date(flight.getDateFlight().getTime());

		try {
			con = DaoFactory.getInstance().getConnexion();
			
			pstmt = con.prepareStatement(ADD);
			pstmt.setDate(1, sqlDate);
			pstmt.setInt(2, flight.getDureeFlight());
			pstmt.setDouble(3, flight.getPrixFlight());
			pstmt.setInt(4, flight.getPlaneur().getIdGlide()); //Venir mettre l'id du planeur grace a son type
			pstmt.setInt(5, flight.getPilote().getIdPilot()); //Venir mettre l'id du pilote grace a son nom

			int rslt = pstmt.executeUpdate();
			if (rslt == 1)
				addResult = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, con);
		}

		return addResult;
	}

	@Override
	public List<Flight> listFlightsWithDate(Date date) {
		List<Flight> list = new ArrayList<Flight>();
		Connection con = null;
		PreparedStatement pstmtList = null;
		PreparedStatement pstmtPilot = null;
		PreparedStatement pstmtGlide = null;
		ResultSet rsList = null;
		ResultSet rsPilot = null;
		ResultSet rsGlide = null;
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		
		try{
			con = DaoFactory.getInstance().getConnexion();
			pstmtList = con.prepareStatement(LIST_WITH_DATE);
			pstmtList.setDate(1, sqlDate);
			rsList = pstmtList.executeQuery();
		
			while(rsList.next()){
				//Recherche du pilote en BD + création
				pstmtGlide = con.prepareStatement(GET_GLIDE);
				pstmtGlide.setInt(1, rsList.getInt("idplaneur"));
				rsGlide = pstmtGlide.executeQuery();
				rsGlide.next();
				Glide g = new Glide(rsGlide.getInt("id"), rsGlide.getString("type"), rsGlide.getDouble("prixhoraire"), rsGlide.getDouble("prixfixe"));
				
				//Recherche du planeur en BD + création
				pstmtPilot = con.prepareStatement(GET_PILOT);
				int idPilot = rsList.getInt("idpilote");
				pstmtPilot.setInt(1, idPilot);
				rsPilot = pstmtPilot.executeQuery();
				rsPilot.next();
				Pilot p = new Pilot(rsPilot.getInt("id"), rsPilot.getString("email"), rsPilot.getString("nom"), rsPilot.getString("prenom"), rsPilot.getString("adresse"), rsPilot.getString("nogsm"), rsPilot.getDouble("solde"));

			
				//Creation du vol
				Flight v = new Flight(rsList.getDate("datevol"), rsList.getInt("duree"), rsList.getDouble("prix"), g, p);
				
				//Ajout du vol dans la liste
				list.add(v);
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			close(rsList, pstmtList, con);
			close(rsPilot, pstmtPilot, con);
			close(rsGlide, pstmtGlide, con);
		}
		return list;
	}
	
	@Override
	public List<Flight> listFlightsWithoutDate() {
		List<Flight> list = new ArrayList<Flight>();
		Connection con = null;
		PreparedStatement pstmtList = null;
		PreparedStatement pstmtPilot = null;
		PreparedStatement pstmtGlide = null;
		ResultSet rsList = null;
		ResultSet rsPilot = null;
		ResultSet rsGlide = null;
		
		try{
			con = DaoFactory.getInstance().getConnexion();
			pstmtList = con.prepareStatement(LIST_WITHOUT_DATE);
			rsList = pstmtList.executeQuery();
		
			while(rsList.next()){
				//Recherche du pilote en BD + création
				pstmtGlide = con.prepareStatement(GET_GLIDE);
				int idGlide = rsList.getInt("idplaneur");
				pstmtGlide.setInt(1, idGlide);
				rsGlide = pstmtGlide.executeQuery();
				rsGlide.next();
				Glide g = new Glide(rsGlide.getInt("id"), rsGlide.getString("type"), rsGlide.getDouble("prixhoraire"), rsGlide.getDouble("prixfixe"));
				
				//Recherche du planeur en BD + création
				pstmtPilot = con.prepareStatement(GET_PILOT);
				int idPilot = rsList.getInt("idpilote");
				pstmtPilot.setInt(1, idPilot);
				rsPilot = pstmtPilot.executeQuery();
				rsPilot.next();
				Pilot p = new Pilot(rsPilot.getInt("id"), rsPilot.getString("email"), rsPilot.getString("nom"), rsPilot.getString("prenom"), rsPilot.getString("adresse"), rsPilot.getString("nogsm"), rsPilot.getDouble("solde"));
			
				//Creation du vol
				Flight v = new Flight(rsList.getDate("datevol"), rsList.getInt("duree"), rsList.getDouble("prix"), g, p);
				
				//Ajout du vol dans la liste
				list.add(v);
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			close(rsList, pstmtList, con);
			close(rsPilot, pstmtPilot, con);
			close(rsGlide, pstmtGlide, con);
		}
		return list;
	}
	
	@Override
	public int getRowCountWithDate(Date date) {
		int rowResult = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		try {
			con = DaoFactory.getInstance().getConnexion();
			pstmt = con.prepareStatement(ROWCOUNT_WITH_DATE);
			pstmt.setDate(1, sqlDate);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				rowResult = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, con);
		}
		
		return rowResult;
	}
	
	@Override
	public int getRowCountWithoutDate() {
		int rowResult = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DaoFactory.getInstance().getConnexion();
			pstmt = con.prepareStatement(ROWCOUNT_WITHOUT_DATE);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				rowResult = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, con);
		}
		
		return rowResult;
		
	}
}
