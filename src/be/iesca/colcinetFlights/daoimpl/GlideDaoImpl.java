package be.iesca.colcinetFlights.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import be.iesca.colcinetFlights.dao.GlideDao;
import be.iesca.colcinetFlights.domaine.Glide;

public class GlideDaoImpl implements GlideDao {

	private static final String GET = "SELECT * FROM planeur WHERE type=?";
	private static final String LIST_ALL_GLIDES = "SELECT * FROM planeur";
	
	public GlideDaoImpl() {
		
	}
	
	private void close(ResultSet rs, PreparedStatement pstmt, Connection con) {
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
	public List<Glide> listAllGlides() {
		List<Glide> listOfGlides = new ArrayList<Glide>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DaoFactory.getInstance().getConnexion();
			pstmt = con.prepareStatement(LIST_ALL_GLIDES);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Glide g = new Glide(rs.getInt("id"), rs.getString("type"), rs.getDouble("prixhoraire"), rs.getDouble("prixfixe"));
				listOfGlides.add(g);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, con);
		}
	
		return listOfGlides;
	}

	@Override
	public Glide getGlide(String type) {
		Glide g = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DaoFactory.getInstance().getConnexion();
			pstmt = con.prepareStatement(GET);
			pstmt.setString(1, type);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				g = new Glide(rs.getInt("id"), rs.getString("type"), rs.getDouble("prixhoraire"), rs.getDouble("prixfixe"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, con);
		}
		
		return g;
	}
	
}
