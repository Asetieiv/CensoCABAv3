package dao;
import modelo.Barrio;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.BarrioComplHab;
import modelo.BarrioTrazForm;
import modelo.BarrioUrbPlanif;
import modelo.Persona;
public class DAO {
	String user = "root";
	String psw = "admin";
	String path = "jdbc:mysql://localhost:3306/censocabatp";


	
    public Connection conectar() {
		Connection c = null;
		try {
			c = DriverManager.getConnection(path, user, psw);
			System.out.println("Se conectó");
		} catch (SQLException e) {
			e.printStackTrace();

		}
		System.out.println("se conectó");
		return c;
	}
	
	public void agregarPersonaxParametro(Persona p) {
		Connection c = null;
		String SQL = "INSERT INTO personas (dni, nombreCompleto, situacionLaboral, fechaNacimiento, Barrio_nombre) VALUES (?,?,?,?,?);";
		try {
			c = conectar();
			PreparedStatement stmt = c.prepareStatement(SQL);
	        stmt.setString(1, p.getDNI());
	        stmt.setString(2, p.getnombreCompleto());
	        stmt.setString(3, p.getsituacionLab().name());
	        stmt.setDate(4, java.sql.Date.valueOf(p.getFechaDeNacimiento()));
	        stmt.setString(5, p.getBarrio().getNombreBarrio());			
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	public void agregarBarrioxParametro(Barrio b) {
		Connection c = null;
		String SQL = "INSERT INTO barrio (nombreBarrio, superficie, cantEdificios, asfaltoEnCalles, numLey, tieneConformidad, cantMonoblocks, tieneConsorcio) VALUES (?,?,?,?,?,?,?,?);";
		try {
			c = conectar();
			PreparedStatement stmt = c.prepareStatement(SQL);
			stmt.setString(1, b.getNombreBarrio());
			stmt.setFloat(2, b.getSuperficie());

			if (b instanceof BarrioTrazForm) {

			    BarrioTrazForm bt = (BarrioTrazForm) b;
			    stmt.setInt(3, bt.getCantEdificios());
			    stmt.setFloat(4, bt.getAsfaltoEnCalles());
			    stmt.setNull(5, java.sql.Types.FLOAT);
			    stmt.setNull(6, java.sql.Types.BOOLEAN);
			    stmt.setNull(7, java.sql.Types.INTEGER);
			    stmt.setNull(8, java.sql.Types.BOOLEAN);
			}

			else if (b instanceof BarrioUrbPlanif) {
			    BarrioUrbPlanif bu = (BarrioUrbPlanif) b;
			    stmt.setNull(3, java.sql.Types.INTEGER);
			    stmt.setNull(4, java.sql.Types.FLOAT);
			    stmt.setFloat(5, bu.getNumLey());
			    stmt.setBoolean(6, bu.getTieneConexionFormal());
			    stmt.setNull(7, java.sql.Types.INTEGER);
			    stmt.setNull(8, java.sql.Types.BOOLEAN);
			}

			else if (b instanceof BarrioComplHab) {
			    BarrioComplHab bc = (BarrioComplHab) b;
			    stmt.setNull(3, java.sql.Types.INTEGER);
			    stmt.setNull(4, java.sql.Types.FLOAT);
			    stmt.setNull(5, java.sql.Types.FLOAT);
			    stmt.setNull(6, java.sql.Types.BOOLEAN);
			    stmt.setInt(7, bc.getCantMonoblocks());
			    stmt.setBoolean(8, bc.getTieneConsorcio());
			}
	}
		catch (SQLException e) {
		e.printStackTrace();

	} finally {
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	}
}
