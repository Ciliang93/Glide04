package be.iesca.colcinetFlights.daoimpl;

import java.util.ArrayList;
import java.util.List;

import be.iesca.colcinetFlights.dao.PilotDao;
import be.iesca.colcinetFlights.domaine.Pilot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PilotDaoImpl implements PilotDao {
	
	private static final String ADD = "INSERT INTO pilote (email, nom, prenom, adresse, nogsm, solde) VALUES (?,?,?,?,?,?)";
	private static final String GET = "SELECT * FROM pilote p WHERE p.nom = ? AND p.prenom = ?";
	private static final String LIST_ALL_PILOT_BY_NAME = "SELECT * FROM pilote p ORDER BY p.nom ASC, p.prenom ASC";
	private static final String LIST_ALL_PILOT_BY_BALANCE = "SELECT * FROM pilote p WHERE p.solde < 0 ORDER BY p.solde ASC";
	private static final String MODIFY = "UPDATE pilote SET email = ?, prenom = ?, adresse = ?, nogsm = ?, solde = ? WHERE nom = ?";
	private static final String DELETE = "DELETE FROM pilote p WHERE p.nom = ?";
	private static final String ROWCOUNTBYNAME = "SELECT COUNT(*) FROM pilote";
	private static final String ROWCOUNTBYBALANCE = "SELECT COUNT(*) FROM pilote p WHERE p.solde < 0";
	
	public PilotDaoImpl(){
	}
	
	private void close(ResultSet rs, PreparedStatement pstmt, Connection con){
		try {
			if (rs != null)
				rs.close();
		} catch (Exception e) { }
		
		try {
			if (pstmt != null)
				pstmt.close();
		} catch (Exception e) { }
		
		try {
			if (con != null)
				con.close();
		} catch (Exception e) { }
	}
	
	@Override
	public boolean addPilot(Pilot pilote) {
		boolean addResult = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DaoFactory.getInstance().getConnexion();
			pstmt = con.prepareStatement(ADD);
			pstmt.setString(1, pilote.getEmailPilot());
			pstmt.setString(2, pilote.getNomPilot());
			pstmt.setString(3, pilote.getPrenomPilot());
			pstmt.setString(4, pilote.getAdressePilot());
			pstmt.setString(5, pilote.getNoGsmPilot());
			pstmt.setDouble(6, pilote.getSoldePilot());
			
			int rslt = pstmt.executeUpdate();
			if(rslt == 1) {
				addResult = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(null, pstmt, con);
		}
		
		return addResult;
	}

	@Override
	public Pilot getPilot(String nameAndForname) {
		Pilot pilot = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[] nomEtPrenom = nameAndForname.split(" ");
		
		try {
			 con = DaoFactory.getInstance().getConnexion();
			 pstmt = con.prepareStatement(GET);
			 pstmt.setString(1, nomEtPrenom[0]); //NOM = ? 
			 pstmt.setString(2, nomEtPrenom[1]); //PRENOM = ?
			 rs = pstmt.executeQuery();
			 
			 if(rs.next()) {
				 pilot = new Pilot(rs.getString("email"), rs.getString("nom"), rs.getString("prenom"), rs.getString("adresse"), rs.getString("nogsm"), rs.getDouble("solde")); 
			 }
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			close(rs, pstmt, con);
		}
		
		return pilot;
	}

	@Override
	public List<Pilot> listAllPilotsByName() {
		List<Pilot> listSortedByName = new ArrayList<Pilot>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DaoFactory.getInstance().getConnexion();
			pstmt = con.prepareStatement(LIST_ALL_PILOT_BY_NAME);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Pilot p = new Pilot(rs.getInt("id"), rs.getString("email"), rs.getString("nom"), rs.getString("prenom"), rs.getString("adresse"), rs.getString("nogsm"), rs.getDouble("solde"));
				listSortedByName.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, con);
		}
		
		return listSortedByName;
	}

	@Override
	public List<Pilot> listAllPilotsByBalance() {
		List<Pilot> listSortedByBalance = new ArrayList<Pilot>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DaoFactory.getInstance().getConnexion();
			pstmt = con.prepareStatement(LIST_ALL_PILOT_BY_BALANCE);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Pilot p = new Pilot(rs.getInt("id"), rs.getString("email"), rs.getString("nom"), rs.getString("prenom"), rs.getString("adresse"), rs.getString("nogsm"), rs.getDouble("solde"));
				listSortedByBalance.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, con);
		}
		
		return listSortedByBalance;
	}

	@Override
	public boolean modifyPilot(Pilot pilote) {
		boolean modifResult = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DaoFactory.getInstance().getConnexion();
			pstmt = con.prepareStatement(MODIFY);
			pstmt.setString(1, pilote.getEmailPilot());
			pstmt.setString(2, pilote.getPrenomPilot());
			pstmt.setString(3, pilote.getAdressePilot());
			pstmt.setString(4, pilote.getNoGsmPilot());
			pstmt.setDouble(5, pilote.getSoldePilot());
			pstmt.setString(6, pilote.getNomPilot());
			int resultat = pstmt.executeUpdate();
			if (resultat == 1) {
				modifResult = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			close(null, pstmt, con);
		}
		return modifResult;
	}

	@Override
	public boolean deletePilot(String nomDuPilote) {
		boolean deleteResult = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DaoFactory.getInstance().getConnexion();
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, nomDuPilote.trim());
			int rslt = pstmt.executeUpdate();
			if (rslt == 1)
				deleteResult = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			close(rs, pstmt, con);
		}
		return deleteResult;
	}

	@Override
	public int getRowCountByName() {
		int rowResult = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DaoFactory.getInstance().getConnexion();
			pstmt = con.prepareStatement(ROWCOUNTBYNAME);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				rowResult = rs.getInt(1);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, con);
		}
		
		return rowResult;
	}
	
	@Override
	public int getRowCountByBalance() {
		int rowResult = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DaoFactory.getInstance().getConnexion();
			pstmt = con.prepareStatement(ROWCOUNTBYBALANCE);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				rowResult = rs.getInt(1);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, con);
		}
		
		return rowResult;
	}

}
