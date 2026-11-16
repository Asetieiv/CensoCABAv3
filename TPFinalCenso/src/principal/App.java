package principal;
import dao.DAO;
public class App {

	public static void main(String[] args) {
		DAO dao = new DAO();
		dao.conectar();

	}

}
